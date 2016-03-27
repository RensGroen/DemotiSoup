package demotisoup.server.beans;

/**
 * User: rgroenveld
 * Date: 21-3-16
 * Time: 19:22
 */
public class ModuleJsonDTO {

  private String type;
  private String physicalLocationName;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getPhysicalLocationName() {
    return physicalLocationName;
  }

  public void setPhysicalLocationName(String physicalLocationName) {
    this.physicalLocationName = physicalLocationName;
  }
}
