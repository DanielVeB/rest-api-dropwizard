package com.comarch.danielkurosz.data;

import org.mongodb.morphia.annotations.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity("clients")
@Indexes(@Index(fields = {@Field("email")}, options = @IndexOptions(unique = true)))
public class ClientEntity {

    @Id
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private Date creationDate;
    private LocalDate birthday;

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
        this.birthday = clientEntityBuilder.birthday;
    }

    public static class ClientEntityBuilder {
        private String firstName;
        private String lastName;

        private String email;
        private Date creationDate;
        private LocalDate birthday;
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

        public ClientEntityBuilder birthday(LocalDate birthday) {
            this.birthday = birthday;
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


    public Date getCreationDate() {
        return creationDate;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
