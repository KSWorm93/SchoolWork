/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import entity.Role;
import entity.User;
import exception.AlreadyExistException;
import exception.InvalidDataException;
import exception.UnknownServerException;
import facades.UserFacade;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author kasper
 */
@Path("register")
public class Register {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    UserFacade facade = new UserFacade();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Register
     */
    public Register() {
    }

    /**
     * POST method for updating or creating an instance of Register
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response createUser(String content) throws AlreadyExistException, UnknownServerException, InvalidDataException {
        User newUser = gson.fromJson(content, User.class);
        JsonObject response = new JsonObject();
        
        if(newUser.getUserName() == null || newUser.getPassword() == null) {
            throw new InvalidDataException("Please enter both username and password.");
        }
        
        User userExist = facade.getUserByUserId(newUser.getUserName());
        if(userExist != null) {
            throw new AlreadyExistException("User already exist.");
        }
        
        try {
            newUser = facade.createUser(newUser);
        } catch (IndexOutOfBoundsException ex) {
            throw new UnknownServerException("test " + ex.getMessage());
        }catch (Exception e) {
            throw new UnknownServerException("hej " + e.getMessage());
        } 

        response.addProperty("userName", newUser.getUserName());
        response.addProperty("password", newUser.getPassword());

        return Response.ok(gson.toJson(response), MediaType.APPLICATION_JSON).build();
    }

}
