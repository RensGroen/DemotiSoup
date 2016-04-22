package demotisoup.server.core.communication;

/**
 * Author: Rens Groenveld
 */
public class IfThenEvent {
  private String ifValue;
  private String thenValue;
  private String fromModuleName;
  private String toModuleName;

  public String getIfValue() {
    return ifValue;
  }

  public void setIfValue(String ifValue) {
    this.ifValue = ifValue;
  }

  public String getThenValue() {
    return thenValue;
  }

  public void setThenValue(String thenValue) {
    this.thenValue = thenValue;
  }

  public String getFromModuleName() {
    return fromModuleName;
  }

  public void setFromModuleName(String fromModuleName) {
    this.fromModuleName = fromModuleName;
  }

  public String getToModuleName() {
    return toModuleName;
  }

  public void setToModuleName(String toModuleName) {
    this.toModuleName = toModuleName;
  }
}
