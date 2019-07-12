package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.data.ClientEntity;
import com.comarch.danielkurosz.dto.ClientDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientsServiceTest {

    @Mock
    private MongoClientDAO mongoClientDAO;
    @Mock
    private ClientMapper clientMapper;

    private List<ClientEntity> mongoClients;

    private ClientsService clientsService = new ClientsService(mongoClientDAO,clientMapper);

    @BeforeClass
    public void init(){
        mongoClients = new LinkedList<>();
        setUpDatabase();
    }

    @Test
    public void get(){

    }



    //    private methods useful for testing
    private ClientEntity createClient(String name, String lastname, String email) {
        return new ClientEntity.ClientEntityBuilder().
                firstName(name).
                lastName(lastname).
                creationDate(new Date()).
                email(email).
                uuuid(UUID.randomUUID()).build();
    }

    private void setUpDatabase() {
        ClientEntity client1 = createClient("Daniel", "Kurosz", "danielkurosz@gmail.com");
        mongoClients.add(client1);
        ClientEntity client2 = createClient("Ania", "Wójcik", "wojcikania@gmail.com");
        mongoClients.add(client2);
        ClientEntity client3 = createClient("Karolina", "Zielińska", "zielinska@gmail.com");
        mongoClients.add(client3);
        ClientEntity client4 = createClient("Marta", "Dąbrowska", "dąbrowskamarta@gmail.com");
        mongoClients.add(client4);
        ClientEntity client5 = createClient("Michał", "Mazur", "mm@gmail.com");
        mongoClients.add(client5);
    }
}