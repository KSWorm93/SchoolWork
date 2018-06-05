/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise3;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author kasper
 */
public class FileSaverThread extends Thread {

    List<String> lines;

    public FileSaverThread(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public void run() {
        while (true) {
            
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                Logger.getLogger(FileSaverThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String userDir = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
            
            try {
                FileWriter writer = new FileWriter("backup.txt", false);
                PrintWriter out = new PrintWriter(writer);
                for(String line : lines){
                    out.println(line);
                }
                    
                out.close();
                
                
            } catch (IOException iOException) {
            }
        }
    }

    /*
    
    
     */
}
