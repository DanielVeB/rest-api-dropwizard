package com.comarch.danielkurosz;

import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.health.RestCheck;
import com.comarch.danielkurosz.resources.ClientsResource;
import com.comarch.danielkurosz.service.ClientMapper;
import com.comarch.danielkurosz.service.ClientsService;
import com.mongodb.MongoClient;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class ClientsServiceApp extends Application<ClientServiceConfiguration> {
    private static MongoClientDAO mongoClientDAO;
    private static ClientMapper clientMapper;
    private static ClientsService clientsService;
    private static Datastore datastore;

    public static void main(String[] args) throws Exception {

        clientMapper = new ClientMapper();

        //set up database
        Morphia morphia = new Morphia();
        morphia.mapPackage("com.comarch.danielkurosz.data");
        datastore = morphia.createDatastore(new MongoClient(), "dropwizard");

        mongoClientDAO = new MongoClientDAO(datastore);


        clientsService = new ClientsService(mongoClientDAO, clientMapper);

        new ClientsServiceApp().run(args);
    }

    @Override
    public void run(ClientServiceConfiguration configuration, Environment environment) {
        final ClientsResource clientsResource = new ClientsResource(clientsService);
        environment.jersey().register(clientsResource);

        environment.healthChecks().register("template", new RestCheck(configuration.getVersion()));
    }
}
