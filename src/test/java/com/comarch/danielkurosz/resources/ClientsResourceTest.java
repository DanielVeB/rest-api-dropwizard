package com.comarch.danielkurosz.resources;

import com.comarch.danielkurosz.clients.TagsClient;
import com.comarch.danielkurosz.dto.ClientDTO;
import com.comarch.danielkurosz.dto.UserTagDTO;
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
    @Mock
    private TagsClient client;


    private ClientsResource clientsResource;

    @Before
    public void init() {
        clientsResource = new ClientsResource(clientsService,client);
    }

    @Test
    public void get_EverythingIsAlright_ThenResponseShouldHaveStatus200() throws AppException {
        List<ClientDTO> clientDTOS = new LinkedList<>();
        when(clientsService.getClients(any(), any(), anyInt(), anyInt())).thenReturn(clientDTOS);
        Response response = clientsResource.getClients(true,"Daniel", null, null, null, 1, 1);

        Assert.assertEquals(200, response.getStatus());

    }

    @Test(expected = AppException.class)
    public void get_WhenServiceThrewAppException_ThenThrowAppException() throws AppException {

        when(clientsService.getClients(any(), any(), anyInt(), anyInt())).thenThrow(AppException.class);
        Response response = clientsResource.getClients(true,"Daniel", null, null, null, 1, 1);

    }

    @Test
    public void get_WhenTagsArePassed_ThenSetTagsToClientDTO() throws AppException {
//       // set tag
        List<UserTagDTO> tags = new LinkedList<>();
        UserTagDTO tag = new UserTagDTO(1,"Premium account");
        tag.setTag_id(1);
        tag.setTag_value("Premium account");
        tags.add(tag);

        List<ClientDTO> clients = new LinkedList<>();
        clients.add(new ClientDTO());
        when(clientsService.getClients(any(),any(),anyInt(),anyInt())).thenReturn(clients);
        when(client.tags(any())).thenReturn(tags);

        clientsResource.getClients(true,"Daniel", null, null, null, 1, 1);

        Assert.assertEquals("client should have premium account tag",tag.getTag_value(),clients.get(0).getTags().get(0).getTag_value());


    }


    @Test
    public void create_EverythingIsAlright_ThenResponseShouldHaveStatus200() throws AppException {
        ClientDTO clientDTO = new ClientDTO();
        when(clientsService.createClient(any())).thenReturn(clientDTO);
        Response response = clientsResource.add(new ClientDTO());

        Assert.assertEquals(200, response.getStatus());
    }





    @Test
    public void delete_EverythingIsAlright_ThenResponseShouldHaveStatus200() throws AppException {
        ClientDTO clientDTO = new ClientDTO();
        when(clientsService.deleteClient(any())).thenReturn(clientDTO);
        Response response = clientsResource.delete("1");

        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void update_EverythingIsAlright_ThenResponseShouldHaveStatus200() throws AppException {
        ClientDTO clientDTO = new ClientDTO();
        when(clientsService.updateClient(any(), any())).thenReturn(clientDTO);
        Response response = clientsResource.update("1", new ClientDTO());

        Assert.assertEquals(200, response.getStatus());
    }




}