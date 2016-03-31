package demotisoup.server.restendpoints.modulesendpoints.rgbmodule;

import demotisoup.server.restendpoints.modulesendpoints.rgbmodule.domain.RgbColorValues;
import demotisoup.server.restendpoints.modulesendpoints.rgbmodule.domain.RgbDTO;

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

  Set<RgbColorValues> colorValues = new HashSet<>();

  public RGBModuleService(){
    RgbColorValues red = new RgbColorValues("red", 100,0,0);
    RgbColorValues green = new RgbColorValues("green", 0,100,0);
    RgbColorValues blue = new RgbColorValues("blue", 0,0,100);
    colorValues.add(red); colorValues.add(green); colorValues.add(blue);
  }

  @POST
  @Path("/changeColor")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response changeColor(RgbDTO rgbDTO){
    System.out.println("POST reached with rgbDTO: " + rgbDTO.getColor());
    for (RgbColorValues colorValue : colorValues){
      if (colorValue.getColor().equals(rgbDTO.getColor())){
        System.out.println("RgbDTO '" + rgbDTO.getColor() + "' translates to: " + colorValue.getRedValue() + "," +
                "" + colorValue.getGreenValue() + "," + colorValue.getBlueValue() + "; Will be send to: " + rgbDTO
                .getPhysicalLocationName());
      }
    }
    return Response.status(201).build();
  }
}
