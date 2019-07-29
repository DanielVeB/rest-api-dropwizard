package com.comarch.danielkurosz.job.zodiac;

import com.comarch.danielkurosz.clients.TagsClient;
import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.job.IProviderJobLogic;
import org.mongodb.morphia.Datastore;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZodiacTagProviderJob implements Job {

    static final Logger LOGGER = LoggerFactory.getLogger(ZodiacTagProviderJob.class);

    private TagsClient client;
    private Datastore datastore;

    private String operatorId;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        IProviderJobLogic logic = ZodiacTagProviderJobLogicFactory.createProviderJobLogic(client, datastore);
        logic.execute();
    }

    public TagsClient getClient() {
        return client;
    }

    public void setClient(TagsClient client) {
        this.client = client;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Datastore getDatastore() {
        return datastore;
    }

    public void setDatastore(Datastore datastore) {
        this.datastore = datastore;
    }
}
