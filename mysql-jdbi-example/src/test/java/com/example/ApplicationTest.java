package com.example;

import com.example.model.Contact;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;

public class ApplicationTest {
    private Client client;
    private Contact contactForTest = new Contact(0, "John", "Doe", "+987645321");

    @Before
    public void setUp() {
        client = new Client();
        client.addFilter(new HTTPBasicAuthFilter("wsuser", "wspassword"));
    }

    @Test
    public void createAndRetriveContact() {
        //create new contact
        WebResource contactResource = client.resource("http://localhost:8080/contact");
        ClientResponse response = contactResource.type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, contactForTest);
        assertEquals(response.getStatus(), 201);

        String newContactURL = response.getHeaders().get("Location").get(0);
        WebResource newContactResource = client.resource(newContactURL);
        Contact contact = newContactResource.get(Contact.class);
        assertEquals(contact.getFirstName(), contactForTest.getFirstName());
        assertEquals(contact.getLastName(), contactForTest.getLastName());
        assertEquals(contact.getPhone(), contactForTest.getPhone());
    }


}
