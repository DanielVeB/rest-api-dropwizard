package com.comarch.danielkurosz.dto;

import java.util.Date;

public class ClientDTO {

    private String firstName;
    private String lastName;
    private String email;
    private Date creationDate;


    public ClientDTO(String email, String firstName, String lastName, Date creationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.creationDate = creationDate;
    }

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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
