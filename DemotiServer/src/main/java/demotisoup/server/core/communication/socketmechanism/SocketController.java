package demotisoup.server.core.communication.socketmechanism;

import demotisoup.server.core.communication.socketmechanism.clientsocket.ReadFromClientSocket;
import demotisoup.server.core.communication.socketmechanism.clientsocket.ClientSocketInterface;
import demotisoup.server.core.communication.socketmechanism.clientsocket.WriteToClientSocket;
import demotisoup.server.core.communication.socketmechanism.exceptions.CannotDecypherNewClientException;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Rens Groenveld
 */
public class SocketController extends Thread{

  private java.net.ServerSocket serverSocket;
  private List<ClientSocketInterface> clients = new ArrayList<>();
  private int port;
  final static Logger logger = Logger.getLogger(SocketController.class);

  public SocketController(int port) throws IOException
  {
    this.port = port;
    serverSocket = new java.net.ServerSocket(port);
  }

  public void run()
  {
    while(true)
    {
      try
      {
        if (serverSocket == null){
          serverSocket = new java.net.ServerSocket(port);
        }
        logger.debug("Waiting for client on port " + serverSocket.getLocalPort() + "...");
        Socket client = serverSocket.accept();
        logger.debug("Just connected to client: " + client.getRemoteSocketAddress());

        //The client needs to state its name and type of communication it wants.
        ClientSocketInterface clientSocket = null;
        try {
          clientSocket = decypherClientRequest(client);
          if (clientSocket instanceof ReadFromClientSocket){
            ((ReadFromClientSocket)clientSocket).start();
          }
          logger.debug("Added a new client: " + clientSocket.getClientName() + ";" + clientSocket.getClientType());
          deleteOldRegisteredClientIfNecessary(clientSocket);
          clients.add(clientSocket);
        } catch (CannotDecypherNewClientException e) {
          logger.error("A client registered but its first request was not according to interface. The right Interface" +
                  " should be name, type and read/write mode, all three seperated by dot comma. Example: 'hallway " +
                  "led;RGBled Module;r'. Use r if the client only reads/recieves information, " +
                  "use w if it only writes/sends information, or use rw if it is doing both reading and writing.",e);
        }
      }catch(SocketTimeoutException s)
      {
        logger.error("Socket timed out!",s);
        //TODO: check which client we are missing
        closeAndDestroySocket();
      }catch(IOException e)
      {
        closeAndDestroySocket();
        logger.error("IOException of one of the sockets", e);
      }
    }
  }

  private void deleteOldRegisteredClientIfNecessary(ClientSocketInterface clientSocket) {
    for (ClientSocketInterface clientSocketInterface : clients){
      if (clientSocketInterface.getClientName().equals(clientSocket.getClientName())){
         clients.remove(clientSocketInterface);
        logger.debug("Removed one of the client sockets: " + clientSocketInterface.getClientName());
      }
    }
  }

  private void closeAndDestroySocket() {
    try {
      serverSocket.close();
      serverSocket = null;
    } catch (IOException e) {
      logger.error("IOException during closing of a socket.",e);
    }
  }

  public List<ClientSocketInterface> getClients(){
    return clients;
  }

  public void write(String name, String data){
    for (ClientSocketInterface clientSocketInterface : clients){
      if (clientSocketInterface.getClientName().equals(name)){
        if (clientSocketInterface instanceof WriteToClientSocket){
          ((WriteToClientSocket)clientSocketInterface).write(data);
        }
      }
    }
  }

  private ClientSocketInterface decypherClientRequest(Socket client) throws CannotDecypherNewClientException {
    DataInputStream in = null;
    try {
      in = new DataInputStream(client.getInputStream());
      String[] data = in.readUTF().split(";");
      if (data.length != 3){
        throw new CannotDecypherNewClientException("Length was unequal to 3");
      }
      String name = data[0];
      String type = data[1];
      String direction = data[2];
      validate(name, type);
      if (getDirection(direction) == SocketDirection.FROM_CLIENT_TO_SERVER){
        return new ReadFromClientSocket(name, client, type);
      } else if (getDirection(direction) == SocketDirection.FROM_SERVER_TO_CLIENT){
        return new WriteToClientSocket(name, client, type);
      } else {
        //TODO: Implement both way Socket
        return null;
      }
    } catch (IOException e) {
      throw new CannotDecypherNewClientException(e.getMessage());
    }
  }

  private void validate(String name, String type) throws CannotDecypherNewClientException {
    if(name == null || name.isEmpty()){
      throw new CannotDecypherNewClientException("name not defined");
    }
    if(type == null || type.isEmpty()){
      throw new CannotDecypherNewClientException("type not defined");
    }
  }

  private SocketDirection getDirection(String direction) throws CannotDecypherNewClientException {
    if (direction.equals("w")){
      return SocketDirection.FROM_CLIENT_TO_SERVER;
    } else if (direction.equals("r")){
      return SocketDirection.FROM_SERVER_TO_CLIENT;
    } else if (direction.equals("rw")){
      return SocketDirection.BOTH_WAYS;
    } else {
      throw new CannotDecypherNewClientException("Cannot determine how to communicatie with the client");
    }
  }
}
