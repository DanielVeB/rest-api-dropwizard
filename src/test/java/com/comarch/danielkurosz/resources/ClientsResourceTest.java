package com.comarch.danielkurosz.resources;

import com.comarch.danielkurosz.auth.AuthUser;
import com.comarch.danielkurosz.dto.ClientDTO;
import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.service.ClientsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ClientsResourceTest {

    @Mock
    private ClientsService clientsService;


    private ClientsResource clientsResource;

    @Before
    public void init() {
        clientsResource = new ClientsResource(clientsService);
    }

    @Test
    public void read_EverythingIsAlright_ThenResponseShouldHaveStatus200() throws AppException {
        List<ClientDTO> clientDTOS = new LinkedList<>();
        when(clientsService.getClients(any(), any(), anyInt(), anyInt())).thenReturn(clientDTOS);
        Response response = clientsResource.read(new AuthUser("daniel"), "Daniel", null, null, null, 1, 1);

        Assert.assertEquals(200, response.getStatus());

    }

    @Test(expected = AppException.class)
    public void read_WhenServiceThrowAppException_ThenThrowAppException() throws AppException {

        when(clientsService.getClients(any(), any(), anyInt(), anyInt())).thenThrow(AppException.class);
        clientsResource.read(new AuthUser("daniel"), "Daniel", null, null, null, 1, 1);

    }


    @Test
    public void create_EverythingIsAlright_ThenResponseShouldHaveStatus200() throws AppException {
        ClientDTO clientDTO = new ClientDTO();
        when(clientsService.createClient(any())).thenReturn(clientDTO);
        Response response = clientsResource.create(new ClientDTO());

        Assert.assertEquals(200, response.getStatus());
    }
    @Test(expected = AppException.class)
    public void create_WhenServiceThrowAppException_ThenThrowAppException()throws AppException{
        when(clientsService.createClient(any())).thenThrow(AppException.class);
        clientsResource.create(new ClientDTO());
    }

    @Test
    public void delete_EverythingIsAlright_ThenResponseShouldHaveStatus200() throws AppException {
        ClientDTO clientDTO = new ClientDTO();
        when(clientsService.deleteClient(any())).thenReturn(clientDTO);
        Response response = clientsResource.delete("1");

        Assert.assertEquals(200, response.getStatus());
    }
    @Test(expected = AppException.class)
    public void delete_WhenServiceThrowAppException_ThenThrowAppException()throws AppException{
        when(clientsService.deleteClient(any())).thenThrow(AppException.class);
        clientsResource.delete("");
    }

    @Test
    public void update_EverythingIsAlright_ThenResponseShouldHaveStatus200() throws AppException {
        ClientDTO clientDTO = new ClientDTO();
        when(clientsService.updateClient(any(), any())).thenReturn(clientDTO);
        Response response = clientsResource.update("id", new ClientDTO());

        Assert.assertEquals(200, response.getStatus());
    }
    @Test(expected = AppException.class)
    public void update_WhenServiceThrowAppException_ThenThrowAppException()throws AppException{
        when(clientsService.updateClient(any(), any())).thenThrow(AppException.class);
        clientsResource.update("id",new ClientDTO());
    }




}