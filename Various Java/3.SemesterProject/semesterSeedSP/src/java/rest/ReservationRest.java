/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entity.ReservationRequest.Passengers;
import entity.ReservationRequest.Reservation;
import static entity.deploy.ApiLinks_.url;
import exception.InvalidDataException;
import exception.NoServerConnectionFoundException;
import exception.UnknownServerException;
import facades.ReservationFacade;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author kasper
 */
@Path("flightreservation")
public class ReservationRest {

    @Context
    private UriInfo context;

    ReservationFacade facade = new ReservationFacade();
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();

    public ReservationRest() {
    }

    private static String input = null;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public String makeReservation(String content) throws MalformedURLException, IOException, InvalidDataException, UnknownServerException {

        Reservation reservation = gson.fromJson(content, Reservation.class);
        if (reservation == null) {
            throw new InvalidDataException("Invalid data");
        }

        JsonObject response = new JsonObject();
        JsonArray passengerArr = new JsonArray();
        String urlToUse = reservation.getUrl() + "/api/flightreservation";

        response.addProperty("flightID", reservation.getFlightID());
        response.addProperty("numberOfSeats", reservation.getNumberOfSeats());
        response.addProperty("ReserveeName", reservation.getReserveeName());
        response.addProperty("ReservePhone", reservation.getReservePhone());
        response.addProperty("ReserveeEmail", reservation.getReserveeEmail());
        for (Passengers passenger : reservation.getPassengers()) {
            JsonObject passengerObj = new JsonObject();
            passengerObj.addProperty("firstName", passenger.getFirstName());
            passengerObj.addProperty("lastName", passenger.getLastName());
            passengerArr.add(passengerObj);
        }
        response.add("Passengers", passengerArr);

        HttpURLConnection con = (HttpURLConnection) new URL(urlToUse).openConnection();
        con.setRequestProperty("Content-Type", "application/json;");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Method", "POST");
        con.setDoOutput(true);

        PrintWriter pw = new PrintWriter(con.getOutputStream());
        try (OutputStream os = con.getOutputStream()) {
            os.write(response.toString().getBytes("UTF-8"));
        }

        int HttpResult = con.getResponseCode();
        InputStreamReader is = HttpResult < 400 ? new InputStreamReader(con.getInputStream(), "utf-8")
                : new InputStreamReader(con.getErrorStream(), "utf-8");
        Scanner responseReader = new Scanner(is);
        String response2 = "";
        while (responseReader.hasNext()) {
            response2 += responseReader.nextLine() + System.getProperty("line.separator");
        }

        Reservation newReservation = gson.fromJson(response2, Reservation.class);
        newReservation.setReservePhone(response.get("ReservePhone").getAsString());
        newReservation.setReserveeEmail(response.get("ReserveeEmail").getAsString());
        facade.addReservation(newReservation);

        return response2;
    }

    @GET
    @Path("reservations/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    @RolesAllowed("User")
    public Response getUserReservation(@PathParam("id") String id) {
        return Response.ok(gson.toJson(facade.getUserReservation(id)), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("reservations/")
    @Consumes("application/json")
    @Produces("application/json")
    @RolesAllowed("Admin")
    public Response getAllReservation() {
        return Response.ok(gson.toJson(facade.getAllReservation()), MediaType.APPLICATION_JSON).build();
    }

};
