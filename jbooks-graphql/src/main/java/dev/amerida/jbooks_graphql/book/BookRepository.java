package dev.amerida.jbooks_graphql.book;

import dev.amerida.jbooks_graphql.author.AuthorEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findAllByTitleContainsIgnoreCase(String title);

    List<BookEntity> findByAuthor(AuthorEntity author);

    @Override
    @EntityGraph(attributePaths = "author")
    List<BookEntity> findAll();
}
