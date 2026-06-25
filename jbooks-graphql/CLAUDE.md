# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Tech Stack

- **Spring Boot 4.1.0** with Java 26
- **Spring for GraphQL** over **WebMVC** (not WebFlux) — annotation-driven (`@QueryMapping`, `@MutationMapping`, `@Argument`)
- **Spring Data JPA** + PostgreSQL (port 5433)
- **OpenTelemetry** via `spring-boot-starter-opentelemetry`
- **Docker Compose** integration (`compose.yaml`) — starts PostgreSQL and Grafana LGTM stack automatically when running locally

## Commands

```bash
# Build
./mvnw clean package

# Run (Docker Compose starts automatically via spring-boot-docker-compose)
./mvnw spring-boot:run

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=JbooksGraphqlApplicationTests

# Run a single test method
./mvnw test -Dtest=JbooksGraphqlApplicationTests#contextLoads
```

`@SpringBootTest` tests require Docker Compose (PostgreSQL) to be running — start it with `docker compose up -d` if not using `./mvnw spring-boot:run`.

GraphiQL UI is available at `http://localhost:8080/graphiql` when the app is running.

## Architecture

The app is a GraphQL API for a books/authors catalogue. The schema lives in `src/main/resources/graphql/schema.graphqls` and is the source of truth for what operations exist.

**Package layout** (`dev.amerida.jbooks_graphql`):

| Package | Responsibility |
|---|---|
| `book/` | `BookEntity`, `BookRepository`, `BookInput` (record), `BookController` |
| `author/` | `AuthorEntity`, `AuthorRepository` |
| `search/` | `SearchController` — cross-entity text search returning a GraphQL `union SearchItem = Author \| Book` |
| root | `DataLoader` (seeds DB on startup via `CommandLineRunner`), `JbooksGraphqlApplication` |

**GraphQL → Java mapping:** Spring for GraphQL resolves fields by method name. Controllers are plain `@Controller` beans (not `@RestController`). Each public method annotated with `@QueryMapping` or `@MutationMapping` must match the corresponding field name in `schema.graphqls`. Input types map to Java records annotated with `@Argument`.

**JPA relationship model:** `AuthorEntity` (1) ↔ (many) `BookEntity` via `@OneToMany(mappedBy = "author")` / `@ManyToOne(fetch = FetchType.LAZY)`. GraphQL field resolvers trigger lazy loading automatically through Spring for GraphQL's built-in batching — no explicit `@BatchMapping` is needed for the current schema.

**Schema union type:** `SearchItem` is a `union` of `Author` and `Book`. `SearchController.search()` returns `List<Object>` mixing both entity types; Spring for GraphQL resolves the concrete type at runtime using the Java class name. Search is implemented via JPA derived query methods (`findAllByTitleContainsIgnoreCase`, `findAllByNameContainsIgnoreCase`).

**Database:** Schema is managed by Hibernate with `ddl-auto: create-drop` — the schema is recreated on every restart. `DataLoader` seeds sample authors and books on every startup.

**Testing:** `pom.xml` includes `spring-boot-starter-graphql-test` (provides `GraphQlTester`) and `spring-boot-starter-data-jpa-test`. Use `GraphQlTester` for slice tests that exercise the GraphQL layer without starting a full server.

**Observability:** OpenTelemetry traces are exported to the Grafana LGTM container (Loki + Grafana + Tempo + Mimir) defined in `compose.yaml`. Grafana runs on port 3000.