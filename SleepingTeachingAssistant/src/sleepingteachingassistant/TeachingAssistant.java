/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sleepingteachingassistant;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hassan
 */
public class TeachingAssistant implements Runnable{
    private SignalSemaphore wakeup;
    private Semaphore chairs;
    private Semaphore teacherAvailable;
    private Thread t;
    private int taNo;
    private int stNo;
    private int x;
    public  int chairsno;
    Display d;
    private volatile boolean exit = false;
    
    public TeachingAssistant(int noOfTeachers,SignalSemaphore wakeup, Semaphore chairs, Semaphore teacherAvailable, int taNo,int chairsno,Display d) {
        this.wakeup = wakeup;
        this.chairs = chairs;
        this.teacherAvailable = teacherAvailable;
        this.t = t.currentThread();
        this.taNo = taNo;
        this.chairsno = chairsno;
        this.d = d;
        this.x = noOfTeachers;
    }

    @Override
    public void run() {
        while(!exit){
            try {
                System.out.println("No Student left. the TA "+taNo+" is going to sleep");
                wakeup.release();
                System.out.println("The TA "+taNo+" was waken by Student");
                t.sleep(5000);
                if(chairs.availablePermits() != chairsno){
                    do{
                        t.sleep(3000);
                        chairs.release();
                    }while(chairs.availablePermits() != chairsno);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(TeachingAssistant.class.getName()).log(Level.SEVERE, null, ex);
                
            }finally{
                this.stop();
            }
        }
    }   
    public void stop(){
        exit = true;
    }
}
