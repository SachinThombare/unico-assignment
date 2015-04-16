package unico.web;

import java.util.ArrayList;
import java.util.List;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jws.WebMethod;
import javax.jws.WebService;
import com.unico.common.MessageObject;
import com.unico.common.UnicoQueueOperation;
import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@WebService
public class SoapWebService {
    
    
      public SoapWebService() {

        try {
            InitialContext jndi = new InitialContext();
            unicoQueueFactory = (ConnectionFactory) jndi.
                    lookup("java:/ConnectionFactory");
            unicoSoapQueue = (Queue) jndi.lookup("java:jboss/exported/jms/queue/unicoSoapQueue");
            unicoRestQueue = (Queue) jndi.lookup("java:jboss/exported/jms/queue/unicoRestQueue");
        } catch (NamingException ex) {
        }

    }

    private Queue unicoRestQueue;
 
    private ConnectionFactory unicoQueueFactory;

    private Queue unicoSoapQueue;

    private UnicoQueueOperation unicoQueueOperation = new UnicoQueueOperation();

    @WebMethod
    public int gcd() {
        Message message = unicoQueueOperation.readMessageFromQueue(unicoRestQueue, unicoQueueFactory);
      try
      {
        if (message != null) {
            int calculatedgcd = findGCD(((MessageObject) ((ObjectMessage) message).getObject()).getFirstInt(), 
                    ((MessageObject) ((ObjectMessage) message).getObject()).getSecondInt());
            unicoQueueOperation.sendGcdToQueue(calculatedgcd, unicoSoapQueue, unicoQueueFactory);
            return calculatedgcd;
        }
      }
      catch(JMSException e)
      {
          e.printStackTrace();
      }
        return 0;
    }

    @WebMethod
    public List<Integer> gcdList() {
        List<Integer> result = new ArrayList();
        try
        {
        for (Message message : unicoQueueOperation.readAllMessagesFromQueue(unicoSoapQueue, unicoQueueFactory)) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            result.add((Integer) objectMessage.getObject());
        }
        }
        catch(JMSException e)
        {
            e.printStackTrace();
        }
             return result;
    }

    @WebMethod
    public int gcdSum(){
        int sum = 0;
        try
        {
        for (Message message : unicoQueueOperation.readAllMessagesFromQueue(unicoSoapQueue, unicoQueueFactory)) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            sum += (Integer) objectMessage.getObject();
        }        
        }
        catch(JMSException e)
        {
            e.printStackTrace();
        }
        return sum;
    }

    private int findGCD(int first, int second) {
        if (second == 0) {
            return first;
        }
        return findGCD(second, first % second);
    }
}
