package rest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

@Path("cvrSearch")
public class CvrSearch {

    @Context
    private UriInfo context;

    public CvrSearch() {
    }

    @POST
    @Consumes("application/json")
    @Path("/getcomp/{option}/{searchText}/{ctry}")
    @RolesAllowed("User")
    public String getCompany(@PathParam("option") String option, @PathParam("searchText") String searchText, @PathParam("ctry") String ctry) throws MalformedURLException, IOException, Exception {
        String urlToUse = "http://cvrapi.dk/api?" + option + "=" + searchText + "& country=" + ctry;
        String jsonStr = null;

        try {
            URL url = new URL(urlToUse);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json;charset=UTF-8");
            Scanner scan = new Scanner(con.getInputStream());

            if (scan.hasNext()) {
                jsonStr = scan.nextLine();
            }
            scan.close();
        } catch (Exception e) {
            System.out.println("### FAIL AT SEARCH ### " + e.getMessage());
            throw new NotFoundException();
        }

        return jsonStr;
    }
}
