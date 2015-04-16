package com.unico.common;

import java.util.Collections;
import java.util.List;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
 
public class UnicoQueueOperation {
    
    
    private Connection getConnection(ConnectionFactory unicoQueueFactory) throws JMSException {
        return unicoQueueFactory.createConnection();
    }

    private void closeSession(Connection connection, Session session) throws JMSException {
        if (session != null) {
            session.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    public void sendMessageObjectToQueue(MessageObject message, Queue myQueue, ConnectionFactory myQueueFactory) throws Exception {
        Connection connection = null;
        Session session = null;
        try {
            connection = getConnection(myQueueFactory);
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer publisher = session.createProducer((Destination) myQueue);
            connection.start();
            ObjectMessage objectMessage = session.createObjectMessage(message);
            publisher.send(objectMessage);
        } finally {
            closeSession(connection, session);
        }
    }

    
        public void sendGcdToQueue(Integer calculatedGcd, Queue myQueue, ConnectionFactory myQueueFactory)  {
        Connection connection = null;
        Session session = null;
        try {
            connection = getConnection(myQueueFactory);
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer publisher = session.createProducer((Destination) myQueue);
            connection.start();
            ObjectMessage objectMessage = session.createObjectMessage(calculatedGcd);
            publisher.send(objectMessage);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            try{
                 closeSession(connection, session);
             }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public List<Message> readAllMessagesFromQueue(Queue myQueue, ConnectionFactory myQueueFactory) {
        Connection connection = null;
        Session session = null;
        try {
            connection = getConnection(myQueueFactory);
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueBrowser browser = session.createBrowser(myQueue);
            return Collections.list(browser.getEnumeration());

        }         
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            try{
                 closeSession(connection, session);
             }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

 
    public Message readMessageFromQueue(Queue myQueue, ConnectionFactory myQueueFactory){
        Connection connection = null;
        Session session = null;
        try {
            connection = getConnection(myQueueFactory);
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination dest = (Destination) myQueue;
            MessageConsumer consumer = session.createConsumer(dest);
            connection.start();
            return consumer.receive(1);
        }   catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            try{
                 closeSession(connection, session);
             }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

}
