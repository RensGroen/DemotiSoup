package demotisoup.server.core.communication.socketmechanism.clientsocket;

import demotisoup.server.core.communication.CommunicationController;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Author: Rens Groenveld
 */
public class ReadFromClientSocket extends Thread implements ClientSocketInterface{

  private Socket client;
  private String name;
  private String type;

  public ReadFromClientSocket(String name, Socket client, String type) {
    this.name = name;
    this.client = client;
    this.type = type;
  }

  public void run(){
    while (true){
      try {
        DataInputStream in = new DataInputStream(client.getInputStream());
        CommunicationController.getInstance().clientReadEvent(name, in.readUTF());
      } catch (IOException e) {
        //TODO: logging
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
    }
  }

  @Override
  public String getClientName() {
    return name;
  }

  @Override
  public String getClientType() {
    return type;
  }
}
