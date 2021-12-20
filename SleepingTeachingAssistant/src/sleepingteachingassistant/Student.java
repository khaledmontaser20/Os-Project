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
public class Student implements Runnable{

    private int progTime;
    private int stNo;
    private int taNo;
    private int x;
    private SignalSemaphore wakeup;
    private Semaphore chairs;
    private Semaphore teacherAvailable;
    private Thread t;
    Display d;
    private volatile boolean exit = false;

    public Student(int x,int progTime, SignalSemaphore wakeup, Semaphore chairs, Semaphore teacherAvailable, int num, int taNo,Display d) {
        this.progTime = progTime;
        this.stNo = num;
        this.taNo = taNo;
        this.wakeup = wakeup;
        this.chairs = chairs;
        this.teacherAvailable = teacherAvailable;
        this.t = t.currentThread();
        this.d = d;
        this.x = x;
    }

    @Override
    public void run() {
        while(!exit){
            try {
                System.out.println("Student "+stNo+" has started programming asignment for "+progTime+" seconds");
                t.sleep(progTime*1000);
                System.out.println("Student "+stNo+" is checking if Ta is available.");
                if(teacherAvailable.tryAcquire()){
                    try{
                    wakeup.take();
                    System.out.println("Student "+stNo+" has wakeup TA ");
                    System.out.println("Student "+stNo+" has started consulting with TA");
                    d.out2.setText(""+teacherAvailable.availablePermits());
                    d.out1.setText(""+((x-teacherAvailable.availablePermits())));
                    t.sleep(10000);
                    System.out.println("Student "+stNo+" has stopped consulting with TA");
                    }catch(InterruptedException e){
                        continue;
                    }
                    finally{
                        teacherAvailable.release();
                        d.out2.setText(""+teacherAvailable.availablePermits());
                        d.out1.setText(""+((x-teacherAvailable.availablePermits())));
                        this.stop();
                    }
                }
                else{
                    System.out.println("Student "+stNo+" found out the TA was not available checking for"
                            + " the chair");
                    if(chairs.tryAcquire())
                    {
                        try{
                            System.out.println("Student "+ stNo + " waits on the chair"
                                + " he is no "+((chairs.availablePermits()))+ " in line." );
                            d.out3.setText(""+((chairs.availablePermits())));
                            teacherAvailable.acquire();
                            System.out.println("Student "+stNo+" has started consulting with TA ");
                            d.out2.setText(""+teacherAvailable.availablePermits());
                            d.out1.setText(""+((x-teacherAvailable.availablePermits())));
                            t.sleep(3000);
                            System.out.println("Student "+stNo+" has stopped consulting with TA ");
                        }catch(InterruptedException e){
                            continue;
                        }finally{
                            teacherAvailable.release();
                            d.out2.setText(""+teacherAvailable.availablePermits());
                            d.out1.setText(""+((x-teacherAvailable.availablePermits())));
                            this.stop();
                        }
                    }
                    else{
                        System.out.println("Student "+stNo+"  found out the TA was not available and no chairs available he will back later");
                        t.sleep(10000);
                        d.out4.setText(t.getName());
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
                }
    }
    
    public void stop(){
        exit = true;
    }
    
    
}
