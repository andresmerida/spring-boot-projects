package dev.amerida.jbooks_graphql.author;

import dev.amerida.jbooks_graphql.book.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    List<AuthorEntity> findAllByNameContainsIgnoreCase(String name);

    @Query("SELECT a FROM AuthorEntity a LEFT JOIN FETCH a.books")
    List<AuthorEntity> findWithBooks();
}
