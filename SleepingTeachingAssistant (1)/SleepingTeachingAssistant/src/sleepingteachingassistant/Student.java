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
    private int count;
    private int progTime;
    private int stNo;
    private int taNo;
    private int x;
    private SignalSemaphore wakeup;
    private Semaphore chairs;
    private Semaphore teacherAvailable;
    private Thread t;
    Display d;
    CounterSemaphore c;
    TextSemaphore wr,wr2,wr3,wr4;
    public  int chairsno;
    private volatile boolean exit = false;

    public Student(int x,int progTime, SignalSemaphore wakeup, Semaphore chairs, Semaphore teacherAvailable,
            int num, int taNo,Display d, CounterSemaphore c,int chairsno, TextSemaphore wr, TextSemaphore wr2,TextSemaphore wr3, TextSemaphore wr4) {
        this.progTime = progTime;
        this.stNo = num;
        this.taNo = taNo;
        this.wakeup = wakeup;
        this.chairs = chairs;
        this.chairsno = chairsno;
        this.teacherAvailable = teacherAvailable;
        this.t = t.currentThread();
        this.d = d;
        this.x = x;
        this.c = c;
        this.wr = wr;
        this.wr2 = wr2;
        this.wr3 = wr3;
        this.wr4 = wr4;
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
                    wr2.write(""+teacherAvailable.availablePermits());
                    wr.write(""+((x-teacherAvailable.availablePermits())));
                    t.sleep(5000);
                    System.out.println("Student "+stNo+" has stopped consulting with TA");
                    }catch(InterruptedException e){
                        continue;
                    }
                    finally{
                        teacherAvailable.release();
                        wr2.write(""+teacherAvailable.availablePermits());
                        wr.write(""+((x-teacherAvailable.availablePermits())));
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
                            d.out3.setText(""+((chairsno - chairs.availablePermits())));
                            teacherAvailable.acquire();
                            System.out.println("Student "+stNo+" has started consulting with TA ");
                            wr2.write(""+teacherAvailable.availablePermits());
                            wr.write(""+((x-teacherAvailable.availablePermits())));
                            t.sleep(5000);
                            System.out.println("Student "+stNo+" has stopped consulting with TA ");
                        }catch(InterruptedException e){
                            continue;
                        }finally{
                            teacherAvailable.release();
                            wr2.write(""+teacherAvailable.availablePermits());
                            wr.write(""+((x-teacherAvailable.availablePermits())));
                            this.stop();
                        }
                    }
                    else{
                        
                        c.increment();
                        wr4.write(""+ c.value());
                        System.out.println("Student "+stNo+" found out the TA was not available and no chairs available he will back later " + c.value());
                        t.sleep(5000);
                        c.decrement();
                        wr4.write(""+ c.value());
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

class Counter { 
    private int count;
    
    Counter( int count) { 
        this.count = count; 
    }
     
   public void increment() { 
        count++; 
    }
    
    public void decrement() { 
        count--; 
    }
    
    public int value() { 
        return count; 
    }
    
    public String tostring(){ 
        return Integer.toString(count); 
    }
    
}

class CounterSemaphore extends Counter{
    Semaphore s; 
    
    public CounterSemaphore(int count) {
        super(count); 
        s = new Semaphore(1); 
    }
    
    public void increment() { 
        try{ 
            s.acquire(); 
            super.increment( ); 
        }catch (InterruptedException ex) { 
        // Interrupted exception 
                }finally { 
            s.release();
            }
        }
        
    public void decrement() { 
            try { 
                s.acquire(); 
                super.decrement(); 
            }catch (InterruptedException ex) { 
            // Interrupted exception 
            }finally { 
                    s.release(); 
            }
            }
    
    public int value() { 
            int tempValue; 
            try{ 
                s.acquire(); 
                tempValue = super.value(); 
            }catch (InterruptedException ex) { 
                // Interrupted exception
                tempValue =- 1;
            }finally{ 
                s. release() ; 
            }
            return tempValue; 
        }
    
}