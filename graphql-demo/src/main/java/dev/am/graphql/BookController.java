package dev.am.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookController {

    @QueryMapping
    public List<Book> books() {
        return Book.books;
    }

    @QueryMapping
    public Book bookById(@Argument Integer id) {
        return Book.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @SchemaMapping
    public Author author(Book book) {
        return Author.getAuthorById(book.authorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }
}
