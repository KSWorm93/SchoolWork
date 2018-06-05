package control;

import facades.ApiFacade;
import facades.UserFacade;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ApiInsertion {
    
    public static void main(String[] args) throws FileNotFoundException {
        
        ApiFacade ctrl = new ApiFacade();
        List<String> urlList = new ArrayList();
        
        try {
            String path = "download.txt";
            String currentLine = "";
            String[] arr;
            BufferedReader br = new BufferedReader(new FileReader(path));
            
            while((currentLine = br.readLine()) != null) {
                arr = currentLine.split(",");
                
                for(int i = 0; i < arr.length; i++) {
                    urlList.add(arr[i]);
                }
            }
            
            ctrl.insertUrlApi(urlList);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
