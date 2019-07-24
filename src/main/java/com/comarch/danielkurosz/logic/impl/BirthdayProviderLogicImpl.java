package com.comarch.danielkurosz.logic.impl;

import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.data.ClientEntity;
import com.comarch.danielkurosz.logic.IBirthdayProviderLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BirthdayProviderLogicImpl implements IBirthdayProviderLogic {

    private static final Logger LOGGER = LoggerFactory.getLogger(BirthdayProviderLogicImpl.class);

    private final MongoClientDAO mongoClientDAO;

    public BirthdayProviderLogicImpl(MongoClientDAO mongoClientDAO) {
        this.mongoClientDAO = mongoClientDAO;
    }

    @Override
    public void provide(String operatorId, Date now) {
        LOGGER.info("Start provide job with operator id {}", operatorId);

        List<ClientEntity> clientEntities;
        int i = 0;
        int numberOfDocuments = 10;
        do {
            clientEntities = mongoClientDAO.get(new ClientEntity(), null, numberOfDocuments, i * numberOfDocuments);
            for (ClientEntity clientEntity : clientEntities) {
                setBirthday(clientEntity);
            }
            i++;
        } while (clientEntities.size() != 0);
    }

    private void setBirthday(ClientEntity clientEntity) {
        if (clientEntity.getBirthday() == null) {
            LOGGER.info("setting birthday date to {}", clientEntity.getEmail());
            LocalDate date = clientEntity.getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            int year = setDateField(date.getYear(), 18, 40);
            int month = Math.abs(setDateField(date.getMonthValue(), 1, 12));
            int day = Math.abs(setDateField(date.getDayOfMonth(), 1, 31));

            LocalDate birthdayDate = LocalDate.of(year, Math.max(1, month), Math.max(1, day));
            clientEntity.setBirthday(birthdayDate);
            mongoClientDAO.update(clientEntity);
        }

    }

    private int setDateField(int firstValue, int from, int to) {
        return firstValue - ThreadLocalRandom.current().nextInt(from, to + 1);
    }

}
