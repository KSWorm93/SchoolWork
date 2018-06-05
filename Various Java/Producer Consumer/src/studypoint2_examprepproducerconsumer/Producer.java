/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studypoint2_examprepproducerconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kasper
 */
public class Producer implements Runnable {
    
    String name;
    BlockingQueue<Long> s1;
    BlockingQueue<Long> s2;
    
    public Producer(String name, BlockingQueue<Long> s1, BlockingQueue<Long> s2) {
        this.name = name;
        this.s1 = s1;
        this.s2 = s2;
    }
    
    @Override
    public void run() {
        while(true) {
            long temp;
            
            try {
                temp = fib(s1.poll());
            } catch (NullPointerException e) {
                    break;
            }
            
            try {
                s2.put(temp);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private long fib(long n) {
        if ((n == 0) || (n == 1)) {
        return n;
        } else {
        return fib(n - 1) + fib(n - 2);
        }
    }

    public void setBq(BlockingQueue<Long> s1) {
        this.s1 = s1;
    }
}
