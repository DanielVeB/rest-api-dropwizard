package com.comarch.danielkurosz;

import com.comarch.danielkurosz.health.RestCheck;
import com.comarch.danielkurosz.resources.ClientsService;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class ClientsServiceApp extends Application<ClientServiceConfiguration> {

    public static void main(String[] args) throws Exception {
        new ClientsServiceApp().run(args);
    }

    @Override
    public void run(ClientServiceConfiguration configuration, Environment environment) throws Exception {
        final ClientsService clientsService = new ClientsService();
        environment.jersey().register(clientsService);

        environment.healthChecks().register("template",new RestCheck(configuration.getVersion()));
    }
}
