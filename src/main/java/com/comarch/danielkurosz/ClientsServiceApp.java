package com.comarch.danielkurosz;

import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.dao.MongoDatabaseConfigurator;
import com.comarch.danielkurosz.exceptions.AppExceptionMapper;
import com.comarch.danielkurosz.health.RestCheck;
import com.comarch.danielkurosz.resources.ClientsResource;
import com.comarch.danielkurosz.service.ClientMapper;
import com.comarch.danielkurosz.service.ClientsService;
import com.comarch.danielkurosz.service.SortingConverter;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class ClientsServiceApp extends Application<ClientServiceConfiguration> {

    private static ClientsService clientsService;

    public static void main(String[] args) throws Exception {

        ClientMapper clientMapper = new ClientMapper();
        MongoClientDAO mongoClientDAO = MongoDatabaseConfigurator.configureMongo();
        SortingConverter sortingConverter = new SortingConverter();
        clientsService = new ClientsService(mongoClientDAO, clientMapper, sortingConverter);

        new ClientsServiceApp().run(args);
    }

    @Override
    public void run(ClientServiceConfiguration configuration, Environment environment) {
        final ClientsResource clientsResource = new ClientsResource(clientsService);
        environment.jersey().register(clientsResource);
        environment.jersey().register(new AppExceptionMapper());
        environment.healthChecks().register("template", new RestCheck(configuration.getVersion()));
    }
}
