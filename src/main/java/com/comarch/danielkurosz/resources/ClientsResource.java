package com.comarch.danielkurosz.resources;

import com.codahale.metrics.annotation.Timed;
import com.comarch.danielkurosz.auth.AuthUser;
import com.comarch.danielkurosz.clients.Tag;
import com.comarch.danielkurosz.dto.ClientDTO;
import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.service.ClientsService;
import io.dropwizard.auth.Auth;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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


    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@NotNull @Valid ClientDTO clientDTO) throws AppException {

        LOGGER.info("add client");
        ClientDTO resultclientDTO = clientsService.createClient(clientDTO);
        return Response.ok(resultclientDTO).build();
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClients(@Auth AuthUser authUser,
                               @QueryParam("firstName") String firstName,
                               @QueryParam("lastName") String lastName,
                               @QueryParam("email") String email,
                               @QueryParam("sortBy") String sortBy,
                               @QueryParam("limit") @Min(0) @DefaultValue("10") int limit,
                               @QueryParam("offset") @Min(0) @DefaultValue("0") int offset) throws AppException {
        LOGGER.info("get clients");

        ClientDTO clientDTO = new ClientDTO.ClientDTOBuilder().firstName(firstName).
                lastName(lastName).
                email(email).build();

        List<ClientDTO> clientsDTO = clientsService.getClients(clientDTO, sortBy, limit, offset);
        return Response.ok(clientsDTO).build();
    }

    @GET
    @Timed
    @Path("/client={id}/tags")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTagsForClient(@Auth AuthUser authUser,
                                     @PathParam("id") String clientId,
                                     @QueryParam("tag_id") String tagId,
                                     @QueryParam("tag_value") String tagValue,
                                     @QueryParam("limit") @Min(0) @DefaultValue("10") int limit,
                                     @QueryParam("offset") @Min(0) @DefaultValue("0") int offset) {

        LOGGER.info("get tags for client with id: " + clientId);
        clientsService.getTagsForClient(clientId, new Tag(tagId, tagValue), limit, offset);
        return null;
    }


    @PUT
    @Timed
    @Path("/id={id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") String id, @NotNull @Valid ClientDTO clientDTO) throws AppException {

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
