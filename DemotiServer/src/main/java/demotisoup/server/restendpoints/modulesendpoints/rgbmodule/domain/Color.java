package demotisoup.server.restendpoints.modulesendpoints.rgbmodule.domain;

/**
 * User: rgroenve
 * Date: 30-3-16
 * Time: 9:01
 */
public class Color {
  private String color;

  public Color(String color) {
    this.color = color;
  }

  public Color(){}

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }
}
