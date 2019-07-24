package com.comarch.danielkurosz.job;

import org.mongodb.morphia.Datastore;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProviderJob implements Job {

    static final Logger LOGGER = LoggerFactory.getLogger(ProviderJob.class);

    private Datastore datastore;
    private String operatorId;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        IProviderJobLogic logic = ProviderJobLogicFactory.createProvideJobLogic(datastore);
        logic.execute(operatorId,context.getScheduledFireTime());
    }

    public Datastore getDatastore() {
        return datastore;
    }

    public void setDatastore(Datastore datastore) {
        this.datastore = datastore;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
}
