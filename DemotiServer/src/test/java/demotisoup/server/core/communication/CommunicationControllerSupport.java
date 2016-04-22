package demotisoup.server.core.communication;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Author: Rens Groenveld
 */
public class CommunicationControllerSupport {

  static CommunicationController setupCommunicationController()  {
    CommunicationController cc = CommunicationController.getInstance();

    return cc;
  }

  static Socket setupClientSocket(String registerString, int port) {
    String serverName = "localhost";
    Socket client = null;
    try
    {
      client = new Socket(serverName, port);
      OutputStream outToServer = client.getOutputStream();
      DataOutputStream out = new DataOutputStream(outToServer);
      out.writeUTF(registerString);
      //We have to wait for the thread to be finished
      Thread.sleep(1000);
    }catch(IOException e)
    {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return client;
  }
}
