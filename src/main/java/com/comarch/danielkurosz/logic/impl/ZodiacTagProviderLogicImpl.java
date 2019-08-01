package com.comarch.danielkurosz.logic.impl;

import com.comarch.danielkurosz.clients.ClientTagsDTO;
import com.comarch.danielkurosz.clients.Tag;
import com.comarch.danielkurosz.clients.TagsClient;
import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.data.ClientEntity;
import com.comarch.danielkurosz.logic.IProviderLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;

public class ZodiacTagProviderLogicImpl implements IProviderLogic {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZodiacTagProviderLogicImpl.class);
    private static final String ZODIAC = "zodiac";
    private static final int LIMIT = 100;

    private final TagsClient tagClient;
    private final MongoClientDAO dao;

    public ZodiacTagProviderLogicImpl(TagsClient client, MongoClientDAO dao) {
        this.tagClient = client;
        this.dao = dao;
    }

    @Override
    public void provide() {
        LOGGER.info("Start zodiac job");

        Map<UUID, ClientEntity> map = new HashMap<>();

//        Get all clients who have birthday date from clients database
//        TO DO!! Find better way, list of all clients can be too huge
//        idea 1: Get only clients who were created in less than 10 minutes, but what with earlier created clients the zodiac tag has been deleted from
        List<ClientEntity> allClients = getAllClientsWithBirthdayDate();

        List<UUID> clientsIdWithZodiac = getClientsIdWithZodiac();

        for (ClientEntity clientEntity : allClients) {
            map.put(clientEntity.getId(), clientEntity);
        }

        for (UUID id : clientsIdWithZodiac) {
            map.remove(id);
        }

        List<ClientEntity> clientsWithoutZodiac = new LinkedList<>(map.values());

        for (ClientEntity entity : clientsWithoutZodiac) {
            updateClientByZodiac(entity);
        }

    }

    private List<ClientEntity> getAllClientsWithBirthdayDate() {

        List<ClientEntity> allId = new LinkedList<>();
        List<ClientEntity> ids;

        int i = 0;
        do {
            ids = dao.getIds(LIMIT, i * LIMIT);
            allId.addAll(ids);
            i++;
        } while (ids.size() != 0);
        return allId;
    }

    private List<UUID> getClientsIdWithZodiac() {
        List<UUID> allId = new LinkedList<>();
        List<UUID> ids;
        int i = 0;
        do {
            ids = tagClient.getClientsId(ZODIAC, null, LIMIT, i * LIMIT);
            allId.addAll(ids);
            i++;
        } while (ids.size() != 0);
        return allId;
    }

    private void updateClientByZodiac(ClientEntity clientEntity) {
        if (clientEntity.getBirthday() != null) {
            String zodiacValue = Zodiac.getZodiac(clientEntity.getBirthday()).value;
            Tag zodiacTag = new Tag(ZODIAC, zodiacValue);
            List<Tag> tags = new LinkedList<>();
            tags.add(zodiacTag);
            ClientTagsDTO dto = new ClientTagsDTO(clientEntity.getId().toString(), tags);
            tagClient.create(dto);
        }
    }

    private enum Zodiac {


        ARIES(newDate(3, 21), newDate(4, 19), "aries"),
        TAURUS(newDate(4, 20), newDate(5, 20), "taurus"),
        GEMINI(newDate(5, 21), newDate(6, 20), "gemini"),
        CANCER(newDate(6, 21), newDate(7, 22), "cancer"),
        LEO(newDate(7, 23), newDate(8, 22), "leo"),
        VIRGO(newDate(8, 23), newDate(9, 22), "virgo"),
        LIBRA(newDate(9, 23), newDate(10, 22), "libra"),
        SCORPIO(newDate(10, 23), newDate(11, 21), "scorpio"),
        SAGITTARIUS(newDate(11, 22), newDate(12, 21), "sagittarius"),
        CAPRICORN(newDate(12, 22), newDate(1, 19), "capricorn"),
        AQUARIUS(newDate(1, 20), newDate(2, 18), "aquarius"),
        PISCES(newDate(2, 19), newDate(3, 20), "pisces");

        private LocalDate from;
        private LocalDate to;
        private String value;

        Zodiac(LocalDate from, LocalDate to, String value) {
            this.from = from;
            this.to = to;
            this.value = value;
        }

        private static LocalDate newDate(int monthOfYear, int dayOfMonth) {
            return LocalDate.of(1, monthOfYear, dayOfMonth);
        }

        public static Zodiac getZodiac(LocalDate birthday) {
            Zodiac prevZodiac = PISCES;
            for (Zodiac zodiac : values()) {
                if (birthday.getMonthValue() == zodiac.from.getMonthValue()) {
                    if (birthday.getDayOfMonth() >= zodiac.from.getDayOfMonth()) {
                        return zodiac;
                    } else {
                        return prevZodiac;
                    }
                }
                prevZodiac = zodiac;
            }
//          never goes here
            return null;
        }
    }

}


