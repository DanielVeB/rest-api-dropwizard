package com.comarch.danielkurosz.data;


import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity("clients")
public class ClientEntity {

    @Id
    private UUID id;

    private String email;
    private String firstName;
    private String lastName;
    private Date creationDate;


    public ClientEntity(String email, String firstName, String lastName, String creationDate) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        try {
            this.creationDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(creationDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public ClientEntity(String email, String firstName, String lastName){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public ClientEntity(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCreationDate(){
        if (this.creationDate== null) return null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return dateFormat.format(this.creationDate);
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
