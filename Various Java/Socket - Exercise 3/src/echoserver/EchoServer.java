/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kasper
 */
public class EchoServer implements Runnable {

    /**
     * @param args the command line arguments
     */
    Socket s;
    PrintWriter out;
    BufferedReader in;
    String echo;

    public EchoServer(Socket socket) {
        s = socket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                echo = in.readLine();
                out = new PrintWriter(s.getOutputStream(), true);
                if (commandChooser(echo).equalsIgnoreCase("Shit happened")) {
                    out.println("You done fucked up!");
                    s.close();
                } else {
                    out.println(commandChooser(echo));
                }
            } catch (IOException ex) {
                Logger.getLogger(EchoServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public static void main(String[] args) throws IOException {
        String ip = "localhost";
        int port = 1331;
        if (args.length == 2) {
            System.out.println("Args found");
            ip = args[0];
            port = Integer.parseInt(args[1]);
        }

        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress("localhost", 1331)); //port skal være højere end 1024

        while (true) {

            EchoServer e = new EchoServer(ss.accept());
            Thread t1 = new Thread(e);
            t1.start();
        }

    }

    public String commandChooser(String echo) {

        String[] splitter = echo.split("#");

        switch (splitter[0]) {
            case "UPPER":
                return splitter[1].toUpperCase();
            case "LOWER":
                return splitter[1].toLowerCase();
            case "REVERSE":
                return reverse(splitter[1]);
                //return new StringBuilder(splitter[1]).reverse().toString(); //løsning fundet senere som der udnytter en metode i StringBuilder

            case "TRANSLATE":
                return translate(splitter[1]);

            default:
                return "Shit happened";

        }
    }

    public String reverse(String text) {
        String temp = "";
        for (int i = text.length() - 1; i > -1; i--) {
            temp = temp + text.charAt(i);
        }
        return temp;
    }

    public String translate(String text) {
        if (text.equalsIgnoreCase("hund")) {
            return "dog";
        } else {
            return "Word not found";
        }

    }

}
