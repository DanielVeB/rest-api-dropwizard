package com.comarch.danielkurosz;

import com.comarch.danielkurosz.auth.AuthUser;
import com.comarch.danielkurosz.clients.TagsClient;
import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.dao.MongoDatabaseConfigurator;
import com.comarch.danielkurosz.exceptions.AppExceptionMapper;
import com.comarch.danielkurosz.exceptions.ConstraintViolationExceptionMapper;
import com.comarch.danielkurosz.health.RestCheck;
import com.comarch.danielkurosz.job.ProviderJobDetailFactory;
import com.comarch.danielkurosz.job.ServiceJobFactory;
import com.comarch.danielkurosz.job.birthday.BirthdayProviderJobDetailFactory;
import com.comarch.danielkurosz.job.zodiac.ZodiacTagProviderJobDetailFactory;
import com.comarch.danielkurosz.resources.ClientsResource;
import com.comarch.danielkurosz.service.ClientMapper;
import com.comarch.danielkurosz.service.ClientsService;
import com.comarch.danielkurosz.service.SortingConverter;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.jersey.errors.EarlyEofExceptionMapper;
import io.dropwizard.jersey.errors.LoggingExceptionMapper;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.setup.Environment;
import org.mongodb.morphia.Datastore;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static com.comarch.danielkurosz.job.ProviderTriggerBuilder.newProviderTrigger;
import static org.quartz.CronScheduleBuilder.cronSchedule;


public class ClientsServiceApp extends Application<ClientServiceConfiguration> {

    private static ClientsService clientsService;
    private Datastore datastore;
    private Scheduler scheduler;
    private static final String QUARTZ_PROPERTIES = "/quartz.properties";


    public static void main(String[] args) throws Exception {
        new ClientsServiceApp().run(args);
    }

    @Override
    public void run(ClientServiceConfiguration configuration, Environment environment) throws SchedulerException {
        ClientMapper clientMapper = new ClientMapper();

        datastore = MongoDatabaseConfigurator.configure();
        MongoClientDAO mongoClientDAO = new MongoClientDAO(datastore);

        SortingConverter sortingConverter = new SortingConverter();

        ((DefaultServerFactory) configuration.getServerFactory()).setRegisterDefaultExceptionMappers(false);
        // Register custom mapper
        environment.jersey().register(new ConstraintViolationExceptionMapper());
        // Restore Dropwizard's exception mappers
        environment.jersey().register(new LoggingExceptionMapper<Throwable>() {
        });
        environment.jersey().register(new JsonProcessingExceptionMapper(true));
        environment.jersey().register(new EarlyEofExceptionMapper());

        TagsClient tagsClient = Feign.builder()
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .requestInterceptor(new BasicAuthRequestInterceptor(configuration.getTagServiceLogin(), configuration.getTagServicePassword()))
                .target(TagsClient.class, "http://localhost:9002");

        clientsService = new ClientsService(mongoClientDAO, clientMapper, sortingConverter, tagsClient);

        final ClientsResource clientsResource = new ClientsResource(clientsService);
        environment.jersey().register(clientsResource);
        environment.jersey().register(new AppExceptionMapper());

        environment.jersey().register(AuthFactory.binder(
                new BasicAuthFactory<>(
                        new ClientsServiceAuthenticator(configuration.getLogin(), configuration.getPassword()),
                        "SECURITY REALM", AuthUser.class)));

        environment.healthChecks().register("template", new RestCheck(configuration.getVersion()));


        // create scheduler with default params
        scheduler = new StdSchedulerFactory().getScheduler();
        ServiceJobFactory serviceJobFactory = new ServiceJobFactory(datastore, tagsClient);
        scheduler.setJobFactory(serviceJobFactory);
        configureBirthdayProviderTrigger(scheduler, "");
        configureZodiacTagProviderTrigger(scheduler, "");
        scheduler.start();

    }


    private static void configureBirthdayProviderTrigger(Scheduler scheduler, String cron) throws SchedulerException {

        ProviderJobDetailFactory factory = new BirthdayProviderJobDetailFactory();
        JobDetail job = factory.createProviderJobDetail("operator ID");

        Trigger trigger = newProviderTrigger("operatorID")
                .startNow()
                .withSchedule(cronSchedule("0/4 * * ? * * *"))
                .forJob(job)
                .build();

        scheduler.scheduleJob(job, trigger);
    }

    private static void configureZodiacTagProviderTrigger(Scheduler scheduler, String cron) throws SchedulerException {
        ProviderJobDetailFactory factory = new ZodiacTagProviderJobDetailFactory();
        JobDetail job = factory.createProviderJobDetail("operator Id");

        Trigger trigger = newProviderTrigger("operatorId")
                .startNow()
                .withSchedule(cronSchedule("0/7 * * ? * * *"))
                .forJob(job)
                .build();

        scheduler.scheduleJob(job, trigger);
    }
}
