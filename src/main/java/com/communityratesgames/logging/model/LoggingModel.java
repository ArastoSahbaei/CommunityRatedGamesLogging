package com.communityratesgames.logging.model;

import com.communityratesgames.logging.domain.Logging;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
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
