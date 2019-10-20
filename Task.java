/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package massageencrypter;
import java.awt.Toolkit;
import java.beans.*;
import java.util.Random;
import javax.swing.*;


/**
 *
 * @author Hassan
 */
public class Task extends SwingWorker<Void, Void> {
    
    private Random random;
    private JButton  encrptBtn;
    private JTextArea  textBody;
    
  
    public Task(JButton btn){
    this.random = new Random();
    this.encrptBtn = btn;
    
    
    }
    
    public Void doInBackground(){
    
        int progress = 0;
    this.setProgress(0);
    
    while(progress < 100){
    
        
        try{
           
            
           Thread.sleep(random.nextInt(10));

        
        
        }catch(Exception exp){}
        progress += random.nextInt(10);
        this.setProgress(Math.min(progress, 100));
    
    }
    
        
    
    return null;
    }
    
    public void done(){
    
    Toolkit.getDefaultToolkit().beep();
    this.encrptBtn.setEnabled(true);

    
    }
    
    
}
