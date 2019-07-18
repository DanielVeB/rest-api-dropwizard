package com.comarch.danielkurosz;

import com.comarch.danielkurosz.clients.TagsClient;
import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.dao.MongoDatabaseConfigurator;
import com.comarch.danielkurosz.exceptions.AppExceptionMapper;
import com.comarch.danielkurosz.health.RestCheck;
import com.comarch.danielkurosz.resources.ClientsResource;
import com.comarch.danielkurosz.service.ClientMapper;
import com.comarch.danielkurosz.service.ClientsService;
import com.comarch.danielkurosz.service.SortingConverter;
import com.mongodb.MongoClient;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.gson.GsonDecoder;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.basic.BasicAuthFactory;
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

        TagsClient tagsClient = Feign.builder()
                .decoder(new GsonDecoder())
                .requestInterceptor(new BasicAuthRequestInterceptor(configuration.getTagServiceLogin(),configuration.getTagServicePassword()))
                .target(TagsClient.class,"http://localhost:9002/tags");

        final ClientsResource clientsResource = new ClientsResource(clientsService, tagsClient);
        environment.jersey().register(clientsResource);
        environment.jersey().register(new AppExceptionMapper());

        environment.jersey().register(AuthFactory.binder(
                new BasicAuthFactory<>(
                        new ClientsServiceAuthenticator(configuration.getLogin(), configuration.getPassword()),
                        "SECURITY REALM", Boolean.class)));

        environment.healthChecks().register("template", new RestCheck(configuration.getVersion()));

    }
}
