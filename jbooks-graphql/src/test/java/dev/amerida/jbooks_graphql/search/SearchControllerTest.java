package dev.amerida.jbooks_graphql.search;

import dev.amerida.jbooks_graphql.author.AuthorEntity;
import dev.amerida.jbooks_graphql.author.AuthorRepository;
import dev.amerida.jbooks_graphql.book.BookEntity;
import dev.amerida.jbooks_graphql.book.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchControllerTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private SearchController searchController;

    @Test
    void search_returnsAuthorsBeforeBooks() {
        var author = authorWith("Josh Long");
        var book = bookWith("Cloud Native Java");
        when(authorRepository.findAllByNameContainsIgnoreCase("java")).thenReturn(List.of(author));
        when(bookRepository.findAllByTitleContainsIgnoreCase("java")).thenReturn(List.of(book));

        List<Object> results = searchController.search("java");

        assertThat(results).containsExactly(author, book);
    }

    @Test
    void search_returnsEmptyList_whenNothingMatches() {
        when(authorRepository.findAllByNameContainsIgnoreCase("xyz")).thenReturn(List.of());
        when(bookRepository.findAllByTitleContainsIgnoreCase("xyz")).thenReturn(List.of());

        assertThat(searchController.search("xyz")).isEmpty();
    }

    @Test
    void search_passesTextToRepositoriesUnmodified() {
        String text = "Spring Boot";
        when(authorRepository.findAllByNameContainsIgnoreCase(text)).thenReturn(List.of());
        when(bookRepository.findAllByTitleContainsIgnoreCase(text)).thenReturn(List.of());

        searchController.search(text);

        verify(authorRepository).findAllByNameContainsIgnoreCase(text);
        verify(bookRepository).findAllByTitleContainsIgnoreCase(text);
    }

    private AuthorEntity authorWith(String name) {
        var entity = new AuthorEntity();
        entity.setName(name);
        return entity;
    }

    private BookEntity bookWith(String title) {
        var entity = new BookEntity();
        entity.setTitle(title);
        return entity;
    }
}