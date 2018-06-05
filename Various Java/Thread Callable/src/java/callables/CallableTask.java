package callables;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.util.concurrent.Callable;
import model.Group;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kasper
 */
public class CallableTask implements Callable<Group> {

    public final String currentUrl;

    public CallableTask(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    @Override
    public Group call() throws Exception {
        Group result = null;
        //Gets every URL you send in 
        URL url = new URL(currentUrl);
        //Reads what the url contains
        LineNumberReader in
                = new LineNumberReader(
                        new InputStreamReader(url.openStream()));

        //Using Jsoup to scrape the data from the urls
        Document doc = Jsoup.connect(currentUrl).get();
        Elements currAuthors = doc.select("#authors");
        String authors = currAuthors.text();
        Elements currClass = doc.select("#class");
        String myClass = currClass.text();
        Elements currGroup = doc.select("#group");
        String group = currGroup.text();

        //Simple sout to check if i got the correct data out
//        System.out.println("authors: " + authors);
//        System.out.println("class: " + myClass);
//        System.out.println("group: " + group);
        try {
            String line = null;
            while ((line = in.readLine()) != null) {
                Group myGroup = new Group();

                //Adds the data to myGroup object
                myGroup.setAuthors(authors);
                myGroup.setMyClass(myClass);
                myGroup.setGroup(group);

//                System.out.println("\nGroup sout:");
//                System.out.println("Group" + myGroup.getAuthors());
//                System.out.println("Group" + myGroup.getGroup());
//                System.out.println("Group" + myGroup.getMyClass());
                result = myGroup;
                //result = "\nAuthors: " + authors + " Class: " + myClass + " Group: " + group + " \n--- from group class: "
                //        + myGroup.getAuthors() + " " + myGroup.getGroup() + " " + myGroup.getMyClass() + "\n";
            }
        } finally {
            in.close();
        }

        //System.out.println(currentUrl + result.getGroup());
        return result;
    }

}
