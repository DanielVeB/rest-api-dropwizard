package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.data.ClientEntity;
import com.comarch.danielkurosz.dto.ClientDTO;

public class ClientMapper {

    public ClientDTO mapToClientDTO(ClientEntity clientEntity){
        return new ClientDTO(clientEntity.getFirstName(),
                             clientEntity.getLastName(),
                             clientEntity.getEmail(),
                             clientEntity.getCreationDate());
    }

    public ClientEntity mapToClientEntity(ClientDTO clientDTO){
        // TO DO!!!
        return null;
    }
    

}
