package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.data.ClientEntity;
import com.comarch.danielkurosz.dto.ClientDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientMapper {

    public ClientDTO mapToClientDTO(ClientEntity clientEntity) {
        return new ClientDTO.ClientDTOBuilder().
                email(clientEntity.getEmail()).
                firstName(clientEntity.getFirstName()).
                lastName(clientEntity.getLastName()).
                creationDate(clientEntity.getCreationDate()).build();
    }

    public ClientEntity mapToClientEntity(ClientDTO clientDTO) {

        Date creationDate = null;
        try {
            if (clientDTO.getCreationDate() != null)
                creationDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(clientDTO.getCreationDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new ClientEntity.ClientEntityBuilder().
                email(clientDTO.getEmail()).
                firstName(clientDTO.getFirstName()).
                lastName(clientDTO.getLastName()).
                creationDate(creationDate).build();
    }

}
