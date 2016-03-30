package demotisoup.server.restendpoints.modulesendpoints.rgbmodule.domain;

/**
 * User: rgroenve
 * Date: 29-3-16
 * Time: 17:06
 */
public class ColorValue extends Color{
  private int redValue;
  private int greenValue;
  private int blueValue;

  public ColorValue(String color, int redValue, int greenValue, int blueValue) {
    super(color);

    this.redValue = redValue;
    this.greenValue = greenValue;
    this.blueValue = blueValue;
  }

  public int getRedValue() {
    return redValue;
  }

  public int getGreenValue() {
    return greenValue;
  }

  public int getBlueValue() {
    return blueValue;
  }
}
