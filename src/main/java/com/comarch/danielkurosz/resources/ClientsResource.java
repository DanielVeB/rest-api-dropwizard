package com.comarch.danielkurosz.resources;

import com.codahale.metrics.annotation.Timed;
import com.comarch.danielkurosz.dto.ClientDTO;
import com.comarch.danielkurosz.service.ClientsService;
import com.comarch.danielkurosz.service.InvalidEmailError;
import com.mongodb.DuplicateKeyException;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/clients")
public class ClientsResource {

    // ClientService - connect REST API with DAO
    private ClientsService clientsService;

    public ClientsResource(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GET
    @Timed
    @Path("/get?email={email}")
    @Produces(MediaType.APPLICATION_JSON)
    public ClientDTO getClientByEmail(@PathParam("email") String email) {
        return clientsService.getClientByEmail(email);
    }

    @GET
    @Timed
    @Path("/get?name={name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ClientDTO> getClientByName(@PathParam("name") String name) {
        return clientsService.getClientsByName(name);
    }

    @GET
    @Timed
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ClientDTO> getAll() {
        return clientsService.getAllClients();
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public List<ClientDTO> getClients(@QueryParam("firstName")String firstName,
                                      @QueryParam("lastName")String lastName,
                                      @QueryParam("email") String email,
                                      @QueryParam("sortBy")List<String>orderBy){
        System.out.println(firstName+"  "+lastName+"    "+orderBy);

        return null;
    }

    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String add(ClientDTO clientDTO){
        try {
            clientsService.createClient(clientDTO);
            return "Client added successfully";
        } catch (InvalidEmailError invalidEmailError) {
            return "Invalid email. Please add correct email";
        } catch (IllegalArgumentException ex){
            return "Please fill all fields";
        } catch (DuplicateKeyException ex){
            return "This email already exists";
        }

    }

}
