package dev.amerida.jbooks_graphql;

import dev.amerida.jbooks_graphql.book.BookEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.restclient.autoconfigure.RestClientAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.client.HttpSyncGraphQlClient;
import org.springframework.web.client.RestClient;

@Import(RestClientAutoConfiguration.class)
public class ClientApp implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(ClientApp.class);

    private final HttpSyncGraphQlClient clientGraphQl;

    public ClientApp(RestClient.Builder restClientBuilder) {
        RestClient restClient = restClientBuilder
                .baseUrl("http://localhost:8080/graphql")
                .build();
        this.clientGraphQl = HttpSyncGraphQlClient.create(restClient);
    }


    static void main(String[] args) {
        new SpringApplicationBuilder(ClientApp.class).web(WebApplicationType.NONE).run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("ClientApp started");

        var document = """
                query findBookById($id: ID!) {
                    book(id: $id) {
                        id
                        title
                        author {
                            id
                            name
                        }
                    }
                }
                """;

        BookEntity bookEntity = clientGraphQl.document(document)
                .variable("id", 1L)
                .retrieveSync("book")
                .toEntity(BookEntity.class);

        log.info("Book retrieved: " + bookEntity);
    }
}
