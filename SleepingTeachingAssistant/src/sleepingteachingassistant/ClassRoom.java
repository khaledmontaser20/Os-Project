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
    
    public ClassRoom(int noOfStudents,int noOfTA,int chairsno, SignalSemaphore wakeup, Semaphore chairs, Semaphore teacherAvailable) {
        this.noOfStudents = noOfStudents;
        this.noOfTeachers = noOfTA;
        this.wakeup = wakeup;
        this.chairs = chairs;
        this.chairsno = chairsno;
        this.teacherAvailable = teacherAvailable;
    }
    
    public void start(Display d){
        int currstno = 0,currtano = 0;
        for (int i = 0; i < noOfTeachers; i++) { 
            currtano = i+1;
            ta = new TeachingAssistant(noOfStudents,wakeup, chairs, teacherAvailable, currtano, chairsno,d);
            t2 = new Thread(ta);
            t2.start();
        }
        for (int i = 0; i < noOfStudents; i++) {
            currstno = i+1;
            student = new Student(noOfTeachers,studentwaitRandom.nextInt(10),wakeup,chairs,teacherAvailable,currstno,currtano,d);
            t1 = new Thread(student);
            t1.start();
        }
       

    }
        
    
    
}
