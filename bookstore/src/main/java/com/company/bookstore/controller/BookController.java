package com.company.bookstore.controller;

import com.company.bookstore.model.Author;
import com.company.bookstore.model.Book;
import com.company.bookstore.model.Publisher;
import com.company.bookstore.repository.AuthorRepository;
import com.company.bookstore.repository.BookRepository;
import com.company.bookstore.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @QueryMapping
    public List<Book> books() {
        return bookRepository.findAll();
    }

    @QueryMapping
    public Book findBookById(@Argument Integer id) {
        return bookRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public List<Book> findBooksByAuthorId(@Argument Integer authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    @MutationMapping
    public Book addBook(
            @Argument String isbn,
            @Argument String publishDate,
            @Argument Integer authorId,
            @Argument String title,
            @Argument Integer publisherId,
            @Argument Double price) {

        Book newBook = new Book();

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + authorId));
        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + publisherId));

        newBook.setIsbn(isbn);
        newBook.setPublishDate(publishDate);
        newBook.setAuthor(author);
        newBook.setTitle(title);
        newBook.setPublisher(publisher);
        newBook.setPrice(price);

        return bookRepository.save(newBook);
    }

    @MutationMapping
    public Book updateBook(
            @Argument Integer id,
            @Argument String isbn,
            @Argument String publishDate,
            @Argument Integer authorId,
            @Argument String title,
            @Argument Integer publisherId,
            @Argument Double price) {

        Optional<Book> existingBookOptional = bookRepository.findById(id);
        if (existingBookOptional.isPresent()) {
            Book existingBook = existingBookOptional.get();

            Author author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new RuntimeException("Author not found with id: " + authorId));
            Publisher publisher = publisherRepository.findById(publisherId)
                    .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + publisherId));

            existingBook.setIsbn(isbn);
            existingBook.setPublishDate(publishDate);
            existingBook.setAuthor(author);
            existingBook.setTitle(title);
            existingBook.setPublisher(publisher);
            existingBook.setPrice(price);

            return bookRepository.save(existingBook);
        } else {
            throw new RuntimeException("Book not found with id: " + id);
        }
    }

    @MutationMapping
    public boolean deleteBookById(@Argument Integer id) {
        boolean exists = bookRepository.existsById(id);
        if (exists) {
            bookRepository.deleteById(id);
        }
        return exists;
    }
}
