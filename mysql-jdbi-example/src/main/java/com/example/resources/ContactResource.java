package com.example.resources;

import com.example.dao.ContactDAO;
import com.example.model.Contact;
import com.example.model.User;
import com.google.inject.Inject;
import io.dropwizard.auth.Auth;
import org.skife.jdbi.v2.DBI;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {
    private final ContactDAO contactDAO;

    @Inject
    public ContactResource(DBI jdbi) {
        this.contactDAO = jdbi.onDemand(ContactDAO.class);
    }

    @GET
    @Path("/{id}")
    public Response getContact(@PathParam("id") int id, @Auth User user) {
        Contact contact = contactDAO.getContactById(id);
        return Response.ok(contact).build();
    }

    @POST
    public Response createContact(@Valid Contact contact, @Auth User user) throws URISyntaxException {
        int newId = contactDAO.createContact(contact.getFirstName(), contact.getLastName(), contact.getPhone());
        return Response.created(new URI("contact/" + String.valueOf(newId))).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") int id, @Auth User user) {
        contactDAO.deleteContact(id);
        return  Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    public Response updateContact(@PathParam("id") int id, @Valid Contact contact, @Auth User user) {
        contactDAO.updateContact(id, contact.getFirstName(), contact.getLastName(), contact.getPhone());
        return Response.ok(new Contact(id, contact.getFirstName(), contact.getLastName(), contact.getPhone())).build();
    }
}
