package demotisoup.server.core.communication;

import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Rens Groenveld
 */
public class CommunicationControllerHappyFlowReaderClientTest {

  private static CommunicationControllerSupport ccs = new CommunicationControllerSupport();
  private static CommunicationController communicationController;
  private static Socket aModuleClientSocket;
  private static IDriver aModuleDriver;

  @BeforeClass
  public static void beforeClass() throws IOException, InterruptedException {
    communicationController = ccs.setupCommunicationController();
    aModuleClientSocket = ccs.setupClientSocket("Camera Hallway;CameraModule;w", 1233);
    aModuleDriver = new AModuleDriver();
  }

  @AfterClass
  public static void afterClass() throws IOException {
    aModuleClientSocket.close();
  }

  @Test
  public void clientShouldBeReadeble() throws InterruptedException {
    communicationController.registerDriver(aModuleDriver);
    ClientSocketThread cst = new ClientSocketThread(aModuleClientSocket);
    Thread.sleep(1000);
    cst.start();
    Thread.sleep(1000);
    Assert.assertEquals("This is a test", ((AModuleDriver)aModuleDriver).getData());
  }

  public static class AModuleDriver implements IDriver{

    private String data;

    public String getData() {
      return data;
    }

    @Override
    public void onEventPublished(String data) {
      this.data = data;
    }

    @Override
    public Class<? extends Enum<?>> getPublishEvents() {
      return null; //not important for our test
    }

    @Override
    public Class<? extends Enum<?>> getListenEvents() {
      return null; //not important for our test
    }

    @Override
    public void executeToClientEvent(String event) {
      //not important for our test
    }

    @Override
    public List<String> getNames() {
      List<String> myList = new ArrayList<>();
      myList.add("Camera Hallway");
      return myList;
    }

    @Override
    public String getType() {
      return "CameraModule";
    }
  }

  private class ClientSocketThread extends Thread{
    Socket socket;
    String message = "This is a test";
    ClientSocketThread(Socket socket){
      this.socket = socket;
    }

    public void run(){
      DataOutputStream out = null;
      try {
        out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(message);
      } catch (IOException e) {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
    }
  }
}
