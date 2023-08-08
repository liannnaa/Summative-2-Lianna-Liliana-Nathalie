package com.company.bookstore.controller;

import com.company.bookstore.model.Book;
import com.company.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    BookRepository repo;

    // create a new book
    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook (@RequestBody Book book){
        return repo.save(book);
    }

    // update an existing book
    @PutMapping("/books")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBook(@RequestBody Book book){
        repo.save(book);
    }

    // delete an existing book
    @DeleteMapping("/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable int id){
        repo.deleteById(id);
    }

    // return a specific book by id
    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable int id) {
        Optional<Book> returnVal = repo.findById(id);
        if(returnVal.isPresent()){
            return returnVal.get();
        } else {
            return null;
        }
    }

    // return all books
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return repo.findAll();
    }

    // return all books for a specific author
    @GetMapping("/books/authors/{authorId}")
    public List<Book> getBooksByAuthorId(@PathVariable int authorId) {
        return repo.findByAuthorAuthorId(authorId);
    }
}
