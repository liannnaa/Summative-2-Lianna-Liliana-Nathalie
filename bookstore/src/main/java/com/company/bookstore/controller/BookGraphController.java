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
public class BookGraphController {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    PublisherRepository publisherRepository;

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
            @Argument String isbn,
            @Argument String publishDate,
            @Argument int authorId,
            @Argument String title,
            @Argument int publisherId,
            @Argument Double price) {

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        Book newBook = new Book(isbn, publishDate, author, title, publisher, price);

        return bookRepository.save(newBook);
    }

    @MutationMapping
    public Book updateBook(
            @Argument int id,
            @Argument String isbn,
            @Argument String publishDate,
            @Argument int authorId,
            @Argument String title,
            @Argument int publisherId,
            @Argument Double price) {

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        Book bookToUpdate = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        bookToUpdate.setIsbn(isbn);
        bookToUpdate.setPublishDate(publishDate);
        bookToUpdate.setAuthor(author);
        bookToUpdate.setTitle(title);
        bookToUpdate.setPublisher(publisher);
        bookToUpdate.setPrice(price);

        return bookRepository.save(bookToUpdate);
    }

    @MutationMapping
    public void deleteBookById(@Argument int id) {
        bookRepository.deleteById(id);
    }
}
