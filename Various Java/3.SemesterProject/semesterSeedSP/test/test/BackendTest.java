/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.defaultParser;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.parsing.Parser;
import static com.jayway.restassured.path.json.JsonPath.from;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import static org.hamcrest.Matchers.equalTo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rest.ApplicationConfig;

/**
 *
 * @author sofus
 */
public class BackendTest {

    static Server server;

    public BackendTest() {
        baseURI = "http://localhost:8084";
        defaultParser = Parser.JSON;
        basePath = "/api";
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        server = new Server(8084);
        ServletHolder servletHolder = new ServletHolder(org.glassfish.jersey.servlet.ServletContainer.class);
        servletHolder.setInitParameter("javax.ws.rs.Application", ApplicationConfig.class.getName());
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");
        contextHandler.addServlet(servletHolder, "/api/*");
        server.setHandler(contextHandler);
        server.start();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        server.stop();
        //waiting for all the server threads to terminate so we can exit gracefully
        server.join();
    }

    //Tests with user login
    @Test
    public void testLoginWrongUsername() {
        given().
                contentType("application/json").
                body("{'username':'john','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(401).
                body("error.message", equalTo("Ilegal username or password"));
    }
    @Test
    public void testLoginWrongUsernameAndPassword() {
        //wrong username and password
        given().
                contentType("application/json").
                body("{'username':'john','password':'doe'}").
                when().
                post("/login").
                then().
                statusCode(401).
                body("error.message", equalTo("Ilegal username or password"));
    }
    @Test
    public void testLogin() {
        //Successful login
        given().
                contentType("application/json").
                body("{'username':'user','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200);

    }
    @Test
    public void testDemoUserNoLogin() {
        given().
                contentType("application/json").
                when().
                get("/demouser").
                then().
                statusCode(401);
    }
    @Test
    public void testDemoUserLogin() {
        //First, make a login to get the token for the Authorization, saving the response body in String json
        String json = given().
                contentType("application/json").
                body("{'username':'user','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200).extract().asString();

        //Then test /demouser URL with the correct token extracted from the JSON string.
        given().
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                get("/demouser").
                then().
                statusCode(200);
        //And test that the user cannot access /demoadmin rest service
        given().
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                get("/demoadmin").
                then().
                statusCode(403).
                body("error.message", equalTo("You are not authorized to perform the requested operation"));
    }
    @Test
    public void testUserNotFound() {
        //Check to see if you can log in with a user that doesnt exist.
        String json = given().
                contentType("application/json").
                body("{'username':'failed','password':'failed'}").
                when().
                post("/login").
                then().
                statusCode(401).extract().asString();
    }
    
    //Tests with register user
    @Test
    public void testInvalidRegisterTest() {
        //Test if the right exception is sent if we forget to input either username or password
        given().
                contentType("application/json").
                body("{'username':'user', 'password' : ''}").
                when().
                post("/register").
                then().
                statusCode(400).
                body("message", equalTo("Please enter both username and password."));
    }
    
    
    //Since we cant delete user, its not possible to remove the test user
    //after we have created him. The database will be filled quickly if run
    //Continuesly while testing other tests
//    @Test
//    public void testCreateUser() {
//        // Tester om der bliver oprettet en bruger og om den eksisterer i databasen.
//        given().
//                contentType("application/json").
//                body("{'userName' : 'MyNewUser1234', 'password' : 'myPass'}").
//                when().
//                post("/register").
//                then().
//                body("userName", equalTo("MyNewUser1234")).
//                body("password", equalTo("myPass")).
//                statusCode(200);
//    }
    @Test
    public void testCreateUserAlreadyExist() {
        // Tester om der kommer fejlbesked ved oprettelse af bruger, hvor brugernavn allerede eksisterer.
        given().
                contentType("application/json").
                body("{'userName' : 'MyNewUser', 'password' : 'myPass'}").
                when().
                post("/register").
                then().
                statusCode(402).
                body("message", equalTo("User already exist."));
    }
//    @Test
//    public void testEditUserPassword() {
//        //Create new test user
//        given().
//                contentType("application/json").
//                body("{'userName' : 'Tester1234567', 'password' : 'myPass'}").
//                when().
//                post("/register").
//                then().
//                statusCode(200).
//                body("userName", equalTo("Tester1234567")).
//                body("password", equalTo("myPass"));
//        //Login, so we can edit the user
//        given().
//                contentType("application/json").
//                body("{'username':'Tester1234567','password':'myPass'}").
//                when().
//                post("/login").
//                then().
//                statusCode(200).extract().asString();
//        //Edit newly created users password
//        given().
//                contentType("application/json").
//                body("{'userName' : 'Tester1234567', 'password' : 'myNewPass'}").
//                when().
//                put("/userSettings/editUserInfo").
//                then().
//                statusCode(200).
//                body("userName", equalTo("Tester1234567"));
//    }
    
    //Tests about URL's
    @Test
    public void testBadUrl() {
        //If you write a request to a page that doesnt exist. it should give you a 404 error
        given().
                contentType("application/json").
                when().
                get("/thiswillnotwork").
                then().
                statusCode(404);
    }
    @Test
    public void testGetOtherServerAPI() {
        //Changing the server URI to something else.
        baseURI = "http://angularairline-plaul.rhcloud.com";
        given().
                contentType("application/json").
                when().
                get("/flightinfo/CPH/2016-01-04T23:00:00.000Z/3").
                then().
                statusCode(200);
    }

    //Tests with Flight search 
    @Test
    public void testGetFlightFromOrigin() {
        //Searching for flights with origin/date/passengers
        given().
                contentType("application/json").
                when().
                get("/flightinfo/external/CPH/STN/2016-01-04T23:00:00.000Z/1").
                then().
                statusCode(200);
    }
    @Test
    public void testGetFlightFromOriginToDestination() {
        //Searching for flights with origin/destination/date/passengers
        given().
                contentType("application/json").
                when().
                get("/flightinfo/external/CPH/STN/2016-01-04T23:00:00.000Z/1").
                then().
                statusCode(200);
    }
    //Tests with failing search params
    @Test
    public void testGetFlightFromOriginFail() {
        //Sets origin to be from abc(random). No flight expected
        given().
                contentType("application/json").
                when().
                get("/flightinfo/external/abc/2016-01-04T23:00:00.000Z/1").
                then().
                statusCode(404).
                body("message", equalTo("Could not find any flights"));

    }
    @Test
    public void testGetFlightFromDateFail() {
        //Sets date to be in 2116. No flights expected at that date
        given().
                contentType("application/json").
                when().
                get("/flightinfo/external/CPH/1515-01-04T23:00:00.000Z/1").
                then().
                statusCode(404).
                body("message", equalTo("Could not find any flights"));

    }
    @Test
    public void testGetFlightNoPassengersFail() {
        //Sets 0(null) passengers. No flights expected at that date
        given().
                contentType("application/json").
                when().
                get("/flightinfo/external/CPH/2116-01-04T23:00:00.000Z/0").
                then().
                statusCode(404).
                body("message", equalTo("Could not find any flights"));
    }
    @Test
    public void testGetFlightNoParamsFail() {
        //Sets null parameters in the call. No flights expected at that date
        given().
                contentType("application/json").
                when().
                get("/flightinfo/external/").
                then().
                statusCode(404).
                body("message", equalTo("Can't find any airlines"));
    }

    
}
