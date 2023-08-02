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
import org.springframework.graphql.data.method.annotation.SchemaMapping;
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
    public Book findBookById(@Argument int id) {
        Optional<Book> returnVal = bookRepository.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }

    @QueryMapping
    public List<Book> findBooksByAuthorId(@Argument int authorId) {
        List<Book> returnVal = bookRepository.findByAuthorAuthorId(authorId);
        if (returnVal.isEmpty()) {
            return null;
        } else {
            return returnVal;
        }
    }

    @MutationMapping
    public Book addBook(
            @Argument int id,
            @Argument String isbn,
            @Argument String publishDate,
            @Argument Author author,
            @Argument String title,
            @Argument Publisher publisher,
            @Argument Double price) {

        Book newBook = new Book(id, isbn, publishDate, author, title, publisher, price);

        return bookRepository.save(newBook);
    }

    @MutationMapping
    public Book updateBook(
            @Argument int id,
            @Argument String isbn,
            @Argument String publishDate,
            @Argument Author author,
            @Argument String title,
            @Argument Publisher publisher,
            @Argument Double price) {

        Book newBook = new Book(id, isbn, publishDate, author, title, publisher, price);

        return bookRepository.save(newBook);
    }

    @MutationMapping
    public void deleteBookById(@Argument int id) {
        bookRepository.deleteById(id);
    }
}
