package demotisoup.server.core.communication;

import java.util.List;

/**
 * Author: Rens Groenveld
 *
 * Every driver implementation has to implement this interface and register it to the framework.
 */
public interface IDriver {

  /**
   * Will be called whenever a client/device such as arduino, raspberri, software module, etc
   * publishes a change. For example, a camera that detects movement.
   * @param data the data sent by the client/device in textual form
   */
  public void onEventPublished(String data);

  /**
   * The framework needs to register the kind of publish events this driver has,
   * so it can be passed to the user. I.e. a camera that detects change could be: CAMERA_MOVEMENT_DETECTED.
   * Notice the syntax. The framework will cut it into a nice user representable: 'Camera movement detected'
   * @return You should return an Enum. The framework will iterate over the Enum items and takes the value per enum
   * as an identifier, but also as a value to pass to the user.
   */
  public Class<? extends Enum<?>> getPublishEvents();

  /**
   * The framework needs to register the kind of events the module listens to,
   * so it can be passed to the user. I.e. a camera that turns right could be: CAMERA_TURN_RIGHT.
   * Notice the syntax. The framework will cut it into a nice user representable: 'Camera turn right'
   * @return You should return an Enum. The framework will iterate over the Enum items and takes the value per enum
   * as an identifier, but also as a value to pass to the user.
   */
  public Class<? extends Enum<?>> getListenEvents();

  /**
   * This method will be triggered if there is an if-then chain defined by the user for your driver.
   * For example: Imagine you have a RGB led driver. User could define a rule where it wants to change the color of
   * your device whenever the camera module detecs movement.
   * @param e --The Enum initially provided by method getListenEvents(), but now selected. use e.getValue() to get the
   *          --Enums value.
   *          For now the param is a String. //TODO: refactor?
   */
  public void executeToClientEvent(String event);

  /**
   * @return The name of your module, i.e. Camera back garden.
   * You can have multiple names. For each name the framework will create a separate thread.
   */
  public List<String> getNames();

  /**
   * @return the Type of your module, i.e. CameraModule. The framework needs this so it can determine
   * the amount of possible modules. A user could have 2 camera modules with the same type but different names.
   */
  public String getType();


}
