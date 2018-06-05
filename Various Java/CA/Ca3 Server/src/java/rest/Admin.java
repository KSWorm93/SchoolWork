package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import facades.UserFacade;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("demoadmin")
@RolesAllowed("Admin")
public class Admin {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    UserFacade facade = new UserFacade();

    @GET
    @Path("users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        return Response.ok(gson.toJson(facade.getUsers()), MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Consumes("application/json")
    @Path("user/{id}")
    public String deleteUser(@PathParam("id") String id) throws Exception {
        JsonObject response = new JsonObject();

        try {
            entity.User removedUser = facade.deleteUser(new Long(id));
            response.addProperty("userName", removedUser.getUserName());
        } catch (Exception e) {
            throw new NotFoundException();
        }

        return gson.toJson(response);
    }

}
