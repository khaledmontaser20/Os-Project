package javaapplication5;

 
 //THIS IS THE MAIN CLASS 
/**
 * University. You should not change anything in this class, but you can adjust
 * the constants to simulate different results.
 *
 * Run this class as a simulation.
 *
  
 * @author Girts Strazdins, 2016-02-08
 */
public class University {

    
    
    Display d1 = new Display();
    /**
     * How many chairs does the Teaching Assistant have in front of his office
     */
    private   static int NUMBER_OF_CHAIRS  =1  ;

    /**
     * Number of students which will be studying in parallel
     */
    private   static int NUMBER_OF_STUDENTS = 3;
    private   static int NUMBER_OF_TAs = 0;

        public  University (int NUMBER_OF_CHAIRS , int NUMBER_OF_STUDENTS  , int NUMBER_OF_TAs)
        {   
            this.NUMBER_OF_CHAIRS =NUMBER_OF_CHAIRS;
            this.NUMBER_OF_STUDENTS =NUMBER_OF_STUDENTS;
            this.NUMBER_OF_TAs =NUMBER_OF_TAs;    
        }
    
    public static void main(String[] args) {
        
//        for (int i=0 ;i<2;i++)
//        {
//        TeachingAssistant  aa= new TeachingAssistant(NUMBER_OF_CHAIRS);
//        }
        
        
        TeachingAssistant ta = new TeachingAssistant(NUMBER_OF_CHAIRS);
        // Get the TA starting
        ta.start();

        // Start the students
        Student[] students = new Student[NUMBER_OF_STUDENTS];
        for (int i = 0; i < NUMBER_OF_STUDENTS; ++i) {
            Student s = new Student();
            s.setAssistant(ta);
            s.start();
            students[i] = s;
        }

        try {
            // Wait for all students to get tired/--> Done
            for (int i = 0; i < NUMBER_OF_STUDENTS; ++i) {
                students[i].join();
            }

            // Send the TA home
            ta.goHome();
            ta.join();

        } catch (InterruptedException ex) {
            System.out.println("Someone crashed into the university business...");
        }
    }
}
