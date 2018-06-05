/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise2;

/**
 *
 * @author kasper
 */
/* 

 a)
 The method next() in the class Even should always return an even number (see code snippet
 below). Implement a program that demonstrates that this is not always true in a multithreaded
 program.

 Hint: Create two threads, which both should call the next() method on the same Even object
 and test if the return value is equal.

 Explain what happens?
 How common is the problem?

 b) Introduce synchronization, so the execution of the method is atomic

 */
public class Exercise2 {

    static Even even = new Even();

    public static void main(String[] args) {

        Thread t1 = new threadEven1();
       // Thread t2 = new threadEven1();
        Thread t2 = new threadEven2();

        t1.start();
        t2.start();
    }

    public static class Even {

        private int n = 0;

        public synchronized int next() {
            n++;
            n++;
            return n;
        }
    }

//    public static class threadEven1 extends Thread {
//
//        @Override
//        public void run() {
//            
//            while (true) {
//                int a = even.next();
//                if (a % 2 == 1) {
//                    System.out.println(a);
//                }
//            }
//
//        }
//    }
    public static class threadEven1 extends Thread {

        @Override
        public void run() {
            System.out.println(even.next());

        }
    }

    public static class threadEven2 extends Thread {

        @Override
        public void run() {
            System.out.println(even.next());

        }
    }

    /*
    
    Thread1 går igang med at udarbejdet opgaven, og når at tælle 1 op før thread2 er gået igang, 
    starter thread2 på 1 fremfor starte på 0 og ender med resultatet 3
    
    hvis man sætter synchronized ind gør det at kun en thread kan have fat i objectet af gangen
    og det gør at den ene når at tælle op før den andet
    
     */
}
