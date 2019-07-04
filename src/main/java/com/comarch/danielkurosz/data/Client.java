package com.comarch.danielkurosz.data;


//import dev.morphia.annotations.Entity;
//import dev.morphia.annotations.Id;
//import dev.morphia.annotations.Property;
//import org.bson.types.ObjectId;

//@Entity
public class Client {

//
    private String email;  //@Id
//    @Property("id")
//    private ObjectId id;

    private String firstName;
    private String lastName;

    public Client(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

//    public ObjectId getId() {
//        return id;
//    }
//
//    public void setId(ObjectId id) {
//        this.id = id;
//    }

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

}
