package dev.amerida.jbooks_graphql.search;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.graphql.test.autoconfigure.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

@SpringBootTest
@AutoConfigureHttpGraphQlTester
@TestPropertySource(properties = "spring.docker.compose.enabled=false")
class SearchControllerIntegrationTest {

    // Static initializer runs before @DynamicPropertySource so the mapped port is available.
    static final GenericContainer<?> POSTGRES;

    static {
        POSTGRES = new GenericContainer<>("postgres:18.1-alpine")
                .withEnv("POSTGRES_DB", "jbooks")
                .withEnv("POSTGRES_USER", "postgres")
                .withEnv("POSTGRES_PASSWORD", "postgres")
                .withExposedPorts(5432)
                .waitingFor(Wait.forListeningPort());
        POSTGRES.start();
    }

    @DynamicPropertySource
    static void configurePostgres(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",
                () -> "jdbc:postgresql://localhost:" + POSTGRES.getMappedPort(5432) + "/jbooks");
        registry.add("spring.datasource.username", () -> "postgres");
        registry.add("spring.datasource.password", () -> "postgres");
    }

    @Autowired
    private HttpGraphQlTester graphQlTester;

    @Test
    void search_findsAuthorByName() {
        // DataLoader seeds "Josh Long" — searching "Josh" returns that author first (authors before books)
        graphQlTester.document("""
                query {
                  search(text: "Josh") {
                    ... on Author { name }
                  }
                }
                """)
                .execute()
                .path("search[0].name")
                .entity(String.class)
                .isEqualTo("Josh Long");
    }

    @Test
    void search_findsMultipleBooks_forCommonTerm() {
        // "Spring" matches several seeded book titles
        graphQlTester.document("""
                query {
                  search(text: "Spring") {
                    __typename
                    ... on Book { title }
                  }
                }
                """)
                .execute()
                .path("search")
                .entityList(Object.class)
                .hasSizeGreaterThan(1);
    }

    @Test
    void search_returnsEmptyList_whenNoMatch() {
        graphQlTester.document("""
                query {
                  search(text: "xyz_no_match_xyz") {
                    ... on Author { name }
                    ... on Book { title }
                  }
                }
                """)
                .execute()
                .path("search")
                .entityList(Object.class)
                .hasSize(0);
    }
}