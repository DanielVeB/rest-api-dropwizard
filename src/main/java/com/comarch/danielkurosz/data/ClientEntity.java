package com.comarch.danielkurosz.data;

import org.mongodb.morphia.annotations.*;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity("clients")
@Indexes(@Index(fields = { @Field("email")}, options = @IndexOptions(unique = true)))
public class ClientEntity {

    @Id
    private UUID id;
    //@Indexed(options = @IndexOptions(unique = true))
    private String email;
    private String firstName;
    private String lastName;
    private Date creationDate;


    public ClientEntity() {
    }

    // builder pattern
    //-------------------------------------------------------------
    private ClientEntity(ClientEntityBuilder clientEntityBuilder) {
        this.firstName = clientEntityBuilder.firstName;
        this.lastName = clientEntityBuilder.lastName;
        this.email = clientEntityBuilder.email;
        this.creationDate = clientEntityBuilder.creationDate;
        this.id = clientEntityBuilder.id;
    }

    public static class ClientEntityBuilder {
        private String firstName;
        private String lastName;

        private String email;
        private Date creationDate;
        private UUID id;

        public ClientEntityBuilder() {

        }

        public ClientEntityBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ClientEntityBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ClientEntityBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ClientEntityBuilder creationDate(Date creationDate) {
            this.creationDate = creationDate;
            return this;
        }
        //only for tests
        public ClientEntityBuilder uuuid(UUID id) {
            this.id = id;
            return this;
        }

        public ClientEntity build() {
            return new ClientEntity(this);
        }
    }
//    ---------------------------------------------------------------------


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

    public String getCreationDate() {
        if (this.creationDate == null) return null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return dateFormat.format(this.creationDate);
    }
}
