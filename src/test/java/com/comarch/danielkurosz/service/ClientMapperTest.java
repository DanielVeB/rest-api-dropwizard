package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.data.ClientEntity;
import com.comarch.danielkurosz.dto.ClientDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ClientMapperTest {

    private ClientMapper clientMapper;

    @Before
    public void init() {
        clientMapper = new ClientMapper();
    }

    @Test(expected = NullPointerException.class)
    public void mapToClientDTO_WhenNullIsPassed() {
        clientMapper.mapToClientDTO(null);
    }

    @Test(expected = NullPointerException.class)
    public void mapToClientEntity_WhenNullIsPassed() {
        clientMapper.mapToClientEntity(null);
    }

    @Test
    public void mapToClientDTO_ReturnClientDTO(){
        ClientEntity clientEntity = new ClientEntity.ClientEntityBuilder().firstName("Daniel").lastName("Kurosz").build();
        clientEntity.setId(UUID.randomUUID());
        ClientDTO clientDTO = clientMapper.mapToClientDTO(clientEntity);
        Assert.assertEquals("client fields be the same",clientEntity.getFirstName(),clientDTO.getFirstName());
        Assert.assertNull("email field should be null", clientDTO.getEmail());
    }

    @Test
    public void mapToClientEntity_WhenDateIsInvalid_ReturnClientWithNewDate(){
        ClientDTO clientDTO = new ClientDTO.ClientDTOBuilder().firstName("Daniel").creationDate("wrong date").build();

        ClientEntity clientEntity = clientMapper.mapToClientEntity(clientDTO);
        Assert.assertEquals("name fields should be the same",clientDTO.getFirstName(),clientEntity.getFirstName());
        Assert.assertNotEquals("date should NOT be the same",clientDTO.getCreationDate(),clientEntity.getCreationDate());
    }
    @Test
    public void mapToClientEntity_WhenEveryDateIsPassed_ReturnClientEntityWithTheSameFields(){
        String date = "2018-02-12T00:00:00Z";
        ClientDTO clientDTO = new ClientDTO.ClientDTOBuilder().firstName("Daniel").
                lastName("Kurosz").email("dnaielkuros@gmail.com").creationDate(date).build();

        ClientEntity clientEntity = clientMapper.mapToClientEntity(clientDTO);
        Assert.assertEquals("name fields should be the same",clientDTO.getFirstName(),clientEntity.getFirstName());
        Assert.assertEquals("date fields should be the same",clientDTO.getCreationDate(),clientEntity.getCreationDate());
    }
}