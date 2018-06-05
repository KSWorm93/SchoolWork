package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import facades.UserFacade;
import javax.annotation.security.RolesAllowed;
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

@Path("currency/")
@RolesAllowed("User")
public class DailyRates {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    UserFacade facade = new UserFacade();

    @Context
    private UriInfo context;

    public DailyRates() {
    }

    @GET
    @Path("dailyrates")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTodaysCurrencies() {
        return Response.ok(gson.toJson(facade.getTodaysCurrencies()), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("calculate/{amount}/{fromcurrency}/{tocurrency}")
    public Response getCalculatedValuta(@PathParam("amount") String amount, @PathParam("fromcurrency") String fromcurrency, @PathParam("tocurrency") String tocurrency) {
        return Response.ok(gson.toJson(facade.calculateCurrency(amount, fromcurrency, tocurrency)), MediaType.APPLICATION_JSON).build();
    }
}
