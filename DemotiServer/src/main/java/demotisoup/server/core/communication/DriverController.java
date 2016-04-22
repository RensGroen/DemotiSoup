package demotisoup.server.core.communication;

import demotisoup.server.core.communication.socketmechanism.DriverEvents;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Rens Groenveld
 */
class DriverController {

  private List<IDriver> iDrivers = new ArrayList<>();
  private List<DriverEvents> fromClientEvents = new ArrayList<>();
  private List<DriverEvents> toClientEvents = new ArrayList<>();
  private List<IfThenEvent> ifThenEvents = new ArrayList<>();

  /**
   * Declare package private constructor
   */
  DriverController() {}

  //<E extends Enum<?>>
  void registerDriver(IDriver IDriver) {
    registerFromClientEvents(IDriver);
    registerToClientEvents(IDriver);
    iDrivers.add(IDriver);
  }

  private void registerFromClientEvents(IDriver iDriver) {
    if (iDriver.getPublishEvents() != null){
      Class<? extends Enum<?>> c = iDriver.getPublishEvents();
      addToDriverEvents(iDriver, c, fromClientEvents);
    }
  }

  private void registerToClientEvents(IDriver iDriver) {
    if (iDriver.getListenEvents() != null){
      Class<? extends Enum<?>> c = iDriver.getListenEvents();
      addToDriverEvents(iDriver, c, toClientEvents);
    }
  }

  private void addToDriverEvents(IDriver iDriver, Class<? extends Enum<?>> c, List<DriverEvents> cachedEvents) {
    boolean driverEventFound = false;
    for (DriverEvents driverEvents : cachedEvents){
      if (driverEvents.getType().equals(iDriver.getType())){
        driverEventFound = true;
      }
    }
    if (!driverEventFound){
      DriverEvents driverEvents = new DriverEvents();
      driverEvents.setType(iDriver.getType());
      for (Enum selectedEnum : c.getEnumConstants()){
        driverEvents.addDriverEvent(selectedEnum.name());
      }
      cachedEvents.add(driverEvents);
    }
  }

  void onPublishEvent(String name, String data) {
    IDriver driver = getDriver(name);
    driver.onEventPublished(data);
  }

  private IDriver getDriver(String name) {
    for (IDriver driver : iDrivers){
      if (driver.getName().equals(name)){
        return driver;
      }
    }
    return null;
  }

  List<DriverEvents> getToClientEvents() {
    return toClientEvents;
  }

  List<DriverEvents> getFromClientEvents() {
    return fromClientEvents;
  }

  void handleFromClientEvent(String name, Enum<?> fromClientEvent){
    for (IfThenEvent ifThenEvent : ifThenEvents){
      if (ifThenEvent.getIfValue().equals(fromClientEvent.name()) && ifThenEvent.getFromModuleName().equals(name)){
        IDriver iDriver = getDriver(ifThenEvent.getToModuleName());
        iDriver.executeToClientEvent(ifThenEvent.getThenValue());
      }
    }
  }

  void registerIfThenEvent(IfThenEvent ifThenEvent) {
    ifThenEvents.add(ifThenEvent);
  }
}