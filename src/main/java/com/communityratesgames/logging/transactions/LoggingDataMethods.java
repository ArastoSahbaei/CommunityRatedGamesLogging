package com.communityratesgames.logging.transactions;

import com.communityratesgames.logging.domain.Logging;
import com.communityratesgames.logging.domain.UserStatistic;

import javax.ejb.Local;
import java.util.List;

@Local
public interface LoggingDataMethods {

    public abstract Logging addNewLog(Logging log);
    public abstract List<Logging> showAllLogs();
    public abstract List<Logging> showStatistic(String name);
    public abstract String findUser(String name);
}
