package com.comarch.danielkurosz.dao;

import com.comarch.danielkurosz.data.ClientEntity;
import com.github.fakemongo.Fongo;
import com.google.gson.Gson;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoCommandException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MongoClientDAOTest {

    private MongoClientDAO mongoClientDAO;

    //  useful for get tests
    private UUID CLIENT1UUID;

    @Before
    public void init() {
        Fongo fongo = new Fongo("dropwizardTest");
        Morphia morphia = new Morphia();
        morphia.mapPackage("com.comarch.danielkurosz.data");
        Datastore datastore = morphia.createDatastore(fongo.getMongo(), "dropwizardTest");
        datastore.ensureIndexes();
        mongoClientDAO = new MongoClientDAO(datastore);
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
        mongoClientDAO.create(client1);
        CLIENT1UUID = client1.getId();
        ClientEntity client2 = createClient("Ania", "Wójcik", "wojcikania@gmail.com");
        mongoClientDAO.create(client2);
        ClientEntity client3 = createClient("Karolina", "Zielińska", "zielinska@gmail.com");
        mongoClientDAO.create(client3);
        ClientEntity client4 = createClient("Marta", "Dąbrowska", "dąbrowskamarta@gmail.com");
        mongoClientDAO.create(client4);
        ClientEntity client5 = createClient("Michał", "Mazur", "mm@gmail.com");
        mongoClientDAO.create(client5);
    }

//    name convention:
//    Roy Osherove's naming strategy
//    given-when-then
//    see : https://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html

    @Test
    public void create_IfUniqueEmailNameInDatabase_ThenReturnAddedClient() {
        ClientEntity clientEntity = createClient("Daniel", "Kurosz", "danielkurosz@gmail.com");

        ClientEntity returnClient = mongoClientDAO.create(clientEntity);

        Assert.assertEquals("clients should be the same ", new Gson().toJson(clientEntity), new Gson().toJson(returnClient));


    }

    @Test(expected = DuplicateKeyException.class)
    public void create_IfIsTheSameEmailInDatabase_ThenThrowDuplicateKeyException() throws DuplicateKeyException {
        ClientEntity clientEntity1 = createClient("Daniel", "Kurosz", "danielkurosz@gmail.com");
        mongoClientDAO.create(clientEntity1);
        ClientEntity clientEntity2 = createClient("Daniel", "Kurosz", "danielkurosz@gmail.com");
        mongoClientDAO.create(clientEntity2);
    }

    //    MongoDB throws a different exception in case of an update
    //    Fongo   - MongoCommandException
    //    MongoDb - DuplicateKeyException
    @Test(expected = MongoCommandException.class)
    public void update_IfIsTheSameEmailInDatabase_ThenThrowDuplicateKeyException() throws MongoCommandException {
        ClientEntity clientEntity = createClient("Daniel", "Kurosz", "danielkurosz@gmail.com");
        mongoClientDAO.create(clientEntity);
        ClientEntity clientEntityToUpdate = createClient("Daniel", "Kurosz", "danielkurosz@gmail.pl");
        mongoClientDAO.create(clientEntityToUpdate);
        clientEntityToUpdate.setEmail("danielkurosz@gmail.com");

        mongoClientDAO.update(clientEntityToUpdate);
    }

    @Test
    public void update_IfIsInvalidUUID_ThenReturnNull() {
        ClientEntity clientEntity = createClient("Daniel", "Kurosz", "danielkurosz@gmail.com");
        mongoClientDAO.create(clientEntity);
        ClientEntity clientEntityToUpdate = createClient("Daniel", "Kurosz", "danielkurosz@gmail.pl");
        mongoClientDAO.create(clientEntityToUpdate);
        clientEntityToUpdate.setId(UUID.randomUUID());

        ClientEntity returnClient = mongoClientDAO.update(clientEntityToUpdate);
        Assert.assertNull("should be null because of invalid email", returnClient);
    }

    @Test
    public void update_IfEverythingIsAlright_ThenReturnUpdatedClient() {
        ClientEntity clientEntity = createClient("Daniel", "Kurosz", "danielkurosz@gmail.com");
        mongoClientDAO.create(clientEntity);
        ClientEntity clientEntityToUpdate = createClient("Daniel", "Kurosz", "danielkurosz@gmail.pl");
        mongoClientDAO.create(clientEntityToUpdate);
        clientEntityToUpdate.setFirstName("Jan");
        clientEntityToUpdate.setLastName("Kowalski");
        clientEntityToUpdate.setEmail("jankowalski@wp.pl");

        ClientEntity returnClient = mongoClientDAO.update(clientEntityToUpdate);

        Assert.assertEquals("clients should be the same ", new Gson().toJson(clientEntityToUpdate), new Gson().toJson(returnClient));
    }


    @Test(expected = NullPointerException.class)
    public void delete_IfUserWithThisIdDoesNotExistInDatabase_ThenThrowNullPointerException() throws NullPointerException {
        ClientEntity clientEntity = createClient("Daniel", "Kurosz", "danielkurosz@gmail.com");
        mongoClientDAO.create(clientEntity);
        UUID userID = clientEntity.getId();
        UUID newID;
        do {
            newID = UUID.randomUUID();
        } while (userID.equals(newID));
        mongoClientDAO.delete(newID);
    }

    @Test
    public void delete_IfEverythingIsAlright_ThenReturnDeletedClient() {
        ClientEntity clientEntity = createClient("Daniel", "Kurosz", "danielkurosz@gmail.com");
        mongoClientDAO.create(clientEntity);
        UUID userID = clientEntity.getId();
        ClientEntity returnClient = mongoClientDAO.delete(userID);

        Assert.assertEquals("clients should be the same ", new Gson().toJson(clientEntity), new Gson().toJson(returnClient));

    }

    @Test(expected = NullPointerException.class)
    public void delete_IfDeleteClientWhoHasBeenDeletedBefore_ThenThrowNullPointerException() throws NullPointerException {
        ClientEntity clientEntity = createClient("Daniel", "Kurosz", "danielkurosz@gmail.com");
        mongoClientDAO.create(clientEntity);
        UUID userID = clientEntity.getId();
        mongoClientDAO.delete(userID);

        mongoClientDAO.delete(userID);

    }

    @Test
    public void get_IfGetFirstFiveClientsSortedByEmailDescendingAndThenByNameAscending_ThenCheckFirstClient() {
        //fill table with clients
        setUpDatabase();

        ClientEntity clientEntity = new ClientEntity.ClientEntityBuilder().build();
        HashMap<String, String> sorts = new HashMap<>();
        sorts.put("email", "desc");
        sorts.put("firstName", "asc");
        List<ClientEntity> clients = mongoClientDAO.get(clientEntity, sorts, 5, 0);

        Assert.assertEquals("first client should has name : 'Karolina'", "Karolina", clients.get(0).getFirstName());
    }

    @Test
    public void get_IfGetClientsWithNameWhichDoesNotExist_ReturnEmptyList() {
        //fill table with clients
        setUpDatabase();
        ClientEntity clientEntity = new ClientEntity.ClientEntityBuilder().firstName("Jake").build();
        List<ClientEntity> clients = mongoClientDAO.get(clientEntity, null, 5, 0);

        Assert.assertEquals("list should be empty", 0, clients.size());
    }

    @Test
    public void get_IfGetClientWhoExistsWithPassedFirstNameLastNameAndEmail_ThenReturnedListWithThisOneClient() {
        //fill table with clients
        setUpDatabase();
        ClientEntity clientEntity = new ClientEntity.ClientEntityBuilder().
                firstName("Daniel").
                lastName("Kurosz").
                email("danielkurosz@gmail.com").build();

        List<ClientEntity> clients = mongoClientDAO.get(clientEntity, null, 5, 0);

        Assert.assertEquals("list should has one client", clients.size(), 1);

        Assert.assertEquals("client id should be the same", CLIENT1UUID, clients.get(0).getId());
    }


}