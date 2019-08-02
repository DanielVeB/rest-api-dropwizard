package com.comarch.danielkurosz.logic.impl;

import com.comarch.danielkurosz.clients.ClientTagsDTO;
import com.comarch.danielkurosz.clients.Tag;
import com.comarch.danielkurosz.clients.TagsClient;
import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.data.ClientEntity;
import com.comarch.danielkurosz.logic.IProviderLogic;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class ZodiacTagProviderLogicImpl implements IProviderLogic {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZodiacTagProviderLogicImpl.class);
    private static final String ZODIAC = "zodiac";
    private static final int LIMIT = 100;


    // creationDate of client who was last updated
    private static Date fromDate = Date.from(LocalDate.parse("January 2, 2010",DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH)).atStartOfDay(ZoneId.of("CET")).toInstant());

    private final TagsClient tagClient;
    private final MongoClientDAO dao;

    public ZodiacTagProviderLogicImpl(TagsClient client, MongoClientDAO dao) {
        this.tagClient = client;
        this.dao = dao;
    }

    @Override
    public void provide() {
        LOGGER.info("Start zodiac job for clients with creationDate greater than: " + fromDate);

        Query<ClientEntity> query = dao.getQuery();

        query.field("birthday").exists();
        query.field("creationDate").greaterThan(fromDate);
        query.order(Sort.ascending("creationDate"));

        List<ClientEntity> lastCreatedClients;
        int i =0;
        do {
            lastCreatedClients = dao.getClients(query, LIMIT, i * LIMIT);
            for (ClientEntity entity : lastCreatedClients) {
                updateClientByZodiac(entity);
                fromDate = entity.getCreationDate();
            }
            i++;
        }while (lastCreatedClients.size()!=0);

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


