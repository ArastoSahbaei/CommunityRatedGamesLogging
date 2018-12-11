package com.communityratesgames.logging.model;

import com.communityratesgames.logging.domain.Logging;

import java.sql.Timestamp;

public class LoggingModel {
    private Long id;
    private Timestamp recieved;
    private String user;

    public LoggingModel() {}

    public Logging toEntity(String input) {
        Logging log = new Logging();

        user = input;
        recieved = log.getTimestamp();

        log.setRecieved(recieved);
        log.setUser(user);
        return log;
    }
}
