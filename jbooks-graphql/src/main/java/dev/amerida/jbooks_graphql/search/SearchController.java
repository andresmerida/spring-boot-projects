package dev.amerida.jbooks_graphql.search;

import dev.amerida.jbooks_graphql.author.AuthorRepository;
import dev.amerida.jbooks_graphql.book.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
class SearchController {
    private static final Logger log = LoggerFactory.getLogger(SearchController.class);

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    SearchController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @QueryMapping
    List<Object> search(@Argument String text) {
        log.info("Searching for {}", text);
        List<Object> results = new ArrayList<>();
        results.addAll(authorRepository.findAllByNameContainsIgnoreCase(text));
        results.addAll(bookRepository.findAllByTitleContainsIgnoreCase(text));
        return results;
    }
}
