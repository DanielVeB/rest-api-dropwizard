package com.comarch.danielkurosz.resources;

import com.codahale.metrics.annotation.Timed;
import com.comarch.danielkurosz.dto.ClientDTO;
import com.comarch.danielkurosz.service.ClientsService;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/clients")
public class ClientsResource {

    // ClientService - connect REST API with DAO
    private ClientsService clientsService;

    public ClientsResource() {
        clientsService = new ClientsService();
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
    @Path("/get/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ClientDTO> getAll() {
        return clientsService.getAllClients();
    }

    @POST
    @Timed
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void add(ClientDTO clientDTO){
        clientsService.createClient(clientDTO);
    }

}
