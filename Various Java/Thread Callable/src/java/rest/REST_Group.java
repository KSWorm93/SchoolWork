/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import callables.CallableTask;
import callables.main;
import static callables.main.getGroupJson;
import static callables.main.groupList;
import static callables.main.main;
import static callables.main.urls;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Group;

/**
 * REST Web Service
 *
 * @author kasper
 */
@Path("groups")
public class REST_Group {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    main ctrl = new main();

    ExecutorService es = Executors.newFixedThreadPool(4);

    List<Future<Group>> futures
            = new ArrayList<Future<Group>>(urls.size());

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of REST_Group
     */
    public REST_Group() {
    }

    /**
     * Retrieves representation of an instance of rest.REST_Group
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public Response getGroups() throws InterruptedException, ExecutionException {
        //TODO return proper representation object

        try {
            //Loops through the urls 
            for (String url : urls) {
                //Add a future for every url ther eis
                futures.add(es.submit(new CallableTask(url)));
            }
            //Solution to a bug i cant detect.
            //Clears the groupList if there is something in it. 
            //Otherwise the list will keep on growing
            if (groupList != null) {
                groupList.clear();
            }
            //Loop through the futures and add the values to the groupList
            for (Future<Group> future : futures) {
                List groupList = main.groupList;
                groupList.add(future.get());
            }

            //            System.out.println("\nBeginning end sout\n");
            //            System.out.println("GroupList: " + groupList.get(1).getAuthors());
            //            System.out.println("GroupList: " + groupList.get(5).getAuthors());
            //            for (int i = 0; i < groupList.size(); i++) {
            //                System.out.println("\nFull results: " + groupList.get(i).getAuthors());
            //                System.out.println("Full results: " + groupList.get(i).getGroup());
            //                System.out.println("Full results: " + groupList.get(i).getMyClass());
            //
            //            }
            //System.out.println("JsonString: " + getGroupJson());
        } finally {
            es.shutdown();
        }
        //returns the response from getGroupJson which is a json string
        return Response.ok(gson.toJson(ctrl.getGroupJson()), MediaType.APPLICATION_JSON).build();
    }

}
