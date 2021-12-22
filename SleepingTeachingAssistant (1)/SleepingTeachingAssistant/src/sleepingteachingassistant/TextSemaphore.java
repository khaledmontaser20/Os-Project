/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sleepingteachingassistant;

import java.util.concurrent.Semaphore;
import javax.swing.JTextField;

/**
 *
 * @author Hassan
 */
public class TextSemaphore extends writeandread{
    Semaphore s; 
    
    public TextSemaphore(JTextField count) {
        super(count); 
        s = new Semaphore(1); 
    }
    
    public void write(String text) { 
        try{ 
            s.acquire(); 
            super.write(text);
        }catch (InterruptedException ex) { 
        // Interrupted exception 
                }finally { 
            s.release();
            }
        }
        
    
    public String read() { 
            String tempValue; 
            try{ 
                s.acquire(); 
                tempValue = super.read(); 
            }catch (InterruptedException ex) { 
                // Interrupted exception
                return "........";
            }finally{ 
                s. release() ; 
            }
            return tempValue; 
        }
    
}

class writeandread { 
    private JTextField count;
    
    writeandread(JTextField count) { 
        this.count = count; 
    }
     
   public void write(String text) { 
        count.setText(text); 
    }
    
    public String read() { 
        return count.getText(); 
    }
    
}