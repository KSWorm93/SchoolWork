package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import control.GetFlightsFromApiUrl;
import entity.ApiLinks;
import entity.InfoResponse.Airline;
import entity.InfoResponse.FlightSearch;
import entity.InfoResponse.Flights;
import exception.InvalidDataException;
import exception.NoFlightsFoundException;
import exception.NoServerConnectionFoundException;
import exception.UnknownServerException;
import facades.ApiFacade;
import facades.ReservationFacade;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

@Path("flightinfo")
public class FlightRest {

    ApiFacade apiCtrl = new ApiFacade();
    ReservationFacade reservationCtrl = new ReservationFacade();
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();
    JsonParser parser = new JsonParser();

    @Context
    private UriInfo context;

    public FlightRest() {
    }

    @GET
    @Path("/external/{from}/{date}/{tickets}")
    @Produces("application/json")
    public Response getFlightsFromOrigin(@PathParam("from") String from, @PathParam("date") String date, @PathParam("tickets") String tickets) throws IOException, InterruptedException, NoServerConnectionFoundException, InvalidDataException, NoFlightsFoundException, ParseException, UnknownServerException {
        List<ApiLinks> apiList = apiCtrl.getApiLinks();
        List<Airline> airlines = new ArrayList();
        ExecutorService service = Executors.newFixedThreadPool(4);

        JsonArray realResponse = new JsonArray();
        JsonObject response = new JsonObject();
        String jsonStr = null;

        for (ApiLinks apiUrl : apiList) {
            String urlToUse = apiUrl.getUrl() + "api/flightinfo/" + from + "/" + date + "/" + tickets;
// Single url call           String urlToUse = "http://angularairline-plaul.rhcloud.com/api/flightinfo/" + from + "/" + date + "/" + tickets;
            try {
                GetFlightsFromApiUrl flightThread = new GetFlightsFromApiUrl(urlToUse, airlines, apiUrl.getUrl());
                service.submit(flightThread);
                System.out.println("After thread start: " + urlToUse);
            } catch (Exception e) {
                throw new NoServerConnectionFoundException("Could not connect to url API.");
            }
        }

        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);

        if (airlines.size() <= 0) {
            throw new NoFlightsFoundException("Could not find any flights");
        } else {
            DateTimeFormatter dateParser = ISODateTimeFormat.dateTimeParser().withZoneUTC();
            DateTime date2 = dateParser.parseDateTime(date);

            FlightSearch flightSearch = new FlightSearch();
            flightSearch.setOrigin(from);
            flightSearch.setDate(date2.toDate());
            flightSearch.setNumberOfSeats(Integer.parseInt(tickets));

            try {
                reservationCtrl.addSearch(flightSearch);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return Response.ok(gson.toJson(airlines), MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("/external/{from}/{to}/{date}/{tickets}")
    @Produces("application/json")
    public Response getFlightsFromOriginAndDestination(@PathParam("from") String from, @PathParam("to") String to, @PathParam("date") String date, @PathParam("tickets") String tickets) throws IOException, InterruptedException, NoServerConnectionFoundException, NoFlightsFoundException {
        List<ApiLinks> apiList = apiCtrl.getApiLinks();
        ExecutorService service = Executors.newFixedThreadPool(4);
        List<Airline> airlines = new ArrayList();

        JsonArray realResponse = new JsonArray();
        JsonObject response = new JsonObject();
        String jsonStr = null;

        for (ApiLinks apiUrl : apiList) {
            String urlToUse = apiUrl.getUrl() + "api/flightinfo/" + from + "/" + to + "/" + date + "/" + tickets;

            try {
                GetFlightsFromApiUrl flightThread = new GetFlightsFromApiUrl(urlToUse, airlines, apiUrl.getUrl());
                service.submit(flightThread);
                System.out.println("After thread start: " + urlToUse);
            } catch (Exception e) {
                throw new NoServerConnectionFoundException("Could not connect to url API.");
            }
        }

        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);

        if (airlines.size() <= 0) {
            throw new NoFlightsFoundException("Could not find any flights");
        } else {
            for (Airline airline : airlines) {
                JsonArray airlineFlights = new JsonArray();

                response.addProperty("airline", airline.getAirline());

                for (Flights route : airline.getFlights()) {
                    JsonObject flight = new JsonObject();

                    flight.addProperty("date", route.getDate());
                    flight.addProperty("numberOfSeats", route.getNumberOfSeats());
                    flight.addProperty("totalPrice", route.getTotalPrice());
                    flight.addProperty("flightID", route.getFlightId());
                    flight.addProperty("travelTime", route.getTravelTime());
                    flight.addProperty("destination", route.getDestination());
                    flight.addProperty("origin", route.getOrigin());
                    //Tilføjet URL som property
                    flight.addProperty("url", route.getUrl());
                    airlineFlights.add(flight);
                }
                response.add("flights", airlineFlights);
            }
            realResponse.add(response);

            DateTimeFormatter dateParser = ISODateTimeFormat.dateTimeParser().withZoneUTC();
            DateTime date2 = dateParser.parseDateTime(date);

            FlightSearch flightSearch = new FlightSearch();
            flightSearch.setOrigin(from);
            flightSearch.setDestination(to);
            flightSearch.setDate(date2.toDate());
            flightSearch.setNumberOfSeats(Integer.parseInt(tickets));

            try {
                reservationCtrl.addSearch(flightSearch);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return Response.ok(gson.toJson(realResponse), MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("/hotspots")
    @Produces("application/json")
    public Response getHotspots() {

        return Response.ok(gson.toJson(reservationCtrl.getTopReservation(2)), MediaType.APPLICATION_JSON).build();
    }
}
