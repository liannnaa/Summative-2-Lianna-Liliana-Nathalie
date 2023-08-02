package com.company.bookstore.repository;

import com.company.bookstore.model.Author;
import com.company.bookstore.model.Book;
import com.company.bookstore.model.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookRepositoryTest {
    @Autowired
    BookRepository repo;

    @Autowired
    AuthorRepository authRepo;

    @Autowired
    PublisherRepository pubRepo;

    private Book book;

    @BeforeEach
    public void setUp() {
        repo.deleteAll();

        // Arrange
        book = new Book();

        Author author = new Author();
        author.setFirstName("Test First");
        author.setLastName("Test Last");
        author = authRepo.save(author);

        Publisher publisher = new Publisher();
        publisher.setName("Test Name");
        publisher = pubRepo.save(publisher);

        book.setIsbn("Test Isbn");
        book.setPublishDate("Test Date");
        book.setAuthor(author);
        book.setTitle("Test Title");
        book.setPublisher(publisher);
        book.setPrice(0.00);
    }

    @Test
    public void addBook() {
        // Act
        book = repo.save(book);

        // Assert
        Optional<Book> foundBook = repo.findById(book.getBookId());

        assertEquals(foundBook.get(), book);
    }

    @Test
    public void updateBook() {
        // Arrange
        repo.save(book);

        // Act
        book.setTitle("UPDATED TITLE");
        repo.save(book);

        // Assert
        Optional<Book> foundBook = repo.findById(book.getBookId());

        assertEquals(foundBook.get(), book);
    }

    @Test
    public void deleteBook() {
        // Arrange
        repo.save(book);

        // Act
        repo.deleteById(book.getBookId());

        // Assert
        Optional<Book> foundBook = repo.findById(book.getBookId());

        assertFalse(foundBook.isPresent());
    }

    @Test
    public void getBookById() {
        // Arrange
        book = repo.save(book);

        // Act
        Book foundBook = repo.findById(book.getBookId()).orElse(null);

        // Assert
        assertEquals(book, foundBook);
    }

    @Test
    public void getBooksByAuthorId() {
        // Arrange
        repo.save(book);

        Book book2 = new Book();

        Author author2 = new Author();
        author2.setFirstName("Test First 2");
        author2.setLastName("Test Last 2");
        author2 = authRepo.save(author2);

        Publisher publisher2 = new Publisher();
        publisher2.setName("Test Name 2");
        publisher2 = pubRepo.save(publisher2);

        book2.setIsbn("Test Isbn 2");
        book2.setPublishDate("Test Date 2");
        book2.setAuthor(author2);
        book2.setTitle("Test Title 2");
        book2.setPublisher(publisher2);
        book2.setPrice(1.00);
        repo.save(book2);

        // Act
        List<Book> books = repo.findByAuthorAuthorId(book.getAuthor().getAuthorId());

        // Assert
        assertTrue(books.contains(book));
        assertFalse(books.contains(book2));
    }
}
