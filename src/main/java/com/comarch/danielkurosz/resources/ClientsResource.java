package com.comarch.danielkurosz.resources;

import com.codahale.metrics.annotation.Timed;
import com.comarch.danielkurosz.dto.ClientDTO;
import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.service.ClientsService;

import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@Path("/clients")
public class ClientsResource {

    // ClientService - connect REST API with DAO
    private ClientsService clientsService;

    private static final Logger LOGGER = Logger.getLogger(ClientsService.class.getName());

    public ClientsResource(ClientsService clientsService) {
        this.clientsService = clientsService;
    }


    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClients(@QueryParam("firstName") String firstName,
                               @QueryParam("lastName") String lastName,
                               @QueryParam("email") String email,
                               @QueryParam("sortBy") String sortBy,
                               @QueryParam("limit") @Min(0) @DefaultValue("10") int limit,
                               @QueryParam("offset") @Min(0) @DefaultValue("0") int offset) throws AppException {

        ClientDTO clientDTO = new ClientDTO.ClientDTOBuilder().firstName(firstName).
                lastName(lastName).
                email(email).build();

        List<ClientDTO> clientDTOs = clientsService.getClients(clientDTO, sortBy, limit, offset);
        return Response.ok(clientDTOs).build();

    }

    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(ClientDTO clientDTO) throws AppException {
        LOGGER.info("add client");
        ClientDTO client = clientsService.createClient(clientDTO);
        return Response.ok(client).build();
    }

    @PUT
    @Timed
    @Path("/id={id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") String id, ClientDTO clientDTO) throws AppException {
        LOGGER.info("update client, id:" + id);
        ClientDTO client = clientsService.updateClient(clientDTO, id);
        return Response.ok(client).build();
    }

    @DELETE
    @Timed
    @Path("/id={id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") String id) throws AppException {
        LOGGER.info("remove client with id: " + id);
        ClientDTO client = clientsService.deleteClient(id);
        return Response.ok(client).build();

    }

}
