package unico.web;

import java.util.ArrayList;
import java.util.List;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import com.unico.common.MessageObject;
import com.unico.common.UnicoQueueOperation;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Path("restwebmessage")
public class RestWebService {

  
    private Queue unicoQueue;
  
    private ConnectionFactory unicoQueueFactory;

    public RestWebService() {

        try {
            InitialContext jndi = new InitialContext();
            unicoQueueFactory = (ConnectionFactory) jndi.
                    lookup("java:/ConnectionFactory");
            unicoQueue = (Queue) jndi.lookup("java:jboss/exported/jms/queue/unicoRestQueue");
        } catch (NamingException ex) {
        }

    }

    private UnicoQueueOperation unicoQueueOperation = new UnicoQueueOperation();

    @Path("pushInQueue")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public String push(@FormParam("firstInt") int firstInt, @FormParam("secondInt") int secondInt) {
        MessageObject messageObject = new MessageObject(firstInt, secondInt);
        try {
            unicoQueueOperation.sendMessageObjectToQueue(messageObject, unicoQueue, unicoQueueFactory);
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to add in Queue";
        }
        return "Added To Quese";
    }

    @Path("fetchlist")
    @GET
    @Produces("application/json")
    public List<Integer> list() {
        List result = new ArrayList();
        try
        {
        for (Message message : unicoQueueOperation.readAllMessagesFromQueue(unicoQueue, unicoQueueFactory)) {
            result.add(((MessageObject) ((ObjectMessage) message).getObject()).getFirstInt());
            result.add(((MessageObject) ((ObjectMessage) message).getObject()).getSecondInt());
        }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
