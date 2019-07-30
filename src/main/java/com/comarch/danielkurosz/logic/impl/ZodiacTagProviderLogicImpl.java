package com.comarch.danielkurosz.logic.impl;

import com.comarch.danielkurosz.clients.TagsClient;
import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.data.ClientEntity;
import com.comarch.danielkurosz.dto.ClientTagDTO;
import com.comarch.danielkurosz.dto.Tag;
import com.comarch.danielkurosz.logic.IProviderLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

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

        List<String> clientsId = getClientsWithoutZodiacId();

        for (String clientId : clientsId) {
            LocalDate date = getBirthdayDate(clientId);
            if (date != null) {
                ClientTagDTO clientTag = new ClientTagDTO();
                clientTag.setClientId(clientId);
                updateClientByZodiacTag(clientTag,date);
            }
        }

    }

    private List<String> getClientsWithoutZodiacId() {
        List<String> withoutTag = new LinkedList<>();
        withoutTag.add("zodiac");
        return client.getClientsId(withoutTag, null);
    }

    private LocalDate getBirthdayDate(String clientId) {
        List<ClientEntity> clientEntities = dao.get(new ClientEntity.ClientEntityBuilder().uuuid(UUID.fromString(clientId)).build(), null, 1, 0);
        if (clientEntities.size() != 0) {
            return clientEntities.get(0).getBirthday();
        } else {
            return null;
        }
    }

    private void updateClientByZodiacTag(ClientTagDTO clientTagDTO, LocalDate birthdayDate) {
        String zodiacValue = Zodiac.getZodiac(birthdayDate).value;
        List<Tag> tags = new LinkedList<>();
        tags.add(new Tag("zodiac",zodiacValue));
        clientTagDTO.setTags(tags);
        client.update(clientTagDTO);
    }

    private enum Zodiac {


        ARIES(newDate(3, 21), newDate(4, 19),"aries"),
        TAURUS(newDate(4, 20), newDate(5, 20),"taurus"),
        GEMINI(newDate(5, 21), newDate(6, 20),"gemini"),
        CANCER(newDate(6, 21), newDate(7, 22),"cancer"),
        LEO(newDate(7, 23), newDate(8, 22),"leo"),
        VIRGO(newDate(8, 23), newDate(9, 22),"virgo"),
        LIBRA(newDate(9, 23), newDate(10, 22),"libra"),
        SCORPIO(newDate(10, 23), newDate(11, 21),"scorpio"),
        SAGITTARIUS(newDate(11, 22), newDate(12, 21),"sagittarius"),
        CAPRICORN(newDate(12, 22), newDate(1, 19),"capricorn"),
        AQUARIUS(newDate(1, 20), newDate(2, 18),"aquarius"),
        PISCES(newDate(2, 19), newDate(3, 20),"pisces");

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

        public static Zodiac getZodiac(LocalDate birthday){
            Zodiac prevZodiac = PISCES;
            for(Zodiac zodiac: values()){
                if(birthday.getMonthValue() == zodiac.from.getMonthValue()){
                    if(birthday.getDayOfMonth() >= zodiac.from.getDayOfMonth()){
                        return zodiac;
                    }else {
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


