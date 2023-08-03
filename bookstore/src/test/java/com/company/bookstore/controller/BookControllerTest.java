package com.company.bookstore.controller;

import com.company.bookstore.model.Book;
import com.company.bookstore.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private BookRepository repo;

    private Book book;

    @BeforeEach
    public void setup() {
        book = new Book();
        book.setIsbn("Test Isbn");
        book.setPublishDate("Test Date");
        book.setTitle("Test Title");
        book.setPrice(0.00);
    }

    @Test
    public void addBookTest() throws Exception {
        when(repo.save(any(Book.class))).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/books")
                        .content(mapper.writeValueAsString(book))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isbn").value("Test Isbn"))
                .andExpect(jsonPath("$.publishDate").value("Test Date"))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.price").value(0.00));
    }

    @Test
    public void updateBookTest() throws Exception {
        when(repo.save(any(Book.class))).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/books")
                        .content(mapper.writeValueAsString(book))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteBookTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/books/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getBookByIdTest() throws Exception {
        when(repo.findById(1)).thenReturn(Optional.of(book));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/books/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("Test Isbn"))
                .andExpect(jsonPath("$.publishDate").value("Test Date"))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.price").value(0.00));
    }

    @Test
    public void getAllBooksTest() throws Exception {
        when(repo.findAll()).thenReturn(Arrays.asList(book));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isbn").value("Test Isbn"))
                .andExpect(jsonPath("$[0].publishDate").value("Test Date"))
                .andExpect(jsonPath("$[0].title").value("Test Title"))
                .andExpect(jsonPath("$[0].price").value(0.00));
    }

    @Test
    public void getBooksByAuthorIdTest() throws Exception {
        when(repo.findByAuthorAuthorId(1)).thenReturn(Arrays.asList(book));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/books/authors/{authorId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isbn").value("Test Isbn"))
                .andExpect(jsonPath("$[0].publishDate").value("Test Date"))
                .andExpect(jsonPath("$[0].title").value("Test Title"))
                .andExpect(jsonPath("$[0].price").value(0.00));
    }
}
