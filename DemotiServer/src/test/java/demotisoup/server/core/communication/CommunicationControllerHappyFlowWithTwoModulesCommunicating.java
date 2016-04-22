package demotisoup.server.core.communication;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Author: Rens Groenveld
 */
public class CommunicationControllerHappyFlowWithTwoModulesCommunicating {

  private static CommunicationControllerSupport ccs = new CommunicationControllerSupport();
  private static CommunicationController communicationController;
  private static Socket cameraModule;
  private static Socket rgbModule;

  @BeforeClass
  public static void beforeClass() throws IOException, InterruptedException {
    communicationController = ccs.setupCommunicationController();
    cameraModule = ccs.setupClientSocket("camera backgarden;CameraModule;w", 1233);
    rgbModule = ccs.setupClientSocket("hallway led;RGBModule;r", 1233);
  }

  @AfterClass
  public static void afterClass() throws IOException {
    cameraModule.close();
    rgbModule.close();
  }

  @Test
  public void cameraShouldChangePositionAndInReactionHallWayLedShouldActivate() throws InterruptedException {
    communicationController.registerDriver(new RGBDriver());
    communicationController.registerDriver(new CameraDriver());

    //Let's register that if the camera module detects movement, the hallway led has to glow red
    //You don't need to worry about this, as this is done through the app by the user.
    communicationController.registerIfThenEvent(createIfThenEvent());

    //We build the socket that controls the hallway led. It is just waiting for incoming signal
    RGBHallwayClientSocket rgbHcs = new RGBHallwayClientSocket(rgbModule);
    rgbHcs.start();

    //We build the socket that controls the camera. We let it write to the framework.
    //The framework will then pass it on to our CameraDriver, who will translate the message into a
    //movement detected event. Because our camera driver sends this event back to the framework,
    //our rgbdriver will get a signal that it should call its client/device to glow red
    CameraModuleClientSocket cst = new CameraModuleClientSocket(cameraModule);
    cst.writeMessage();
    Thread.sleep(1000);
    //Framework should now have received a movement and passed it on to the hallway led driver
    Assert.assertEquals(rgbHcs.incomingData, "glowRed");
  }

  private IfThenEvent createIfThenEvent() {
    IfThenEvent ifCameraDetectsMovementThenLetHallwayLedGlowRed = new IfThenEvent();
    ifCameraDetectsMovementThenLetHallwayLedGlowRed.setFromModuleName("camera backgarden");
    ifCameraDetectsMovementThenLetHallwayLedGlowRed.setToModuleName("hallway led");
    ifCameraDetectsMovementThenLetHallwayLedGlowRed.setIfValue("MOVEMENT_DETECTED");
    ifCameraDetectsMovementThenLetHallwayLedGlowRed.setThenValue("CHANGE_COLOR_TO_RED");
    return ifCameraDetectsMovementThenLetHallwayLedGlowRed;
  }

  class RGBDriver implements IDriver{

    @Override
    public void onEventPublished(String data) {
      //RGB Led doesn't have any published events
      //(Events that happen at client/device side, i.e. for a camera that it detecs movement)
    }

    @Override
    public Class<? extends Enum<?>> getPublishEvents() {
      return null;
    }

    @Override
    public Class<? extends Enum<?>> getListenEvents() {
      return rgbEnumListenEvents.CHANGE_COLOR_TO_RED.getClass();
    }

    @Override
    public void executeToClientEvent(String event) {
      //The framework will call this event to let de driver know it has to do something
      //The driver should have the intelligence to know what kind of event this is,
      // and react on it. In this case turn the rgb led red
      communicationController.write(this.getName(), "glowRed");
    }

    @Override
    public String getName() {
      return "hallway led";
    }

    @Override
    public String getType() {
      return "RGBModule";
    }
  }

  public enum rgbEnumListenEvents{
    CHANGE_COLOR_TO_RED;
  }

  class CameraDriver implements IDriver{

    @Override
    public void onEventPublished(String data) {
      //We always have to let the framework know what kind of event this was.
      //Maybe the user has defined it as en IfThen event
      //So convert the incoming data to something meaningful for your driver and return an enum.
      //In this test, we know it is a movement detected
      communicationController.stateFromClientEvent(this.getName(), cameraPublishEvents.MOVEMENT_DETECTED);
    }

    @Override
    public Class<? extends Enum<?>> getPublishEvents() {
      return cameraPublishEvents.MOVEMENT_DETECTED.getClass();
    }

    @Override
    public Class<? extends Enum<?>> getListenEvents() {
      return null;
    }

    @Override
    public void executeToClientEvent(String event) {
      //In this test we won't have any events that should happen to the camera
    }

    @Override
    public String getName() {
      return "camera backgarden";
    }

    @Override
    public String getType() {
      return "CameraModule";
    }
  }

  public enum cameraPublishEvents{
    MOVEMENT_DETECTED;
  }

  /**
   * This is a representation of the source code of the camera module.
   * For test purposes, it is fine that it only tells the framework once that it detected movement
   */
  private class CameraModuleClientSocket {
    Socket socket;
    String message = "Camera Detects Movement";
    CameraModuleClientSocket(Socket socket){
      this.socket = socket;
    }

    public void writeMessage(){
      DataOutputStream out = null;
      try {
        out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(message);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Representation of the code that controls the RGB Led in the hallway
   * Could be an arduino, raspberry, wipy or whatever...
   */
  private class RGBHallwayClientSocket extends Thread{
    Socket socket;
    String incomingData;
    RGBHallwayClientSocket(Socket socket){
      this.socket = socket;
    }

    public void run(){
      while (true){
        DataInputStream in = null;
        try {
          in = new DataInputStream(socket.getInputStream());
          incomingData = in.readUTF();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
