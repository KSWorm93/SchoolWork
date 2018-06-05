package security;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.User;
import facades.UserFacade;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("register")
public class Register {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    UserFacade facade = new UserFacade();

    @Context
    private UriInfo context;

    public Register() {
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public String createUser(String content) {
        User newUser = gson.fromJson(content, User.class);
        JsonObject response = new JsonObject();

        try {
            newUser = facade.createUser(newUser);
        } catch (Exception e) {
        }
//        String username = response.get("userName").getAsString();
//        String password = response.get("password").getAsString();

        response.addProperty("id", newUser.getId());
        response.addProperty("userName", newUser.getUserName());
        response.addProperty("password", newUser.getPassword());
        response.addProperty("role", "User");

        return gson.toJson(response);

    }

}
