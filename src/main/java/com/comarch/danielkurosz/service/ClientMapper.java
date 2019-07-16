package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.data.ClientEntity;
import com.comarch.danielkurosz.dto.ClientDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ClientMapper {

    ClientDTO mapToClientDTO(ClientEntity clientEntity) {

        return new ClientDTO.ClientDTOBuilder().
                id(clientEntity.getId().toString()).
                email(clientEntity.getEmail()).
                firstName(clientEntity.getFirstName()).
                lastName(clientEntity.getLastName()).
                creationDate(clientEntity.getCreationDate()).build();
    }

    ClientEntity mapToClientEntity(ClientDTO clientDTO) {

        Date creationDate = null;
        try {
            if (clientDTO.getCreationDate() != null)
                creationDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(clientDTO.getCreationDate());
        } catch (ParseException e) {
            creationDate = new Date();
        }
        return new ClientEntity.ClientEntityBuilder().
                email(clientDTO.getEmail()).
                firstName(clientDTO.getFirstName()).
                lastName(clientDTO.getLastName()).
                creationDate(creationDate).build();
    }

}
