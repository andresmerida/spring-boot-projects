package dev.amerida.jbooks_graphql.book;

import dev.amerida.jbooks_graphql.author.AuthorEntity;
import dev.amerida.jbooks_graphql.author.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    BookController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @QueryMapping
    List<BookEntity> books() {
        return bookRepository.findAll();
    }

    @QueryMapping
    Optional<BookEntity> book(@Argument Long id) {
        return bookRepository.findById(id);
    }

    @MutationMapping
    BookEntity addBook(@Argument BookInput bookInput) {
        AuthorEntity author = authorRepository.findById(bookInput.authorId()).orElseThrow();
        var book = new BookEntity();
        book.setTitle(bookInput.title());
        book.setAuthor(author);
        return bookRepository.save(book);
    }
}
