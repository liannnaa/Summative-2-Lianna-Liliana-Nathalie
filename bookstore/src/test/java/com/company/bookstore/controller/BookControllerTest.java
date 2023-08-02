package com.company.bookstore.controller;

import com.company.bookstore.model.Author;
import com.company.bookstore.model.Book;
import com.company.bookstore.model.Publisher;
import com.company.bookstore.repository.AuthorRepository;
import com.company.bookstore.repository.BookRepository;
import com.company.bookstore.repository.PublisherRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    BookRepository repo;

    @Autowired
    AuthorRepository authRepo;

    @Autowired
    PublisherRepository pubRepo;

    private Book book;

    @BeforeEach
    public void setUp() {
        repo.deleteAll();
        authRepo.deleteAll();
        pubRepo.deleteAll();

        // Arrange
        book = new Book();

        Author author = new Author();
        author.setFirstName("Test First");
        author.setLastName("Test Last");
        author = authRepo.save(author);

        Publisher publisher = new Publisher();
        publisher.setName("Test Name");
        publisher = pubRepo.save(publisher);

        book.setIsbn("Test Isbn");
        book.setPublishDate("Test Date");
        book.setAuthor(author);
        book.setTitle("Test Title");
        book.setPublisher(publisher);
        book.setPrice(0.00);
        book = repo.save(book);

        System.out.println("Book ID is: " + book.getBookId());
    }

    @Test
    public void whenFindBookById_thenReturnBook() throws Exception {
        String query = "{ \"query\": \"{ findBookById(id: " + book.getBookId() + ") { bookId title isbn publishDate author { authorId firstName lastName } publisher { publisherId name } price } }\"}";

        RequestBuilder request = MockMvcRequestBuilders
                .post("/graphql")
                .content(query)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        // Validate the response using ObjectMapper
        String responseContent = result.getResponse().getContentAsString();
        JsonNode responseJson = objectMapper.readTree(responseContent);

        // Validate the fields in the response
        assertEquals("Test Title", responseJson.path("data").path("findBookById").path("title").asText());
        assertEquals("Test Isbn", responseJson.path("data").path("findBookById").path("isbn").asText());
        assertEquals("Test First", responseJson.path("data").path("findBookById").path("author").path("firstName").asText());
        assertEquals("Test Name", responseJson.path("data").path("findBookById").path("publisher").path("name").asText());
        assertEquals(0.00, responseJson.path("data").path("findBookById").path("price").asDouble());
    }

    @Test
    public void whenFindBooksByAuthorId_thenReturnBooks() throws Exception {
        String query = "{" +
                "  \"query\": \"{ findBooksByAuthorId(authorId: 1) { bookId title isbn publishDate author { id firstName lastName } publisher { id name } price } }\"" +
                "}";

        RequestBuilder request = MockMvcRequestBuilders
                .post("/graphql")
                .content(query)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        // Validate the response using ObjectMapper
        String responseContent = result.getResponse().getContentAsString();
        JsonNode responseJson = objectMapper.readTree(responseContent);

        // Validate that the returned list is not empty
        assertTrue(responseJson.path("data").path("findBooksByAuthorId").isArray());
        assertTrue(responseJson.path("data").path("findBooksByAuthorId").size() > 0);
    }

    @Test
    public void whenAddBook_thenCreateBook() throws Exception {
        String mutation = "{" +
                "  \"query\": \"mutation { addBook(isbn: \\\"1234567890\\\", publishDate: \\\"2023-08-01\\\", authorId: 1, title: \\\"Test Book\\\", publisherId: 1, price: 19.99) { bookId title isbn publishDate author { id firstName lastName } publisher { id name } price } }\"" +
                "}";

        RequestBuilder request = MockMvcRequestBuilders
                .post("/graphql")
                .content(mutation)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        // Validate the response using ObjectMapper
        String responseContent = result.getResponse().getContentAsString();
        JsonNode responseJson = objectMapper.readTree(responseContent);

        // Validate the fields in the response
        assertEquals("Test Book", responseJson.path("data").path("addBook").path("title").asText());
        assertEquals("1234567890", responseJson.path("data").path("addBook").path("isbn").asText());
        assertEquals("Test First", responseJson.path("data").path("addBook").path("author").path("firstName").asText());
        assertEquals("Test Name", responseJson.path("data").path("addBook").path("publisher").path("name").asText());
        assertEquals(19.99, responseJson.path("data").path("addBook").path("price").asDouble());
    }

    @Test
    public void whenUpdateBook_thenUpdateExistingBook() throws Exception {
        String mutation = "{" +
                "  \"query\": \"mutation { updateBook(id: 1, isbn: \\\"1234567890\\\", publishDate: \\\"2023-08-01\\\", authorId: 1, title: \\\"Updated Book\\\", publisherId: 1, price: 19.99) { bookId title isbn publishDate author { id firstName lastName } publisher { id name } price } }\"" +
                "}";

        RequestBuilder request = MockMvcRequestBuilders
                .post("/graphql")
                .content(mutation)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        // Validate the response using ObjectMapper
        String responseContent = result.getResponse().getContentAsString();
        JsonNode responseJson = objectMapper.readTree(responseContent);

        // Validate the fields in the response
        assertEquals("Updated Book", responseJson.path("data").path("updateBook").path("title").asText());
        assertEquals("1234567890", responseJson.path("data").path("updateBook").path("isbn").asText());
        assertEquals("Stephen", responseJson.path("data").path("updateBook").path("author").path("firstName").asText());
        assertEquals("McGraw-Hill", responseJson.path("data").path("updateBook").path("publisher").path("name").asText());
        assertEquals(19.99, responseJson.path("data").path("updateBook").path("price").asDouble());
    }

    @Test
    public void whenDeleteBookById_thenDeleteBook() throws Exception {
        String mutation = "{" +
                "  \"query\": \"mutation { deleteBookById(id: 1) }\"" +
                "}";

        RequestBuilder request = MockMvcRequestBuilders
                .post("/graphql")
                .content(mutation)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        // Validate the response using ObjectMapper
        String responseContent = result.getResponse().getContentAsString();
        JsonNode responseJson = objectMapper.readTree(responseContent);

        // Validate that the book was deleted successfully
        assertTrue(responseJson.path("data").path("deleteBookById").asBoolean());
    }
}