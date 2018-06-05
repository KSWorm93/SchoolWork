/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author kasper
 */
public class MonitorHandler extends Thread {

    Scanner input;
    PrintWriter writer;
    Socket socket;

    int onlineID = Server.getMonList().size();
    //Initial customer set to 1 so ++ function can be used

    MonitorHandler(Socket socket) throws IOException {
        input = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        writer.println("You are a Monitor, your id is: " + (onlineID + 1));
        String message = input.nextLine(); //IMPORTANT blocking call
        while (!message.equals("STOP")) {
            writer.println("Your command was: " + message.toUpperCase()); //The command/msg you sent
            writer.println("Amount of spectators from all Turnstiles: " + Server.spectators);
            message = input.nextLine(); //IMPORTANT blocking call
        }
        writer.println("STOP command received");//Echo the stop message back to the client for a nice closedown
        try {
            writer.println("Monitor Closed");
            socket.close();
        } catch (IOException ex) {
        }
    }

    public void send(String msg) {
        writer.println(msg.toUpperCase());
        writer.println();
    }

}
