package com.communityratesgames.logging.transactions;

import com.communityratesgames.logging.domain.Logging;
import com.communityratesgames.logging.domain.UserStatistic;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Stateless
@Default
public class LoggingTransaction implements LoggingDataMethods {

    private final static Logger logger = Logger.getLogger(com.communityratesgames.logging.transactions.LoggingTransaction.class);

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
    public Long showStatistic(Logging name) {
        Long query = em.createQuery("SELECT COUNT(l.recieved) FROM Logging l WHERE l.user = :name", Long.class)
                .setParameter("name", name.getUser())
                .getSingleResult();

        return query;
    }

    @Override
    public String findUser(String name) {
        List<String> results = em.createQuery("SELECT DISTINCT l.user FROM Logging l WHERE l.user LIKE :name",String.class)
                .setParameter("name", name+'%')
                .setMaxResults(5)
                .getResultList();
        return reduceUserList(results);
    }

    private String reduceUserList(List<String> userList) {
        JsonFactory factory = new JsonFactory();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            JsonGenerator jgen = factory.createGenerator(outputStream);
            jgen.writeStartArray();
            for (String user:userList
            ) {
                jgen.writeStartObject();
                jgen.writeObjectField("user",user);
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
