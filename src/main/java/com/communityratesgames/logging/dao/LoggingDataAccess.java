package com.communityratesgames.logging.dao;

import com.communityratesgames.logging.domain.Logging;
import com.communityratesgames.logging.transactions.LoggingDataMethods;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class LoggingDataAccess implements DataAccessLocal, DataAccessRemote {

    @Inject
    private LoggingDataMethods loggingDataMethods;

    @Override
    public Logging addNewLog(Logging log) {return loggingDataMethods.addNewLog(log);}

    @Override
    public List<Logging> showAllLogs() {return loggingDataMethods.showAllLogs();}
}
