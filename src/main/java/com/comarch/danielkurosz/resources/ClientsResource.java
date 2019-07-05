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


    private ClientsService clientsService;

    public ClientsResource() {
        clientsService = new ClientsService();
    }

    @GET
    @Timed
    @Path("/get?email={email}")
    @Produces(MediaType.APPLICATION_JSON)
    public ClientDTO getClientByEmail(@PathParam("email") String email) {
        return clientsService.getClientByEmail();
    }

    @GET
    @Timed
    @Path("/get?name={name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ClientDTO> getClientByName(@PathParam("name") String name) {
        return clientsService.getClientsByName();
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
        System.out.println(clientDTO.getCreationDate());
        clientsService.createClient(clientDTO);

    }



}
