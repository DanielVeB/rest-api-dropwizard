package com.comarch.danielkurosz.job;

import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.job.impl.ProviderJobLogicImpl;
import com.comarch.danielkurosz.logic.impl.BirthdayProviderLogicImpl;
import org.mongodb.morphia.Datastore;

class ProviderJobLogicFactory {
    static IProviderJobLogic createProvideJobLogic(Datastore datastore){
        return new ProviderJobLogicImpl(new BirthdayProviderLogicImpl(new MongoClientDAO(datastore)));
    }
}
