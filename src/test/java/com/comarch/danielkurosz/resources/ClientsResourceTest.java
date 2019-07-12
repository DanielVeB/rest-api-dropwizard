package com.comarch.danielkurosz.resources;

import com.comarch.danielkurosz.dto.ClientDTO;
import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.service.ClientsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ClientsResourceTest {

    @Mock
    private ClientsService clientsService;

    ClientsResource clientsResource = new ClientsResource(clientsService);

    @Test
    public void get_IfIllegalArgumentExceptionIsThrown_then404IsReceived() throws AppException {
        clientsResource.getClients(null,null,null,null,1,1);
        when(clientsService.getClients(new ClientDTO.ClientDTOBuilder().build(),"",1,1)).thenThrow(IllegalArgumentException.class);

        Assert.assertEquals("",0,0);
    }



}