package demotisoup.server.core.communication;

import demotisoup.server.core.communication.socketmechanism.DriverEvents;
import demotisoup.server.core.communication.socketmechanism.SocketController;
import demotisoup.server.core.communication.socketmechanism.clientsocket.ClientSocketInterface;

import java.io.IOException;
import java.util.List;

/**
 * Author: Rens Groenveld
 */
public class CommunicationController {

  private SocketController socketController;
  private static CommunicationController instance;
  private DriverController driverController;

  private CommunicationController(){}

  public static CommunicationController getInstance(){
    if (instance == null){
      instance = new CommunicationController();
      instance.initialize();
    }
    return instance;
  }

  private void initialize() {
    driverController = new DriverController();
    initializeSocketMechanism();
  }

  private void initializeSocketMechanism() {
    try {
      int port = 1233;
      socketController = new SocketController(port);
      socketController.start();
    } catch (IOException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
  }

  public List<ClientSocketInterface> getRegisteredModules(){
    return socketController.getClients();
  }

  public ClientSocketInterface getRegisteredModule(String name){
    for (ClientSocketInterface csi : socketController.getClients()){
      if (csi.getClientName().equals(name)){
        return csi;
      }
    }
    return null;
  }

  /**
   * Write your command to the device/client you want
   * @param name the name of your device/client
   * @param data the data you want to send
   */
  public void write(String name, String data){
    socketController.write(name, data);
  }

  /**
   * This method is called by the communication handlers i.e. sockets or webservices when a device sends data.
   * I currently don't like that it's publicly available to the whole application. Refactoring still to do.
   * @param name the name of the application
   * @param data the data that the device/client sent
   */
  public void clientReadEvent(String name, String data) {
    System.out.println(name + " received: " + data);
    driverController.onPublishEvent(name, data);
  }

  /**
   * Register your Driver here through the IDriver interface.
   */
  public void registerDriver(IDriver IDriver){
     driverController.registerDriver(IDriver);
  }

  /**
   * Register an if-then event. i.e. if camera detects movement then change colors of rgb led to red.
   * It is crucial that the values of the strings are the name values of the registered Enums,
   * as can be found in the IDriver.
   * @param ifThenEvent
   */
  public void registerIfThenEvent(IfThenEvent ifThenEvent){
    driverController.registerIfThenEvent(ifThenEvent);
  }

  /**
   * Get all possible to clients events
   * i.e. move camera to right
   * @return
   */
  public List<DriverEvents> getToClientEvents(){
    return driverController.getToClientEvents();
  }

  /**
   * Get all posible from client events
   * i.e. camera detected movement
   * @return
   */
  public List<DriverEvents> getFromClientEvents(){
    return driverController.getFromClientEvents();
  }

  /**
   * Crucial for the framework. If your driver was called by this framework through a fromClient event,
   * you have to let the framework which kind of fromClient event this was. If you don't do this, the framework
   * will not call any actions on other modules as by user defined.
   * @param fromClientEvent The highlighted Enum which is one of the earlier registered enums as registered in IDriver
   */
  public void stateFromClientEvent(String name, Enum<?> fromClientEvent){
     driverController.handleFromClientEvent(name, fromClientEvent);
  }
}
