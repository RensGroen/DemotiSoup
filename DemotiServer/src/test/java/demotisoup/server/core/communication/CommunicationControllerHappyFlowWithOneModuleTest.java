package demotisoup.server.core.communication;

import demotisoup.server.core.communication.socketmechanism.clientsocket.WriteToClientSocket;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

/**
 * Author: Rens Groenveld
 */
public class CommunicationControllerHappyFlowWithOneModuleTest {

  private static CommunicationControllerSupport ccs = new CommunicationControllerSupport();
  private static CommunicationController communicationController;
  private static Socket rgbModuleClientSocket;

  @BeforeClass
  public static void beforeClass() throws IOException, InterruptedException {
    communicationController = ccs.setupCommunicationController();
    rgbModuleClientSocket = ccs.setupClientSocket("hallway led;RGBModule;r", 1233);
  }

  @AfterClass
  public static void afterClass() throws IOException {
    rgbModuleClientSocket.close();
  }

  @Test
  public void validClientShouldOnlyBeRegisteredOnce(){
    Assert.assertEquals(1, communicationController.getRegisteredModules().size());
  }

  @Test
  public void validClientShouldRegisterNameCorrectly() throws IOException, InterruptedException {
    Assert.assertEquals("hallway led", communicationController.getRegisteredModule("hallway led").getClientName());
  }

  @Test
  public void validClientShouldRegisterTypeCorrectly() throws IOException, InterruptedException {
    Assert.assertEquals("RGBModule", communicationController.getRegisteredModule("hallway led").getClientType());
  }

  @Test
  public void validClientShouldRegisterAsWriter() throws IOException, InterruptedException {
    Assert.assertTrue(communicationController.getRegisteredModule("hallway led") instanceof WriteToClientSocket);
  }
}
