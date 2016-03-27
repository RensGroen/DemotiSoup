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
  @Path("configuredModules")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getConfiguredModules()
  {
    LinkedHashMap<String,Object> map = new LinkedHashMap<>();
    map.put("modules", createConfiguredModuleList());
    return Response.ok(map)
            .header("Access-Control-Allow-Origin","*")
            .build();
  }

  // Utiliy method to create module list.
  public List<ModuleJsonDTO> createConfiguredModuleList()
  {
    ModuleJsonDTO ledModule=new ModuleJsonDTO();
    ledModule.setType("RGBLEDModule");
    ledModule.setPhysicalLocationName("Hallway led");

    //ModuleJsonDTO cameraModule=new ModuleJsonDTO();
    //cameraModule.setType("Camera module");
    //cameraModule.setPhysicalLocationName("Back yard camera");

    List<ModuleJsonDTO> modules = new ArrayList<ModuleJsonDTO>();
    modules.add(ledModule);
    //modules.add(cameraModule);
    return modules;
  }
}
