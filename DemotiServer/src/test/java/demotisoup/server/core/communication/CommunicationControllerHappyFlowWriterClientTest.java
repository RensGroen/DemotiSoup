package demotisoup.server.core.communication;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Author: Rens Groenveld
 */
public class CommunicationControllerHappyFlowWriterClientTest {

  private static CommunicationControllerSupport ccs = new CommunicationControllerSupport();
  private static CommunicationController communicationController;
  private static Socket aModuleClientSocket;

  @BeforeClass
  public static void beforeClass() throws IOException, InterruptedException {
    communicationController = ccs.setupCommunicationController();
    aModuleClientSocket = ccs.setupClientSocket("hallway led;RGBModule;r", 1233);
  }

  @AfterClass
  public static void afterClass() throws IOException {
    aModuleClientSocket.close();
  }

  @Test
  public void validClientShouldOnlyBeRegisteredOnce(){
    Assert.assertEquals(1, communicationController.getRegisteredModules().size());
  }

  @Test
  public void clientShouldBeWritable() throws InterruptedException {
    ClientSocketThread cst = new ClientSocketThread(aModuleClientSocket);
    cst.start();
    communicationController.write("hallway led","This is a test message");
    Thread.sleep(1000);
    Assert.assertEquals("This is a test message",cst.message);
  }

  private class ClientSocketThread extends Thread{
    Socket socket;
    String message = "unitialized";
    ClientSocketThread(Socket socket){
      this.socket = socket;
    }

    public void run(){
      DataInputStream in = null;
      try {
        in = new DataInputStream(socket.getInputStream());
        message = in.readUTF();
      } catch (IOException e) {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
    }
  }
}
