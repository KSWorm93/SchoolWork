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
import exceptions.ProjectNotFoundException;
import java.util.List;
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
 * @author sofus
 */
@Path("projects")
public class ProjectRestService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RestService
     */
    Gson gson;
    Facade f = new Facade();
    public ProjectRestService() {
    gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProject(String project) {
        Project p = gson.fromJson(project, Project.class);
        f.createProject(p);
        return Response.status(Response.Status.CREATED).type(MediaType.APPLICATION_JSON).entity(gson.toJson(p)).build();
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProjects() {
        JsonArray out= new JsonArray();
        JsonObject jprojects = new JsonObject();
        List<Project>projects= f.getProjects();
        System.out.println(projects.size());
        for (Project p : projects) {
            jprojects=makeProject(p);
           
            out.add(jprojects);
        }
        return Response.status(Response.Status.OK).entity(out.toString()).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getProject(@PathParam("id") String id) throws ProjectNotFoundException {
        
        return Response.status(Response.Status.OK).entity(makeProject(f.findProject(new Long(id))).toString()).build(); 
    }
    
    private JsonObject makeProject(Project project){
          JsonObject jproject=new JsonObject();
            jproject.addProperty("id", project.getId());
            jproject.addProperty("name", project.getName());
            jproject.addProperty("description", project.getDescription());
            
            return jproject;
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uID}, {pID}")
    public void assignUserToProject(@PathParam("uID") Long uID, @PathParam("pID") Long pID) {
        Facade facade = new Facade();
        facade.assignUserToProject(pID, uID);
    }
   
}
