package com.comarch.danielkurosz.data;


import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.Date;

@Entity("clients")
public class ClientEntity {

    private String email;
    @Id
    @Property("id")
    private ObjectId id;

    private String firstName;
    private String lastName;
    private Date creationDate;


    public ClientEntity(String email, String firstName, String lastName, Date creationDate) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.creationDate = creationDate;
    }

    public ClientEntity(String email, String firstName, String lastName){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.creationDate = new Date();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
