package com.comarch.danielkurosz.dao;

import com.comarch.danielkurosz.data.ClientEntity;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class MongoDatabaseConfigurator {


    public static Datastore configure(){
        Datastore datastore;
        Morphia morphia = new Morphia();
        morphia.map(ClientEntity.class);
        datastore = morphia.createDatastore(new MongoClient(), "dropwizard");
        datastore.ensureIndexes();
        return datastore;
    }
}
