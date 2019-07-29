package com.comarch.danielkurosz.job.birthday;

import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.job.IProviderJobLogic;
import com.comarch.danielkurosz.job.impl.ProviderJobLogicImpl;
import com.comarch.danielkurosz.logic.impl.BirthdayProviderLogicImpl;
import org.mongodb.morphia.Datastore;

class BirthdayProviderJobLogicFactory {
    static IProviderJobLogic createProviderJobLogic(Datastore datastore){
        return new ProviderJobLogicImpl(new BirthdayProviderLogicImpl(new MongoClientDAO(datastore)));
    }
}
