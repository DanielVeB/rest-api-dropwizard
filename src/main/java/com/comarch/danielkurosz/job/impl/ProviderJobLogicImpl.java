package com.comarch.danielkurosz.job.impl;

import com.comarch.danielkurosz.job.IProviderJobLogic;
import com.comarch.danielkurosz.logic.IProviderLogic;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProviderJobLogicImpl implements IProviderJobLogic {

    static final Logger LOGGER = LoggerFactory.getLogger(ProviderJobLogicImpl.class);

    private final IProviderLogic logic;

    public ProviderJobLogicImpl(IProviderLogic logic) {
        this.logic = logic;
    }

    @Override
    public void execute() throws JobExecutionException {
        logic.provide();
    }

    public IProviderLogic getLogic() {
        return logic;
    }
}
