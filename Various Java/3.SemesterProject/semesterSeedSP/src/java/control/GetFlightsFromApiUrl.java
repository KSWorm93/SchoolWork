package control;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import entity.InfoResponse.Airline;
import entity.InfoResponse.Flights;
import exception.NoFlightsFoundException;
import exception.NoServerConnectionFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.NotFoundException;
import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class GetFlightsFromApiUrl extends Thread implements Runnable {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonParser parser = new JsonParser();
    private String urlToUse;
    private volatile List<Airline> flyList;
    private String apiUrl;

    public GetFlightsFromApiUrl(String urlToUse, List<Airline> flyList, String apiUrl) {
        this.urlToUse = urlToUse;
        this.flyList = flyList;
        this.apiUrl = apiUrl;
    }

    @Override
    public void run() {

        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        try {
            URL url = new URL(urlToUse);
            urlConn = url.openConnection();
            if (urlConn != null) {
                urlConn.setReadTimeout(60 * 1000);
            }
            
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            throw new RuntimeException("Exception while calling URL:" + urlToUse, e);
        }
        Airline airline = null;
        try {
            airline = gson.fromJson(sb.toString(), Airline.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        for(Flights flight : airline.getFlights()) {
            flight.setUrl(apiUrl);
        }
        flyList.add(airline);

//        try {
//            WebDriver driver = new HtmlUnitDriver();
//            WebClient client = new WebClient();
//            driver.get(urlToUse);
//            String jsonStr = "";
//            
//            if(client.getPage(urlToUse).getWebResponse().getStatusCode() == 200) {
//                jsonStr = driver.getPageSource();
//            
//                Airline airline = gson.fromJson(jsonStr, Airline.class);
//                flyList.add(airline);
//            } 
//              
//        } catch (NotFoundException e) {
//            throw new NotFoundException();
//        } catch (IOException ex) {
//            
//        } catch (FailingHttpStatusCodeException ex) {
//            
//        }
    }

}
