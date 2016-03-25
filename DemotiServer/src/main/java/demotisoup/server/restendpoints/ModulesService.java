package demotisoup.server.restendpoints;

import demotisoup.server.beans.Module;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
  public Response getModules()
  {
    LinkedHashMap<String,Object> map = new LinkedHashMap<>();
    map.put("modules", createAvailableModuleList());
    return Response.ok(map)
            .header("Access-Control-Allow-Origin","*")
            .build();
  }

  @GET
  @Path("configuredModules")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getConfiguredModules(){
    LinkedHashMap<String,Object> map = new LinkedHashMap<>();
    //Module ledModule=new Module(1,"RGB LED Module","This module allows you to control a red, " +
    //        "green and blue (RGB) led light that is installed within your wifi.");
    //ArrayList modules = new ArrayList();
    //modules.add(ledModule);
    //map.put("modules", modules);
    return Response.ok(map)
            .header("Access-Control-Allow-Origin","*")
            .build();
  }

//  @GET
//  @Path("{id: \\d+}")
//  @Produces(MediaType.APPLICATION_JSON)
//  public Module getModuleById(@PathParam("id") int id)
//  {
//    List<Module> modules;
//    modules= createAvailableModuleList();
//
//    for (Module module: modules) {
//      if(module.getId()==id)
//        return module;
//    }
//
//    return null;
//  }

  // Utiliy method to create module list.
  public List<Module> createAvailableModuleList()
  {
    Module ledModule=new Module(1,"RGB LED Module","This module allows you to control a red, " +
            "green and blue (RGB) led light that is installed within your wifi.");
    Module motionSensorModule=new Module(2, "Motion Sensor Module","Control your motion sensors that are installed " +
            "within your wifi.");
    Module cameraModule=new Module(3, "Camera module","Gain control over your camera's installed in your wifi.");
    Module displayModule=new Module(4, "Display module","This display module is really the shit, " +
            "but it is still only fictional");

    List<Module> modules = new ArrayList<Module>();
    modules.add(ledModule);
    modules.add(motionSensorModule);
    modules.add(cameraModule);
    modules.add(displayModule);
    return modules;
  }
}
