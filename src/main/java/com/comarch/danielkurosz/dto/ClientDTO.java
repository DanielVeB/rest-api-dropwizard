package com.comarch.danielkurosz.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter @Setter
public class ClientDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    @Setter(AccessLevel.NONE)
    private String creationDate;

    public ClientDTO() {
    }

//----------------------------------------------------------------------
//    builder pattern

    private ClientDTO(ClientDTOBuilder clientDTOBuilder) {
        this.id = clientDTOBuilder.id;
        this.firstName = clientDTOBuilder.firstName;
        this.lastName = clientDTOBuilder.lastName;
        this.email = clientDTOBuilder.email;
        this.creationDate = clientDTOBuilder.creationDate;
    }

    public static class ClientDTOBuilder {
        private String id;
        private String firstName;
        private String lastName;
        private String email;
        private String creationDate;

        public ClientDTOBuilder() {

        }
        public ClientDTOBuilder id(String id) {
            this.id = id;
            return this;
        }
        public ClientDTOBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ClientDTOBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ClientDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ClientDTOBuilder creationDate(String creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public ClientDTO build() {
            return new ClientDTO(this);
        }
    }
//    ---------------------------------------------------------------------
    public void setCreationDate(Date creationDate) {
//        ISO 8601 date format: 2019-07-08T08:57:08+00:00
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        this.creationDate = dateFormat.format(creationDate);
    }
}
