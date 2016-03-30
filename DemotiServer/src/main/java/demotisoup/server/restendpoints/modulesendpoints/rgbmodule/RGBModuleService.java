package demotisoup.server.restendpoints.modulesendpoints.rgbmodule;

import demotisoup.server.restendpoints.modulesendpoints.rgbmodule.domain.Color;
import demotisoup.server.restendpoints.modulesendpoints.rgbmodule.domain.ColorValue;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;

/**
 * User: rgroenve
 * Date: 29-3-16
 * Time: 17:00
 */
@Path("/rgbmodule")
public class RGBModuleService {

  Set<ColorValue> colorValues = new HashSet<>();

  public RGBModuleService(){
    ColorValue red = new ColorValue("red", 100,0,0);
    ColorValue green = new ColorValue("green", 0,100,0);
    ColorValue blue = new ColorValue("blue", 0,0,100);
    colorValues.add(red); colorValues.add(green); colorValues.add(blue);
  }

  @POST
  @Path("/changeColor")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response changeColor(Color color){
    System.out.println("POST reached with color: " + color.getColor());
    for (ColorValue colorValue : colorValues){
      if (colorValue.getColor().equals(color.getColor())){
        System.out.println("Color '" + color.getColor() + "' translates to: " + colorValue.getRedValue() + "," +
                "" + colorValue.getGreenValue() + "," + colorValue.getBlueValue());
      }
    }
    return Response.status(201).build();
  }
}
