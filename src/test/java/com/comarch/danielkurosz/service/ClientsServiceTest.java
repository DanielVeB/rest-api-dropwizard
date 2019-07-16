package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.data.ClientEntity;
import com.comarch.danielkurosz.dto.ClientDTO;
import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.exceptions.DuplicateEmailException;
import com.mongodb.DuplicateKeyException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ClientsServiceTest {

    @Mock
    private MongoClientDAO mongoClientDAO;
    @Mock
    private ClientMapper clientMapper;
    @Mock
    private SortingConverter sortingConverter;

    private ClientsService clientsService;

    @Before
    public void init() {

        clientsService = new ClientsService(mongoClientDAO, clientMapper, sortingConverter);

    }

    @Test
    public void getClients_ShouldCallMapperToClientEntityOnce() throws AppException {
        ClientDTO clientDTO = new ClientDTO();
        clientsService.getClients(clientDTO, null, 1, 1);
        verify(clientMapper, times(1)).mapToClientEntity(clientDTO);
    }

    @Test(expected = AppException.class)
    public void getClients_WhenSortingConverterThrewAppException_ThenThrowAppException() throws AppException {
        when(sortingConverter.getSorts(any())).thenThrow(AppException.class);
        clientsService.getClients(new ClientDTO(), "string", 1, 1);
    }

    @Test(expected = AppException.class)
    public void createClient_WhenMongoClientDAOThrewDuplicateKeyException_ThenThrowDuplicateEmailException() throws AppException {
        when(mongoClientDAO.create(any())).thenThrow(DuplicateKeyException.class);
        ClientDTO clientDTO = new ClientDTO();
        clientsService.createClient(clientDTO);
    }


    @Test
    public void createClient_WhenEverythingIsAlright_ThenReturnClientDTO() throws AppException {
        ClientEntity clientEntity = new ClientEntity();
        ClientEntity returnClient = new ClientEntity();
        when(clientMapper.mapToClientEntity(any())).thenReturn(clientEntity);
        when(mongoClientDAO.create(clientEntity)).thenReturn(returnClient);
        ClientDTO returnClientDTO = new ClientDTO.ClientDTOBuilder().firstName("DANIEL").build();
        when(clientMapper.mapToClientDTO(returnClient)).thenReturn(returnClientDTO);
        ClientDTO clientDTO = new ClientDTO.ClientDTOBuilder().firstName("DANIEL").
                lastName("Kurosz").
                email("danielkurosz@gmail.com").build();
        ClientDTO expectedDTO = clientsService.createClient(clientDTO);

        Assert.assertEquals("Passed and returned client should be the same", expectedDTO.getFirstName(), clientDTO.getFirstName());
    }

    @Test(expected = AppException.class)
    public void createClient_WhenPassedClientHasAnyEmptyField_ThenThrowAppException() throws AppException {
        ClientDTO clientDTO = new ClientDTO.ClientDTOBuilder().firstName("DANIEL").build();
        clientsService.createClient(clientDTO);
    }

    @Test(expected = AppException.class)
    public void createClient_WhenEmailAlreadyExistInDatabase_ThenThrowAppException() throws AppException {
        ClientDTO clientDTO = new ClientDTO.ClientDTOBuilder().firstName("DANIEL").
                lastName("Kurosz").
                email("danielkurosz@gmail.com").build();
        ClientEntity clientEntity = new ClientEntity.ClientEntityBuilder().firstName("DANIEL").
                lastName("Kurosz").
                email("danielkurosz@gmail.com").build();
        when(clientMapper.mapToClientEntity(any())).thenReturn(clientEntity);
        when(mongoClientDAO.create(any())).thenThrow(DuplicateKeyException.class);
        clientsService.createClient(clientDTO);
    }

    @Test(expected = AppException.class)
    public void updateClient_WhenPassedClientHasAnyEmptyField_ThenThrowAppException() throws AppException {
        ClientDTO clientDTO = new ClientDTO.ClientDTOBuilder().firstName("DANIEL").build();
        clientsService.updateClient(clientDTO, UUID.randomUUID().toString());
    }

    @Test(expected = AppException.class)
    public void updateClient_WhenPassInvalidUUID_ThenThrowAppException() throws AppException {
        ClientDTO clientDTO = new ClientDTO.ClientDTOBuilder().firstName("DANIEL").
                lastName("Kurosz").
                email("danielkurosz@gmail.com").build();
        clientsService.updateClient(clientDTO, "123");
    }

    @Test(expected = AppException.class)
    public void updateClient_WhenUUIDIsInvalid_ThenThrowAppException() throws AppException {
        ClientDTO clientDTO = new ClientDTO.ClientDTOBuilder().firstName("DANIEL").
                lastName("Kurosz").
                email("danielkurosz@gl.com").build();
        clientsService.updateClient(clientDTO, "123");
    }

    @Test(expected = AppException.class)
    public void updateClient_WhenEmailIsInvalid_ThenThrowAppException() throws AppException {
        ClientDTO clientDTO = new ClientDTO.ClientDTOBuilder().firstName("DANIEL").
                lastName("Kurosz").
                email("danielkurosz.com").build();
        clientsService.updateClient(clientDTO, "123");
    }

    @Test(expected = AppException.class)
    public void updateClient_WhenEmailAlreadyExistInDatabase_ThenThrowAppException() throws AppException {
        ClientDTO clientDTO = new ClientDTO.ClientDTOBuilder().firstName("DANIEL").
                lastName("Kurosz").
                email("danielkurosz@gmail.com").build();
        ClientEntity clientEntity = new ClientEntity.ClientEntityBuilder().firstName("DANIEL").
                lastName("Kurosz").
                email("danielkurosz@gmail.com").build();
        when(clientMapper.mapToClientEntity(any())).thenReturn(clientEntity);
        when(mongoClientDAO.update(any())).thenThrow(DuplicateKeyException.class);
        clientsService.updateClient(clientDTO, UUID.randomUUID().toString());
    }

    @Test
    public void updateClient_WhenEverythingIsAlright_ThenReturnClientDTO() throws AppException {
        ClientDTO clientDTO = new ClientDTO.ClientDTOBuilder().firstName("DANIEL").
                lastName("Kurosz").
                email("danielkurosz@gmail.com").build();

        ClientEntity clientEntity = new ClientEntity.ClientEntityBuilder().firstName("DANIEL").
                lastName("Kurosz").
                email("danielkurosz@gmail.com").build();

        when(clientMapper.mapToClientEntity(any())).thenReturn(clientEntity);
        when(mongoClientDAO.update(any())).thenReturn(clientEntity);
        when(clientMapper.mapToClientDTO(any())).thenReturn(clientDTO);

        clientsService.updateClient(clientDTO, UUID.randomUUID().toString());

        verify(clientMapper, times(1)).mapToClientEntity(any());
        verify(mongoClientDAO, times(1)).update(any());
        verify(clientMapper, times(1)).mapToClientDTO(any());

    }

    @Test(expected = AppException.class)
    public void deleteClient_WhenMongoDAOReturnNull_ThenThrowAppException() throws AppException {
        when(mongoClientDAO.delete(any())).thenThrow(NullPointerException.class);
        clientsService.deleteClient(UUID.randomUUID().toString());
    }

    @Test(expected = AppException.class)
    public void deleteClient_WhenPassInvalidUUID_ThenThrowAppException() throws AppException {
        clientsService.deleteClient("1");
    }

    @Test
    public void deleteClient_WhenEverythingIsAlright_ThenReturnDeletedClient() throws AppException {
        ClientEntity clientEntity = new ClientEntity.ClientEntityBuilder().firstName("Daniel").lastName("Kurosz").
                email("danielkurosz@gmail.com").uuuid(UUID.fromString("3822a540-d55f-4e85-8cb5-9d197df6e606")).build();
        when(mongoClientDAO.delete(any())).thenReturn(clientEntity);

        clientsService.deleteClient("3822a540-d55f-4e85-8cb5-9d197df6e606");
        verify(clientMapper, times(1)).mapToClientDTO(clientEntity);

    }

}