/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise1;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kasper
 */
public class Exercise1 {

    /**
     * @param args the command line arguments
     */
    /*
    
     Exercise 1 (create, start and end threads)
     Create a program that starts 3 different parallel threads.
     task1 : Compute and print the sum of all numbers from 1 to 1 billion
    
     task2 : Print the numbers from 1 to 5. Pause for 2 seconds between each print.
    
     task3 : Print all numbers from 10 and up. Pause for 3 seconds between each print.
    
     The program should stop task3 after 10 seconds.
    
    
     Hint: For the sum in task-one, use the a long data type
     Hint2: Let the main thread sleep for 10 seconds after starting task 3
     Hint3: Use a Boolean value in the loop in task-3 to terminate task3 (let the main thread
     change the value of the boolean value.
    
     */
    
         static boolean stop = false;
      
 

    public static void main(String[] args) {
        
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        Thread3 thread3 = new Thread3();

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Exercise1.class.getName()).log(Level.SEVERE, null, ex);
        }
            stop = true;

    }

    public static class Thread1 extends Thread {

        long sum;

        @Override
        public void run() {

            for (int i = 0; i < 1000000000; i++) {
                sum += i;
            }
            System.out.println(sum);
        }
    }

    public static class Thread2 extends Thread {

        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {

                System.out.println(i);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Exercise1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public static class Thread3 extends Thread {

        @Override
        public void run() {
//            while (stop == false) {
                for (int i = 10; i > 9; i++) {
                    System.out.println(i);

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Exercise1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (stop) {
                        break;
                    }
//                }
            }
        }

    }

}
