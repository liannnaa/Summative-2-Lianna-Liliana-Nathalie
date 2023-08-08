package com.company.bookstore.controller;

import com.company.bookstore.model.Author;
//import com.company.bookstore.model.Book;
//import com.company.bookstore.model.Publisher;
import com.company.bookstore.repository.AuthorRepository;
//import com.company.bookstore.repository.BookRepository;
//import com.company.bookstore.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;

import java.util.List;
import java.util.Optional;
public class AuthorGraphController {
    @Autowired
    AuthorRepository authorRepository;



    @QueryMapping
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @QueryMapping
    public Author getAuthorById(@Argument int id) {
        Optional<Author> returnVal = authorRepository.findById(id);
        return returnVal.orElse(null);
    }


    @MutationMapping
    public Author addAuthor(
            @Argument String name,
            @Argument String street,
            @Argument String city,
            @Argument String state,
            @Argument String postalCode,
            @Argument String phone,
            @Argument String email) {

        Author newAuthor = new Author();
        newAuthor.setFirstName(name);
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
            @Argument String name,
            @Argument String street,
            @Argument String city,
            @Argument String state,
            @Argument String postalCode,
            @Argument String phone,
            @Argument String email) {

        Author authorToUpdate = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        authorToUpdate.setFirstName(name);
        authorToUpdate.setStreet(street);
        authorToUpdate.setCity(city);
        authorToUpdate.setState(state);
        authorToUpdate.setPostalCode(postalCode);
        authorToUpdate.setPhone(phone);
        authorToUpdate.setEmail(email);

        return authorRepository.save(authorToUpdate);
    }

    @MutationMapping
    public void deletePublisherById(@Argument int id) {
        authorRepository.deleteById(id);
    }

}
