/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sleepingteachingassistant;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Hassan
 */
public class ClassRoom {
    public int noOfStudents;
    public int noOfTeachers;
    int chairsno;
    SignalSemaphore wakeup;
    Semaphore chairs;
    Semaphore teacherAvailable;
    Thread t1,t2;
    Random studentwaitRandom = new Random();
    Random tasleepRandom = new Random();
    Student student;
    TeachingAssistant ta;
    int count;
    CounterSemaphore c;
    TextSemaphore wr,wr2,wr3,wr4;
    Display d;
    public ClassRoom(int noOfStudents,int noOfTA,int chairsno, SignalSemaphore wakeup, 
            Semaphore chairs, Semaphore teacherAvailable, Display d) {
        this.noOfStudents = noOfStudents;
        this.noOfTeachers = noOfTA;
        this.wakeup = wakeup;
        this.chairs = chairs;
        this.chairsno = chairsno;
        this.d = d;
        this.teacherAvailable = teacherAvailable;
        this.count = 0;
        c = new CounterSemaphore(count);
        wr = new TextSemaphore(d.out1);
        wr2 = new TextSemaphore(d.out2);
        wr3 = new TextSemaphore(d.out3);
        wr4 = new TextSemaphore(d.out4);
    }
    
    public void start(){
        int currstno = 0,currtano = 0;
        for (int i = 0; i < noOfTeachers; i++) { 
            currtano = i+1;
            ta = new TeachingAssistant(noOfStudents,wakeup, chairs, teacherAvailable, currtano, chairsno,d,wr3);
            t2 = new Thread(ta);
            t2.start();
        }
        for (int i = 0; i < noOfStudents; i++) {
            currstno = i+1;
            student = new Student(noOfTeachers,3,wakeup,chairs,teacherAvailable,currstno,currtano,d,c,chairsno,wr,wr2,wr3,wr4);
            t1 = new Thread(student);
            t1.start();
        }
       

    }
        
    
    
}
