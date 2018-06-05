package exceptions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NotFoundExceptionsMapper implements ExceptionMapper<NotFoundException> {
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Response toResponse(NotFoundException e) {
        JsonObject obj = new JsonObject();
        obj.addProperty("code", "404");
        obj.addProperty("msg", "The thing you were searching for, could not be found");

        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(gson.toJson(obj))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
