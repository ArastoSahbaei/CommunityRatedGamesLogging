package com.communityratesgames.logging.jms;

import com.communityratesgames.logging.dao.DataAccessLocal;
import com.communityratesgames.logging.domain.Logging;
import com.communityratesgames.logging.model.LoggingModel;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(name = "MDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "acknowledgeMode",
                                  propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType",
                                  propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination",
                                  propertyValue = "queue/crg")
})
public class LoggingReceiver implements MessageListener {

    private final static Logger logger = Logger.getLogger(com.communityratesgames.logging.jms.LoggingReceiver.class);

    LoggingModel loggingModel = new LoggingModel();

    @Inject
    DataAccessLocal dal;

    @Resource
    private MessageDrivenContext messageDrivenContext;

/* Instead of REST end point, this will be the "endpoint" for a jms queue. Works like an websocket listener*/
    @Override
    public void onMessage(Message message) {
        TextMessage msg;
        try {
            if ( message instanceof TextMessage ) {
                msg = (TextMessage) message;
                String temp = msg.getBody(String.class);
                Logging log = loggingModel.toEntity(temp);
                dal.addNewLog(log);
            } else {
                logger.warn("Message not a text message!!!!!" + message.getClass().getName());
            }
        } catch (JMSException ex) {
            logger.error("Reciever:  " + ex);
            messageDrivenContext.setRollbackOnly();
        }
    }
}
