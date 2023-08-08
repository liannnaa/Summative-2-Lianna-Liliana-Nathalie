package com.company.bookstore.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.company.bookstore.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AuthorRepositoryTest {
    @Autowired
    AuthorRepository authRepo;

    private Author author;

    @BeforeEach
    public void setUp() throws Exception {
        authRepo.deleteAll();

        // Arrange
        author = new Author();

        Author author = new Author();
        author.setAuthorId(1);
        author.setFirstName("Test First");
        author.setLastName("Test Last");
        author.setEmail("johndoe@gmail.com");
        author.setStreet("123 St");
        author.setCity("Miami");
        author.setState("FL");
        author.setPostalCode("33167");
        author.setPhone("123-456-7890");

        author = authRepo.save(author);

    }

    @Test
    public void addAuthor() {
        // Assert
        Optional<Author> foundAuthor = authRepo.findById(author.getAuthorId());

        assertEquals(foundAuthor.get(), author);
    }

    @Test
    public void updateAuthor() {
        // Act

        author.setFirstName("John");
        authRepo.save(author);

        // Assert
        Optional<Author> foundAuthor = authRepo.findById(author.getAuthorId());

        assertEquals(foundAuthor.get(), author);
    }

    @Test
    public void deleteAuthor() {
        // Act
        authRepo.deleteById(author.getAuthorId());

        // Assert
        Optional<Author> foundAuthor = authRepo.findById(author.getAuthorId());

        assertFalse(foundAuthor.isPresent());
    }

    @Test
    public void getAuthorById() {
        // Act
        Author foundAuthor = authRepo.findById(author.getAuthorId()).orElse(null);

        // Assert
        assertEquals(author, foundAuthor);
    }
    @Test
    public void getAllPublisherTest() {
        //Act
        List<Author> authors = authRepo.findAll();

        //Assert
        assertTrue(authors.contains(author));
    }
    }