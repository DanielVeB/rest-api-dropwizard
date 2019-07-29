package com.comarch.danielkurosz.logic.impl;

import com.comarch.danielkurosz.clients.TagsClient;
import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.data.ClientEntity;
import com.comarch.danielkurosz.logic.IProviderLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ZodiacTagProviderLogicImpl implements IProviderLogic {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZodiacTagProviderLogicImpl.class);

    private final TagsClient client;
    private final MongoClientDAO dao;

    public ZodiacTagProviderLogicImpl(TagsClient client, MongoClientDAO dao) {
        this.client = client;
        this.dao = dao;
    }

    @Override
    public void provide() {
        LOGGER.info("Start zodiac job");
        List<ClientEntity> clientEntities;
        int i = 0;
        int numberOfDocuments = 10;
        do {
            clientEntities = dao.get(new ClientEntity(), null, numberOfDocuments, i * numberOfDocuments);
            for (ClientEntity clientEntity : clientEntities) {
                System.out.println(clientEntity.getBirthday());
            }
            i++;
        } while (clientEntities.size() != 0);

        client.addZodiac("1", "1", "1");

    }
}
