package com.company.bookstore.controller;

import com.company.bookstore.model.Author;
//import com.company.bookstore.model.Book;
//import com.company.bookstore.model.Publisher;
import com.company.bookstore.model.Book;
import com.company.bookstore.repository.AuthorRepository;
//import com.company.bookstore.repository.BookRepository;
//import com.company.bookstore.repository.PublisherRepository;
import com.company.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class AuthorGraphController {
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @QueryMapping
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @QueryMapping
    public Author getAuthorById(@Argument int id) {
        Optional<Author> returnVal = authorRepository.findById(id);
        return returnVal.orElse(null);
    }

    @QueryMapping
    public List<Book> getBooksByAuthorId(@Argument int authorId) {
        return bookRepository.findByAuthorAuthorId(authorId);
    }

    @MutationMapping
    public Author addAuthor(
            @Argument String firstName,
            @Argument String lastName,
            @Argument String street,
            @Argument String city,
            @Argument String state,
            @Argument String postalCode,
            @Argument String phone,
            @Argument String email) {

        Author newAuthor = new Author();
        newAuthor.setFirstName(firstName);
        newAuthor.setLastName(lastName);
        newAuthor.setStreet(street);
        newAuthor.setCity(city);
        newAuthor.setState(state);
        newAuthor.setPostalCode(postalCode);
        newAuthor.setPhone(phone);
        newAuthor.setEmail(email);

        return authorRepository.save(newAuthor);
    }

    @MutationMapping
    public Author updateAuthor(
            @Argument int id,
            @Argument String firstName,
            @Argument String lastName,
            @Argument String street,
            @Argument String city,
            @Argument String state,
            @Argument String postalCode,
            @Argument String phone,
            @Argument String email) {

        Author authorToUpdate = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        authorToUpdate.setFirstName(firstName);
        authorToUpdate.setLastName(lastName);
        authorToUpdate.setStreet(street);
        authorToUpdate.setCity(city);
        authorToUpdate.setState(state);
        authorToUpdate.setPostalCode(postalCode);
        authorToUpdate.setPhone(phone);
        authorToUpdate.setEmail(email);

        return authorRepository.save(authorToUpdate);
    }

    @MutationMapping
    public void deleteAuthorById(@Argument int id) {
        authorRepository.deleteById(id);
    }

}
