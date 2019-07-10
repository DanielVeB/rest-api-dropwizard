package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.dto.ClientDTO;
import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ClientsServiceTest {

    @Mock
    private MongoClientDAO mongoClientDAO;


    @BeforeClass
    public void init(){
        Fongo fongo = new Fongo("test");

        Mongo mongo = fongo.getMongo();


    }

    @Mock
    private ClientMapper clientMapper;




    //name convention
    //MethodName_ExpectedBehavior_StateUnderTest

    @Test(expected = InvalidEmailError.class)
    public void createClient_InvalidEmailError_IfAccountIsInvalid() throws InvalidEmailError {
        ClientDTO clientDTO = new ClientDTO.ClientDTOBuilder().firstName("Daniel").lastName("Kurosz").email("invalidemail@").build();

        ClientsService clientsService = new ClientsService(mongoClientDAO, clientMapper);
        clientsService.createClient(clientDTO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClient_IllegalArgumentException_IfAnyParameterOfClientDTOIsMissing() throws InvalidEmailError {
        // clientDTO with missing lastName parameter
        ClientDTO clientDTO = new ClientDTO.ClientDTOBuilder().firstName("Daniel").email("invalidemail@").build();
        ClientsService clientsService = new ClientsService(mongoClientDAO, clientMapper);
        clientsService.createClient(clientDTO);
    }

    @Test
    public void ShouldReturnCorrectDate() {
        ClientsService clientsService = new ClientsService(mongoClientDAO, clientMapper);
        ClientDTO clientDTO = new ClientDTO.ClientDTOBuilder().firstName("Daniel").lastName("Kurosz").build();
        List<ClientDTO> clients = clientsService.getClients(clientDTO, "creationDate:asc", 1, 0);

        Assert.assertEquals("date should be the same", "2019-07-09 05:12:19.000Z", clients.get(0).getCreationDate());
    }


}