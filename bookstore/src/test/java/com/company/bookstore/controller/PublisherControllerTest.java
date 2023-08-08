package com.company.bookstore.controller;

import com.company.bookstore.model.Publisher;
import com.company.bookstore.repository.PublisherRepository;
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

@WebMvcTest(PublisherController.class)
public class PublisherControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private PublisherRepository repo;

    private Publisher publisher;

    @BeforeEach
    public void setup() {
        publisher = new Publisher();
        publisher.setPublisherId(1);
        publisher.setName("Test Name");
        publisher.setStreet("Test Street");
        publisher.setCity("Test City");
        publisher.setState("Test State");
        publisher.setPostalCode("Test Postal Code");
        publisher.setPhone("Test Phone");
        publisher.setEmail("Test Email");

    }

    @Test
    public void addPublisherTest() throws Exception {
        when(repo.save(any(Publisher.class))).thenReturn(publisher);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/publisher")
                        .content(mapper.writeValueAsString(publisher))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.publisherId").value(1))
                .andExpect(jsonPath("$.name").value("Test Name"))
                .andExpect(jsonPath("$.street").value("Test Street"))
                .andExpect(jsonPath("$.city").value("Test City"))
                .andExpect(jsonPath("$.state").value("Test State"))
                .andExpect(jsonPath("$.postalCode").value("Test Postal Code"))
                .andExpect(jsonPath("$.phone").value("Test Phone"))
                .andExpect(jsonPath("$.email").value("Test Email"));
    }

    @Test
    public void updatePublisherTest() throws Exception {
        when(repo.save(any(Publisher.class))).thenReturn(publisher);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/publisher")
                        .content(mapper.writeValueAsString(publisher))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deletePublisherTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/publisher/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getPublisherByIdTest() throws Exception {
        when(repo.findById(1)).thenReturn(Optional.of(publisher));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/publisher/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publisherId").value(1))
                .andExpect(jsonPath("$.name").value("Test Name"))
                .andExpect(jsonPath("$.street").value("Test Street"))
                .andExpect(jsonPath("$.city").value("Test City"))
                .andExpect(jsonPath("$.state").value("Test State"))
                .andExpect(jsonPath("$.postalCode").value("Test Postal Code"))
                .andExpect(jsonPath("$.phone").value("Test Phone"))
                .andExpect(jsonPath("$.email").value("Test Email"));
    }

    @Test
    public void getAllPublisherTest() throws Exception {
        when(repo.findAll()).thenReturn(Arrays.asList(publisher));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/publisher"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].publisherId").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Name"))
                .andExpect(jsonPath("$[0].street").value("Test Street"))
                .andExpect(jsonPath("$[0].city").value("Test City"))
                .andExpect(jsonPath("$[0].state").value("Test State"))
                .andExpect(jsonPath("$[0].postalCode").value("Test Postal Code"))
                .andExpect(jsonPath("$[0].phone").value("Test Phone"))
                .andExpect(jsonPath("$[0].email").value("Test Email"));
    }

}

