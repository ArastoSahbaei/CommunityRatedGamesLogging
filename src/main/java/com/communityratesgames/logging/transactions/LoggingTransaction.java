package com.communityratesgames.logging.transactions;

import com.communityratesgames.logging.domain.Logging;
import com.communityratesgames.logging.domain.UserStatistic;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Stateless
@Default
public class LoggingTransaction implements LoggingDataMethods {

    @PersistenceContext(unitName = "communityratesgameslogging")
    private EntityManager em;

    @Override
    public Logging addNewLog(Logging log) {
        em.persist(log);
        em.flush();
        return log;
    }

    @Override
    public List<Logging> showAllLogs() {
        Query q = em.createNativeQuery("SELECT * FROM logs", Logging.class);
        List<Logging> logs = q.getResultList();
        return logs;
    }

    @Override
    public List<Logging> showStatistic(String name) {
        return null;
    }

    @Override
    public String findUser(String name) {
        List<Logging> results = em.createQuery("SELECT DISTINCT u.user FROM Logging u WHERE u.user LIKE :name",Logging.class)
                .setParameter("name", name+'%')
                .setMaxResults(5)
                .getResultList();
        return showName(results);
    }

    private String showName(List<Logging> userList) {
        JsonFactory factory = new JsonFactory();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            JsonGenerator jgen = factory.createGenerator(outputStream);
            jgen.writeStartArray();
            for (Logging user:userList
            ) {
                jgen.writeStartObject();
                jgen.writeObjectField("name", user.getUser());
                jgen.writeEndObject();
            }
            jgen.writeEndArray();
            jgen.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toString();
    }
}
