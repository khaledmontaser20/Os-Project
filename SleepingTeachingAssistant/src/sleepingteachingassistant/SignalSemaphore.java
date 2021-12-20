/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sleepingteachingassistant;

/**
 *
 * @author Hassan
 */
public class SignalSemaphore {
    private boolean signal = false;
    
    public synchronized void take(){
        this.signal = true;
        this.notify();
    }
    
    public synchronized void release() throws InterruptedException{
        while (!this.signal) wait();            
        this.signal = false;
    }
}
