package com.comarch.danielkurosz.resources;

import com.codahale.metrics.annotation.Timed;
import com.comarch.danielkurosz.dto.ClientDTO;
import com.comarch.danielkurosz.service.ClientsService;
import com.comarch.danielkurosz.service.InvalidEmailError;
import com.mongodb.DuplicateKeyException;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    @Produces(MediaType.APPLICATION_JSON)
    public List<ClientDTO> getClients(@QueryParam("firstName")String firstName,
                                      @QueryParam("lastName")String lastName,
                                      @QueryParam("email") String email,
                                      @QueryParam("sortBy")String sortBy,
                                      @QueryParam("limit") int limit,
                                      @QueryParam("offset")int offset){

        ClientDTO clientDTO = new ClientDTO.ClientDTOBuilder().firstName(firstName).
                                                                lastName(lastName).
                                                                email(email).build();
        // example
        //sortBy=name:desc,email:asc
        return clientsService.getClients(clientDTO,sortBy,limit,offset);
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

    @PUT
    @Timed
    @Path("/id={id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ClientDTO update(@PathParam("id")String id, ClientDTO clientDTO){
        return clientsService.updateClient(clientDTO,id);
    }

}
