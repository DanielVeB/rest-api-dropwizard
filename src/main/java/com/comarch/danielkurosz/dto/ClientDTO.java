package com.comarch.danielkurosz.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String creationDate;

    public ClientDTO() {
    }

//----------------------------------------------------------------------
//    builder pattern

    private ClientDTO(ClientDTOBuilder clientDTOBuilder) {
        this.firstName = clientDTOBuilder.firstName;
        this.lastName = clientDTOBuilder.lastName;
        this.email = clientDTOBuilder.email;
        this.creationDate = clientDTOBuilder.creationDate;
    }

    public static class ClientDTOBuilder {
        private String firstName;
        private String lastName;
        private String email;
        private String creationDate;

        public ClientDTOBuilder() {

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
//        ISO 8601 date format: 2019-07-08T08:57:08+00:00
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        this.creationDate = dateFormat.format(creationDate);
    }
}
