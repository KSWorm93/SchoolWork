package exceptions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ProjectNotFoundExceptionMapper implements ExceptionMapper<ProjectNotFoundException> {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    @Override
    public Response toResponse(ProjectNotFoundException e) {
        JsonObject obj = new JsonObject();
        obj.addProperty("code", "404");
        obj.addProperty("msg", "Project with the given ID is not in the database");

        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(gson.toJson(obj))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    
}
