package demotisoup.server.core.communication.socketmechanism.clientsocket;
import org.apache.log4j.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Author: Rens Groenveld
 */
public class WriteToClientSocket implements ClientSocketInterface{
  private Socket client;
  private String name;
  private String type;
  final static Logger logger = Logger.getLogger(WriteToClientSocket.class);

  public WriteToClientSocket(String name, Socket client, String type) {
    this.name = name;
    this.client = client;
    this.type = type;
  }

  public void write(String data){
    DataOutputStream out;
    try {
      out = new DataOutputStream(client.getOutputStream());
      out.writeUTF(data);
      logger.debug("Wrote data: '" + data + "' to " + this.name);
    } catch (IOException e) {
      logger.error("Exception during writing to socket client.", e);
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
