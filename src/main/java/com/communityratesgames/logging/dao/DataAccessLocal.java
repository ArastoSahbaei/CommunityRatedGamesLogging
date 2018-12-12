package com.communityratesgames.logging.dao;

import com.communityratesgames.logging.domain.Logging;

import javax.ejb.Local;
import java.util.List;

@Local
public interface DataAccessLocal {

    public Logging addNewLog(Logging log);
    public List<Logging> showAllLogs();

    public Long showStatistic(Logging name);
    public String findUser(String name);
}
