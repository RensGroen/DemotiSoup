package demotisoup.server.restendpoints.modulesendpoints.rgbmodule.domain;

/**
 * User: rgroenve
 * Date: 30-3-16
 * Time: 9:01
 */
public class RgbDTO {
  private String color;
  private String physicalLocationName;

  public RgbDTO(String color,  String physicalLocationName) {
    this.color = color;
    this.physicalLocationName = physicalLocationName;
  }

  public RgbDTO(){}

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getPhysicalLocationName() {
    return physicalLocationName;
  }

  public void setPhysicalLocationName(String physicalLocationName) {
    this.physicalLocationName = physicalLocationName;
  }
}
