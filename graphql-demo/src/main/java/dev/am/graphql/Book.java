package dev.am.graphql;

import java.util.List;
import java.util.Optional;

public record Book(Integer id, String name, Integer pageCount, Integer authorId) {
    public static List<Book> books = List.of(
        new Book(1, "Kafka", 1, 1),
        new Book(2, "Harry Potter", 4, 2),
        new Book(3, "Foobar", 6, 1),
        new Book(4, "Spring Boot", 8, 3)
    );

    public static Optional<Book> getBookById(Integer id) {
        return books.stream()
                .filter(book -> book.id().equals(id))
                .findFirst();
    }
}
