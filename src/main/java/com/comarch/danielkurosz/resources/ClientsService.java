package com.comarch.danielkurosz.resources;

import com.codahale.metrics.annotation.Timed;
import com.comarch.danielkurosz.data.Client;
import com.comarch.danielkurosz.jdbi.MongoClientDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.LinkedList;
import java.util.List;

@Path("/client")
public class ClientsService{

    private MongoClientDAO mongoClientDAO = new MongoClientDAO();


    public ClientsService(){
    }

    @GET
    @Timed
    @Path("/get/email={email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Client getClientByEmail(@PathParam("email") String email){
        return mongoClientDAO.getByEmail(email);
    }
    @GET
    @Timed
    @Path("/get/name={name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> getClientByName(@PathParam("name") String name){
        System.out.println("szukam");
        return mongoClientDAO.getByName(name);
    }

    @GET
    @Timed
    @Path("/get/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client>getAll(){
        return mongoClientDAO.getAll();
    }


    @POST
    @Timed
    @Path("/save")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_JSON})
    public void addPerson(Client client) {
        mongoClientDAO.create(client);
    }






}
