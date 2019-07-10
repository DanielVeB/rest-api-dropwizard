package com.comarch.danielkurosz.resources;

import com.codahale.metrics.annotation.Timed;
import com.comarch.danielkurosz.dto.ClientDTO;
import com.comarch.danielkurosz.service.ClientsService;
import com.comarch.danielkurosz.service.InvalidEmailError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @Produces(MediaType.APPLICATION_JSON)
    public List<ClientDTO> getClients(@QueryParam("firstName") String firstName,
                                      @QueryParam("lastName") String lastName,
                                      @QueryParam("email") String email,
                                      @QueryParam("sortBy") String sortBy,
                                      @QueryParam("limit") int limit,
                                      @QueryParam("offset") int offset) {

        ClientDTO clientDTO = new ClientDTO.ClientDTOBuilder().firstName(firstName).
                lastName(lastName).
                email(email).build();

        return clientsService.getClients(clientDTO, sortBy, limit, offset);
    }

    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String add(ClientDTO clientDTO) {
        try {
            clientsService.createClient(clientDTO);
            return "Client added successfully";
        } catch (InvalidEmailError ex) {
            return "Invalid email. Please add correct email";
        } catch (IllegalArgumentException ex) {
            return "Please fill all fields";
        } catch (DuplicateKeyException ex) {
            return "This email already exists";
        }
    }

    @PUT
    @Timed
    @Path("/id={id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") String id, ClientDTO clientDTO) {
        String json;
        try {
            ClientDTO client = clientsService.updateClient(clientDTO, id);

            //map clientDTO to json in string format
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(client);

        } catch (DuplicateKeyException ex) {
            json = "{\"message\" : \"This email already exists\"}";
        } catch (JsonProcessingException ex) {
            json = "{\"message\": \"Cannot parse object to json type\"}";
        } catch (InvalidEmailError ex) {
            json = "{\"message\": \"This email is incorrect\"}";
        } catch (NullPointerException ex) {
            json = "{\"message\": \"Wrong client id\"}";
        } catch (IllegalArgumentException ex) {
            json = "{\"message\" : \"Missing fields\"}";
        }
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Timed
    @Path("/id={id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") String id) {
        String json;
        try {
            ClientDTO client = clientsService.deleteClient(id);

            //map clientDTO to json in string format
            ObjectMapper mapper = new ObjectMapper();
            json = "{\"message\": \"Client removed\"}";
            json += mapper.writeValueAsString(client);

        } catch (JsonProcessingException ex) {
            json = "{\"message\": \"Cannot parse object to json type\"}";
        } catch (NullPointerException ex) {
            json = "{\"message\": \"Wrong client id\"}";
        }
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

}
