package callables;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
public class main {

    public static List<Group> groupList = new ArrayList(10);

    public static List<String> urls = new ArrayList<String>(10) {
        {
            //Class A
            add("http://cphbusinessjb.cloudapp.net/CA2/");
            add("http://ca2-ebski.rhcloud.com/CA2New/");
            add("http://ca2-pernille.rhcloud.com/NYCA2/");

            //Class B
            add("https://ca2-ssteinaa.rhcloud.com/CA2/");
            add("https://ca2-ksw.rhcloud.com/DeGuleSider/");
            add("http://ca2-ab207.rhcloud.com/CA2/index.html");
            add("http://ca2-sindt.rhcloud.com/CA2/index.jsp");
            add("http://ca2gruppe8-tocvfan.rhcloud.com/");
            add("https://ca-ichti.rhcloud.com/CA2/");

            //Class COS
            add("https://ca2-9fitteen.rhcloud.com:8443/CA2/");
        }
    };

    public static void main(String[] args) throws IOException, Exception {
        //Group result = new Group();

//        ExecutorService es = Executors.newFixedThreadPool(4);
//
//        List<Future<Group>> futures
//                = new ArrayList<Future<Group>>(urls.size());
//
//        try {
//            long start = System.nanoTime();
//            for (String url : urls) {
//                futures.add(es.submit(new CallableTask(url)));
//            }
//
//            for (Future<Group> future : futures) {
////                result += future.get();
//                groupList.add(future.get());
//            }
//
//            System.out.println("\nBeginning end sout\n");
//            System.out.println("GroupList: " + groupList.get(1).getAuthors());
//            System.out.println("GroupList: " + groupList.get(5).getAuthors());
//            for (int i = 0; i < groupList.size(); i++) {
//                System.out.println("\nFull results: " + groupList.get(i).getAuthors());
//                System.out.println("Full results: " + groupList.get(i).getGroup());
//                System.out.println("Full results: " + groupList.get(i).getMyClass());
//
//            }
//            System.out.println("JsonString: " + getGroupJson());
//            System.out.println("Total time: "
//                    + (System.nanoTime() - start) / 1000000
//                    + "ms");
//        } finally {
//            es.shutdown();
//        }

    }

    public static String getGroupJson() {
        Gson gson = new Gson();

        //Take the groupList and convert it to a jsonString - NOT nicely formatted
        String jsonString = gson.toJson(groupList);
//        System.out.println("jsonString: " + jsonString);

        return jsonString;
    }

}
