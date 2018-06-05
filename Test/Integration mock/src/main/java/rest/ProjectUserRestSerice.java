/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entity.Facade;
import entity.Project;
import entity.ProjectUser;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
 * @author sofus
 */
@Path("users")
public class ProjectUserRestSerice {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RestService
     */
    Gson gson;
    Facade f = new Facade();

    public ProjectUserRestSerice() {
        gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(String user) {
        ProjectUser u = gson.fromJson(user, ProjectUser.class);
        f.createUser(u);
        return Response.status(Response.Status.CREATED).type(MediaType.APPLICATION_JSON).entity(gson.toJson(u)).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        JsonArray out= new JsonArray();
        JsonObject juser = new JsonObject();
        List<ProjectUser>users= f.getUsers();
        System.out.println(users.size());
        for (ProjectUser user : users) {
            juser=makeUser(user);
           
            out.add(juser);
        }
        return Response.status(Response.Status.OK).entity(out.toString()).build();
    }
    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") String id){
    f.deleteUser(new Long(id));
    return Response.status(Response.Status.OK).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getUser(@PathParam("id") String id){
        return Response.status(Response.Status.OK).entity(makeUser(f.findUser(new Long(id))).toString()).build(); 
    }
    
    private JsonObject makeUser(ProjectUser user){
          JsonObject juser=new JsonObject();
            juser.addProperty("id", user.getId());
            juser.addProperty("userName", user.getUserName());
            juser.addProperty("email", user.getEmail());
            juser.addProperty("created",user.getCreated().toString());
            JsonArray jprojects = new JsonArray();
            JsonObject project;
            for (Project projects : user.getContributors()) {
            project=new JsonObject();
            project.addProperty("name", projects.getName());
            project.addProperty("id", projects.getId());
                jprojects.add(project);
            }
             juser.add("projects", jprojects);
            return juser;
    }
    

}
