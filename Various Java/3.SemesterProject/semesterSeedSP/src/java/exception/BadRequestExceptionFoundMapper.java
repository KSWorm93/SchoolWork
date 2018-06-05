package exception;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class BadRequestExceptionFoundMapper implements ExceptionMapper<NotFoundException> {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    @Override
    public Response toResponse(NotFoundException ex) {
        JsonObject json = new JsonObject();
        json.addProperty("httpError", "400");
        json.addProperty("errorCode", "400");
        json.addProperty("message", "Bad request.");
        
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(gson.toJson(json))
                .type(MediaType.APPLICATION_JSON)
                .build();
         
    }
    
}
