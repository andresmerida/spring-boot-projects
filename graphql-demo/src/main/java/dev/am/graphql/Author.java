package dev.am.graphql;

import java.util.List;
import java.util.Optional;

public record Author(Integer id, String name) {
    public static List<Author> authors = List.of(
            new Author(1, "Andres Merida"),
            new Author(2, "Ana Arebalo"),
            new Author(3, "Luis Miguel"),
            new Author(4, "Lusia Mendez")
    );

    public static Optional<Author> getAuthorById(Integer id) {
        return authors.stream()
                .filter(author -> author.id().equals(id))
                .findFirst();
    }
}
