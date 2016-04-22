package demotisoup.server.core.communication.socketmechanism;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Rens Groenveld
 */
public class DriverEvents {

  private Set<String> events = new HashSet<>();
  private String type;

  public void addDriverEvent(String event){
    events.add(event);
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Set<String> getEvents(){
    return events;
  }
}
