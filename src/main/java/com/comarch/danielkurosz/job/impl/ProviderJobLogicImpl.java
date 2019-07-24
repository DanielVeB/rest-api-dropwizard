package com.comarch.danielkurosz.job.impl;

import com.comarch.danielkurosz.job.IProviderJobLogic;
import com.comarch.danielkurosz.logic.IBirthdayProviderLogic;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class ProviderJobLogicImpl implements IProviderJobLogic {

    static final Logger LOGGER = LoggerFactory.getLogger(ProviderJobLogicImpl.class);

    private final IBirthdayProviderLogic logic;

    public ProviderJobLogicImpl(IBirthdayProviderLogic logic) {
        this.logic = logic;
    }

    @Override
    public void execute(String operatorId, Date now) throws JobExecutionException {
        logic.provide(operatorId, now);
    }

    public IBirthdayProviderLogic getLogic() {
        return logic;
    }
}
