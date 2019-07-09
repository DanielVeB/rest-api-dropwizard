package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.dto.ClientDTO;

import com.mongodb.DuplicateKeyException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientsServiceTest {

    @Mock
    private MongoClientDAO mongoClientDAO;
    @Mock
    private ClientMapper clientMapper;

    //name convention
    //MethodName_ExpectedBehavior_StateUnderTest

    @Test(expected = InvalidEmailError.class)
    public void createClient_InvalidEmailError_IfAccountIsInvalid()throws InvalidEmailError{
        ClientDTO clientDTO = new ClientDTO.ClientDTOBuilder().firstName("Daniel").lastName("Kurosz").email("invalidemail@").build();

        ClientsService clientsService = new ClientsService(mongoClientDAO,clientMapper);
        clientsService.createClient(clientDTO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClient_IllegalArgumentException_IfAnyParameterOfClientDTOIsMissing() throws InvalidEmailError {
        // clientDTO with missing lastName parameter
        ClientDTO clientDTO = new ClientDTO.ClientDTOBuilder().firstName("Daniel").email("invalidemail@").build();
        ClientsService clientsService = new ClientsService(mongoClientDAO,clientMapper);
        clientsService.createClient(clientDTO);
    }






}