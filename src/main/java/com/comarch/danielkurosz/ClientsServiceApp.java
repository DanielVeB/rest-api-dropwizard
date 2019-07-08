package com.comarch.danielkurosz;

import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.health.RestCheck;
import com.comarch.danielkurosz.resources.ClientsResource;
import com.comarch.danielkurosz.service.ClientMapper;
import com.comarch.danielkurosz.service.ClientsService;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class ClientsServiceApp extends Application<ClientServiceConfiguration> {
    private static MongoClientDAO mongoClientDAO;
    private static ClientMapper clientMapper;
    private static ClientsService clientsService;

    public static void main(String[] args) throws Exception {

        clientMapper = new ClientMapper();

        mongoClientDAO = new MongoClientDAO();

        clientsService = new ClientsService(mongoClientDAO,clientMapper);
        new ClientsServiceApp().run(args);
    }

    @Override
    public void run(ClientServiceConfiguration configuration, Environment environment){
        final ClientsResource clientsResource = new ClientsResource(clientsService);
        environment.jersey().register(clientsResource);

        environment.healthChecks().register("template", new RestCheck(configuration.getVersion()));
    }
}
