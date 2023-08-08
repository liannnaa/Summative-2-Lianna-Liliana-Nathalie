package com.company.bookstore.controller;

import com.company.bookstore.model.Author;
import com.company.bookstore.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AuthorController {
    @Autowired
    AuthorRepository repo;

    //Create Author
    @PostMapping("/authors")
    @ResponseStatus(HttpStatus.CREATED)
    public Author addAuthor(@RequestBody Author author) {
        return repo.save(author);
    }

    //Return All Authors
    @GetMapping("/authors")
    public List<Author> getAllAuthors() {
        return repo.findAll();
    }

    //Read By Author ID
    @GetMapping("/authors/{id}")
    public Author getAuthorById(@PathVariable int id) {
        Optional<Author> returnVal = repo.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }

    //Update Author by ID
    @PutMapping("authors")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAuthor(@RequestBody Author author) {
           repo.save(author);
        }

    @DeleteMapping("authors/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthorById(@PathVariable int id) {
        repo.deleteById(id);

    }
}
