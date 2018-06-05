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
public class TurnstileHandler extends Thread {

    Scanner input;
    PrintWriter writer;
    Socket socket;
    private int internSpectator = 0;

    int onlineID = Server.getTSList().size();
    //Initial customer set to 1 so ++ function can be used

    TurnstileHandler(Socket socket) throws IOException {
        input = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        writer.println("You are a Turnstile, your id is: " + (onlineID + 1));
        String message = input.nextLine(); //IMPORTANT blocking call
        while (!message.equals("STOP")) {
            writer.println("Your command was: " + message.toUpperCase()); //The command/msg you sent

            //Increment the values from the global and internal spectator by 1
            Server.spectators++;
            internSpectator++;

            //Print out the values
            writer.println("Amount of spectators: " + Server.spectators);
            writer.println("Internal value: " + internSpectator);

            message = input.nextLine(); //IMPORTANT blocking call
        }
        writer.println("STOP command received");//Echo the stop message back to the client for a nice closedown
        try {
            writer.println("Turnstile Closed");
            socket.close();
        } catch (IOException ex) {
        }
    }

    public void send(String msg) {
        writer.println(msg.toUpperCase());
        writer.println();
    }
}
