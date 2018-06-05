package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import facades.UserFacade;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("userSettings")
public class UserSettings {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    UserFacade facade = new UserFacade();
    
    @Context
    private UriInfo context;

    public UserSettings() {
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("editUserInfo")
    public Response editUser(String content) {
        System.out.println(content);
        JsonObject response = new JsonObject();
        
        try {
            entity.User editedUser = gson.fromJson(content, entity.User.class);
            entity.User newUser = facade.editUserInfo(editedUser);
            response.addProperty("userName", newUser.getUserName());
        } catch (Exception e) {
        }
        
        return Response.ok(gson.toJson(response), MediaType.APPLICATION_JSON).build();
        
    }
}
