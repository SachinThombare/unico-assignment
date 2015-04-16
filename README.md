# unico-assignment

WebServices
````
  JAX-RS
    class - unico.web.RestWebService
    a. URL to push message - http://{server}:{port}/unico-assignment-0.1/resources/restwebmessage/pushInQueue
    b. URL to fetch list of messages - http://{server}:{port}/unico-assignment-0.1/resources/restwebmessage/fetchlist
````
````
   JAX-WS
  
      class - unico.web.SoapWebService
      
      URL for WSDL - http://{server}:{port}/unico-assignment-0.1/unico-assignment-0.1/SoapWebService?wsdl
````      
 Configuration of message queue
````
    A. For Rest Services
        <jms-queue name="UnicoRestQueue">
            <entry name="jms/queue/unicoRestQueue"/>
            <entry name="java:jboss/exported/jms/queue/unicoRestQueue"/>
        </jms-queue>
````
````
    B. For Soap Services 
        <jms-queue name="UnicoSoapQueue">
            <entry name="jms/queue/unicoSoapQueue"/>
            <entry name="java:jboss/exported/jms/queue/unicoSoapQueue"/>
        </jms-queue>
````
