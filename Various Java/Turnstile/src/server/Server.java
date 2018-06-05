/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author kasper
 */
public class Server {

    private static boolean keepRunning = true;
    private static ServerSocket serverSocket;
    private static List<TurnstileHandler> tsList;
    private static List<MonitorHandler> monList;
    Scanner input;
    PrintWriter writer;

    //Global amount of spectators
    public static int spectators = 0;

    //Get id from server
    public static List<TurnstileHandler> getTSList() {
        return tsList;
    }
    //Get id from server
    public static List<MonitorHandler> getMonList() {
        return monList;
    }

    public void runServer() {
        //command to connect in terminal: telnet localhost 1313
        int port = 1313;
        String ip = "localhost";

        monList = new ArrayList();
        tsList = new ArrayList();

        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(ip, port));
            do {
                Socket socket = serverSocket.accept(); //Important Blocking call
                input = new Scanner(socket.getInputStream());
                writer = new PrintWriter(socket.getOutputStream(), true);
                writer.println("Write 1 to become a Turnstile");
                writer.println("Write 2 to become a Monitor");
                String choice = input.nextLine(); //IMPORTANT blocking call
                
                //Check whether you are a turnstile or a monitor based on scanned value
                if (choice.equals("2")) {
                    MonitorHandler mon = new MonitorHandler(socket);
                    monList.add(mon);
                    mon.start();

                } else {
                    TurnstileHandler ch = new TurnstileHandler(socket);
                    tsList.add(ch);
                    ch.start();
                }

                //While loop to keep the server running
            } while (keepRunning);
        } catch (IOException ex) {
            System.out.println("Exception caught: " + ex);
        }

    }

    public void send(String msg) {
        for (TurnstileHandler ch : tsList) {
            ch.send(msg);
        }
    }

    public static void main(String[] args) {
        new Server().runServer();
    }
}
