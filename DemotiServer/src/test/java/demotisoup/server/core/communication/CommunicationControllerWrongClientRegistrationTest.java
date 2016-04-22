package demotisoup.server.core.communication;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

/**
 * Author: Rens Groenveld
 */
public class CommunicationControllerWrongClientRegistrationTest {

  private static CommunicationControllerSupport ccs = new CommunicationControllerSupport();
  private static CommunicationController communicationController;
  private static Socket clientSocketWithNoReadOrWriteRegistration;
  private static Socket clientSocketWithNoNameRegistration;
  private static Socket clientSocketWithNoTypeRegistration;
  private static Socket clientSocketWithEmptyStringRegistration;

  @BeforeClass
  public static void beforeClass() throws IOException, InterruptedException {
    communicationController = ccs.setupCommunicationController();
    clientSocketWithNoReadOrWriteRegistration = ccs.setupClientSocket("hallway led;RGBModule;", 1233);
    clientSocketWithNoNameRegistration = ccs.setupClientSocket("RGBModule;w", 1233);
    clientSocketWithNoTypeRegistration = ccs.setupClientSocket("hallway led;w", 1233);
    clientSocketWithEmptyStringRegistration = ccs.setupClientSocket("", 1233);
  }

  @AfterClass
  public static void afterClass() throws IOException {
    clientSocketWithNoReadOrWriteRegistration.close();
    clientSocketWithNoNameRegistration.close();
    clientSocketWithNoTypeRegistration.close();
    clientSocketWithEmptyStringRegistration.close();
  }

  @Test
  public void validClientShouldOnlyBeRegisteredOnce(){
    Assert.assertEquals(0, communicationController.getRegisteredModules().size());
  }
}
