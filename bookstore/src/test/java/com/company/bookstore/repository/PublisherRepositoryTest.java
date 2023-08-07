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
public class PublisherRepositoryTest {
    @Autowired
    PublisherRepository repo;

    private Publisher publisher;

    @BeforeEach
    public void setUp() {
        repo.deleteAll();

        // Arrange
        publisher = new Publisher();
        publisher.setPublisherId(1);
        publisher.setName("Test Name");
        publisher.setStreet("Test Street");
        publisher.setCity("Test City");
        publisher.setState("Test State");
        publisher.setPostalCode("Test Postal Code");
        publisher.setPhone("Test Phone");
        publisher.setEmail("Test Email");

        publisher=repo.save(publisher);
    }

    @Test
    public void addPublisher() {
        // Assert
        Optional<Publisher> foundPublisher = repo.findById(publisher.getPublisherId());

        assertEquals(foundPublisher.get(), publisher);
    }

    @Test
    public void updatePublisher() {
        // Act
        publisher.setName("UPDATED NAME");
        repo.save(publisher);

        // Assert
        Optional<Publisher> foundPublisher = repo.findById(publisher.getPublisherId());

        assertEquals(foundPublisher.get(), publisher);
    }

    @Test
    public void deletePublisher() {
        // Act
        repo.deleteById(publisher.getPublisherId());

        // Assert
        Optional<Publisher> foundPublisher = repo.findById(publisher.getPublisherId());

        assertFalse(foundPublisher.isPresent());
    }

    @Test
    public void getPublisherById() {
        // Act
        Publisher foundPublisher = repo.findById(publisher.getPublisherId()).orElse(null);

        // Assert
        assertEquals(publisher, foundPublisher);
    }

    @Test
    public void getAllPublisherTest(){
        //Act
        List<Publisher> publishers=repo.findAll();

        //Assert
        assertTrue(publishers.contains(publisher));

    }

}

