package com.company.bookstore.controller;

import com.company.bookstore.model.Author;
import com.company.bookstore.repository.AuthorRepository;
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

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

        @Autowired
        private MockMvc mockMvc;
        private ObjectMapper mapper = new ObjectMapper();

        @MockBean
        private AuthorRepository repo;

        private Author author;

        @BeforeEach
        public void setup() {
            author = new Author();
            author.setAuthorId(1);
            author.setFirstName("John");
            author.setLastName("Doe");
            author.setEmail("johndoe@gmail.com");
            author.setStreet("123 St");
            author.setCity("Miami");
            author.setState("FL");
            author.setPostalCode("33167");
            author.setPhone("123-456-7890");
        }

        @Test
        public void addAuthorTest() throws Exception {
            when(repo.save(any(Author.class))).thenReturn(author);

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/authors")
                            .content(mapper.writeValueAsString(author))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.firstName").value("John"))
                    .andExpect(jsonPath("$.lastName").value("Doe"))
                    .andExpect(jsonPath("$.email").value("johndoe@gmail.com"))
                    .andExpect(jsonPath("$.street").value("123 St"))
                    .andExpect(jsonPath("$.city").value("Miami"))
                    .andExpect(jsonPath("$.state").value("FL"))
                    .andExpect(jsonPath("$.postalCode").value("33167"))
                    .andExpect(jsonPath("$.phone").value("123-456-7890"));
        }

        @Test
        public void updateAuthorTest() throws Exception {
            when(repo.save(any(Author.class))).thenReturn(author);

            mockMvc.perform(MockMvcRequestBuilders
                            .put("/authors")
                            .content(mapper.writeValueAsString(author))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }

        @Test
        public void deleteAuthorTest() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/authors/{id}", 1))
                    .andExpect(status().isNoContent());
        }

        @Test
        public void getAuthorsByIdTest() throws Exception {
            when(repo.findById(1)).thenReturn(Optional.of(author));

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/authors/{id}", 1))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName").value("John"))
                    .andExpect(jsonPath("$.lastName").value("Doe"))
                    .andExpect(jsonPath("$.email").value("johndoe@gmail.com"))
                    .andExpect(jsonPath("$.street").value("123 St"))
                    .andExpect(jsonPath("$.city").value("Miami"))
                    .andExpect(jsonPath("$.state").value("FL"))
                    .andExpect(jsonPath("$.postalCode").value("33167"))
                    .andExpect(jsonPath("$.phone").value("123-456-7890"));
        }


        @Test
        public void getAllAuthorsTest() throws Exception {
            when(repo.findAll()).thenReturn(Arrays.asList(author));

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/authors"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].firstName").value("John"))
                    .andExpect(jsonPath("$[0].lastName").value("Doe"))
                    .andExpect(jsonPath("$[0].email").value("johndoe@gmail.com"))
                    .andExpect(jsonPath("$[0].street").value("123 St"))
                    .andExpect(jsonPath("$[0].city").value("Miami"))
                    .andExpect(jsonPath("$[0].state").value("FL"))
                    .andExpect(jsonPath("$[0].postalCode").value("33167"))
                    .andExpect(jsonPath("$[0].phone").value("123-456-7890"));
        }

}