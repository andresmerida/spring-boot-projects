package dev.amerida.jbooks_graphql;

import dev.amerida.jbooks_graphql.author.AuthorEntity;
import dev.amerida.jbooks_graphql.author.AuthorRepository;
import dev.amerida.jbooks_graphql.book.BookEntity;
import dev.amerida.jbooks_graphql.book.BookRepository;
import dev.amerida.jbooks_graphql.review.Review;
import dev.amerida.jbooks_graphql.review.ReviewRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class DataLoader implements CommandLineRunner {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ReviewRepository reviewRepository;

    public DataLoader(BookRepository bookRepository, AuthorRepository authorRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.reviewRepository = reviewRepository;
    }

    private record AuthorsAndBooks(Map<String, AuthorEntity> authors, Map<String, BookEntity> books) { }

    @Override
    public void run(String @NonNull ... args) throws Exception {
        var authorsAndBooks = loadAuthorsAndBooks();
        loadReviews(authorsAndBooks);
    }

    private AuthorsAndBooks loadAuthorsAndBooks() {
        // Create Authors
        AuthorEntity josh = new AuthorEntity();
        josh.setName("Josh Long");
        authorRepository.save(josh);

        AuthorEntity mark = new AuthorEntity();
        mark.setName("Mark Heckler");
        authorRepository.save(mark);

        AuthorEntity greg = new AuthorEntity();
        greg.setName("Greg Turnquist");
        authorRepository.save(greg);

        AuthorEntity dan = new AuthorEntity();
        dan.setName("Dan Vega");
        authorRepository.save(dan);

        AuthorEntity nate = new AuthorEntity();
        nate.setName("Nate Schutta");
        authorRepository.save(nate);

        AuthorEntity craig = new AuthorEntity();
        craig.setName("Craig Walls");
        authorRepository.save(craig);

        // Create Books
        BookEntity cloudNative = new BookEntity();
        cloudNative.setTitle("Cloud Native Java");
        cloudNative.setAuthor(josh);
        bookRepository.save(cloudNative);

        BookEntity reactiveSpring = new BookEntity();
        reactiveSpring.setTitle("Spring Boot: Up and Running");
        reactiveSpring.setAuthor(mark);
        bookRepository.save(reactiveSpring);

        BookEntity springBootInAction = new BookEntity();
        springBootInAction.setTitle("Spring Boot in Action");
        springBootInAction.setAuthor(greg);
        bookRepository.save(springBootInAction);

        BookEntity learningSpring = new BookEntity();
        learningSpring.setTitle("Learning Spring Boot 3.0");
        learningSpring.setAuthor(greg);
        bookRepository.save(learningSpring);

        BookEntity fundamentals = new BookEntity();
        fundamentals.setTitle("Fundamentals of Software Engineering");
        fundamentals.setAuthor(dan);
        bookRepository.save(fundamentals);

        BookEntity springInAction = new BookEntity();
        springInAction.setTitle("Spring in Action");
        springInAction.setAuthor(craig);
        bookRepository.save(springInAction);

        BookEntity springAiInAction = new BookEntity();
        springAiInAction.setTitle("Spring AI in Action");
        springAiInAction.setAuthor(craig);
        bookRepository.save(springAiInAction);

        return new AuthorsAndBooks(
                Map.of(
                        "josh", josh,
                        "mark", mark,
                        "greg", greg,
                        "dan", dan,
                        "nate", nate,
                        "craig", craig
                ),
                Map.of(
                        "cloudNative", cloudNative,
                        "reactiveSpring", reactiveSpring,
                        "springBootInAction", springBootInAction,
                        "learningSpring", learningSpring,
                        "fundamentals", fundamentals,
                        "springInAction", springInAction,
                        "springAiInAction", springAiInAction
                )
        );
    }

    private void loadReviews(AuthorsAndBooks data) {
        // Cloud Native Java Reviews
        Review review1 = new Review();
        review1.setBook(data.books().get("cloudNative"));
        review1.setRating(5);
        review1.setComment("Exceptional deep dive into Cloud Native Java! Josh's expertise shines through every chapter.");
        review1.setReviewerName("Sarah Chen");
        review1.setVerified(true);
        review1.setCreatedAt(LocalDateTime.now().minusDays(5));
        reviewRepository.save(review1);

        Review review2 = new Review();
        review2.setBook(data.books().get("cloudNative"));
        review2.setRating(5);
        review2.setComment("A masterpiece on Cloud Native Java. The examples are practical and the concepts are explained brilliantly!");
        review2.setReviewerName("Mike Johnson");
        review2.setVerified(true);
        review2.setCreatedAt(LocalDateTime.now().minusDays(10));
        reviewRepository.save(review2);

        // Spring Boot: Up and Running Reviews
        Review review3 = new Review();
        review3.setBook(data.books().get("reactiveSpring"));
        review3.setRating(5);
        review3.setComment("Mark delivers a perfect guide for Spring Boot - clear, concise, and incredibly practical!");
        review3.setReviewerName("John Smith");
        review3.setVerified(true);
        review3.setCreatedAt(LocalDateTime.now().minusDays(2));
        reviewRepository.save(review3);

        Review review4 = new Review();
        review4.setBook(data.books().get("reactiveSpring"));
        review4.setRating(5);
        review4.setComment("Comprehensive coverage from basics to advanced topics. A must-read for any Spring developer!");
        review4.setReviewerName("Anonymous");
        review4.setVerified(false);
        review4.setCreatedAt(LocalDateTime.now().minusDays(15));
        reviewRepository.save(review4);

        // Spring Boot in Action Reviews
        Review review5 = new Review();
        review5.setBook(data.books().get("springBootInAction"));
        review5.setRating(5);
        review5.setComment("Greg's expertise makes Spring Boot approachable and exciting. Best technical book I've read this year!");
        review5.setReviewerName("Linda Martinez");
        review5.setVerified(true);
        review5.setCreatedAt(LocalDateTime.now().minusDays(7));
        reviewRepository.save(review5);

        // Learning Spring Boot 3.0 Reviews
        Review review6 = new Review();
        review6.setBook(data.books().get("learningSpring"));
        review6.setRating(5);
        review6.setComment("Fantastic coverage of Spring Boot 3.0! Greg makes complex topics easy to understand.");
        review6.setReviewerName("David Wilson");
        review6.setVerified(true);
        review6.setCreatedAt(LocalDateTime.now().minusDays(1));
        reviewRepository.save(review6);

        Review review7 = new Review();
        review7.setBook(data.books().get("learningSpring"));
        review7.setRating(5);
        review7.setComment("Perfect balance of theory and practice. The examples are gold!");
        review7.setReviewerName("Sarah Chen");
        review7.setVerified(true);
        review7.setCreatedAt(LocalDateTime.now().minusDays(3));
        reviewRepository.save(review7);

        Review review8 = new Review();
        review8.setBook(data.books().get("learningSpring"));
        review8.setRating(5);
        review8.setComment("Comprehensive and well-structured. A perfect guide for all skill levels!");
        review8.setReviewerName("Bob");
        review8.setVerified(false);
        review8.setCreatedAt(LocalDateTime.now().minusDays(20));
        reviewRepository.save(review8);
    }
}
