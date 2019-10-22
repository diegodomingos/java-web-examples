package com.fullexample.resources;

import com.fullexample.api.ResultCode;
import com.fullexample.api.User;
import com.fullexample.message.MessageSender;
import com.google.inject.Inject;
import com.rabbitmq.tools.json.JSONWriter;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersResource {
    private final MessageSender sender;

    @Inject
    public UsersResource(MessageSender sender) {
        this.sender = sender;
        try {
            sender.init();
        } catch (Exception e) {
            System.out.println("Failed to init message queue: " + e.getMessage());
        }
    }

    @POST
    public ResultCode addUser(User user) {
        JSONWriter jsonWriter = new JSONWriter();
        String message = jsonWriter.write(user);
        try {
            sender.send(message);
            return new ResultCode(200, "User " + user.getLastName() + ", " + user.getFirstName() + " added successfully\n");
        } catch (Exception e) {
            return new ResultCode(500, "Exception: " + e.getMessage());
        }
    }
}
