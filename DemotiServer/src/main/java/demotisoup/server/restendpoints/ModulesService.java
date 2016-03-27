package demotisoup.server.restendpoints;

import demotisoup.server.beans.ModuleJsonDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * User: rensgroenveld
 * Date: 21-3-16
 * Time: 19:16
 */

@Path("/modules")
public class ModulesService {

  @GET
  @Path("availableModules")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAvailableModules()
  {
    LinkedHashMap<String,Object> map = new LinkedHashMap<>();
    map.put("modules", createAvailableModuleList());
    return Response.ok(map)
            .header("Access-Control-Allow-Origin","*")
            .build();
  }

  // Utiliy method to create module list.
  public List<ModuleJsonDTO> createAvailableModuleList()
  {
    ModuleJsonDTO ledModule=new ModuleJsonDTO();
    ledModule.setType("RGB LED ModuleJsonDTO");
    ledModule.setDescription("This module allows you to control a red, " +
            "green and blue (RGB) led light that is installed within your wifi.");
    ledModule.setPhysicalLocationName("Hallway led");

    ModuleJsonDTO cameraModule=new ModuleJsonDTO();
    cameraModule.setType("Camera module");
    cameraModule.setDescription("Gain control over your camera's installed in your wifi.");
    cameraModule.setPhysicalLocationName("Back yard camera");

    List<ModuleJsonDTO> modules = new ArrayList<ModuleJsonDTO>();
    modules.add(ledModule);
    modules.add(cameraModule);
    return modules;
  }
}
