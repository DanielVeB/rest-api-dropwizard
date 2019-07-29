package com.comarch.danielkurosz.job.zodiac;

import com.comarch.danielkurosz.clients.TagsClient;
import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.job.IProviderJobLogic;
import com.comarch.danielkurosz.job.impl.ProviderJobLogicImpl;
import com.comarch.danielkurosz.logic.impl.ZodiacTagProviderLogicImpl;
import org.mongodb.morphia.Datastore;

class ZodiacTagProviderJobLogicFactory {
    static IProviderJobLogic createProviderJobLogic(TagsClient client, Datastore datastore){
        return new ProviderJobLogicImpl(new ZodiacTagProviderLogicImpl(client, new MongoClientDAO(datastore)));
    }
}
