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
public class PublisherGraphController {

    @Autowired
    PublisherRepository publisherRepository;

    @QueryMapping
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    @QueryMapping
    public Publisher getPublisherById(@Argument int id) {
        Optional<Publisher> returnVal = publisherRepository.findById(id);
        return returnVal.orElse(null);
    }

    @MutationMapping
    public Publisher addPublisher(
            @Argument String name,
            @Argument String street,
            @Argument String city,
            @Argument String state,
            @Argument String postalCode,
            @Argument String phone,
            @Argument String email) {

        Publisher newPublisher = new Publisher();
        newPublisher.setName(name);
        newPublisher.setStreet(street);
        newPublisher.setCity(city);
        newPublisher.setState(state);
        newPublisher.setPostalCode(postalCode);
        newPublisher.setPhone(phone);
        newPublisher.setEmail(email);

        return publisherRepository.save(newPublisher);
    }

    @MutationMapping
    public Publisher updatePublisher(
            @Argument int id,
            @Argument String name,
            @Argument String street,
            @Argument String city,
            @Argument String state,
            @Argument String postalCode,
            @Argument String phone,
            @Argument String email) {

        Publisher publisherToUpdate = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));

        publisherToUpdate.setName(name);
        publisherToUpdate.setStreet(street);
        publisherToUpdate.setCity(city);
        publisherToUpdate.setState(state);
        publisherToUpdate.setPostalCode(postalCode);
        publisherToUpdate.setPhone(phone);
        publisherToUpdate.setEmail(email);

        return publisherRepository.save(publisherToUpdate);
    }

    @MutationMapping
    public void deletePublisherById(@Argument int id) {
        publisherRepository.deleteById(id);
    }

}

