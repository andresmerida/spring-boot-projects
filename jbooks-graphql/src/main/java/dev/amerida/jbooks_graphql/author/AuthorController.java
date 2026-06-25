package dev.amerida.jbooks_graphql.author;

import dev.amerida.jbooks_graphql.book.BookEntity;
import dev.amerida.jbooks_graphql.book.BookRepository;
import org.slf4j.Logger;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
class AuthorController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AuthorController.class);

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    AuthorController(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @QueryMapping
    List<AuthorEntity> authors() {
        return authorRepository.findWithBooks();
    }

    @SchemaMapping
    List<BookEntity> books(AuthorEntity author) throws InterruptedException {
        log.info("Fetching books for {}", author);
        Thread.sleep(1000);
        return bookRepository.findByAuthor(author);
    }
}
