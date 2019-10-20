/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package massageencrypter;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Hassan
 */

public class myDocListener implements  DocumentListener {
    
    
    private int docLeng;
    private JTextField  textField;
    private JButton    button;
    private JButton    resetBtn;
    public myDocListener(JTextField tf, JButton  button,JButton resetBtn){
    this.textField = tf;
    this.button = button;
    this.resetBtn = resetBtn;
    
    }
    
     public void insertUpdate(DocumentEvent e){
         enableButton(e);

    
        }
        
    public void removeUpdate(DocumentEvent e){
   
          enableButton(e);

            
        
        }
        
    
    
    public void changedUpdate(DocumentEvent e){
        
   enableButton(e);
    
    
    }
    
   private void enableButton(DocumentEvent e){
   
                 
    if(e.getDocument().getLength() < 12){
    this.button.setEnabled(false);
    }else 
        this.button.setEnabled(true);
    
    if(e.getDocument().getLength() <= 0){
    
        this.resetBtn.setEnabled(false);
    
    }else
        this.resetBtn.setEnabled(true);
    
   
   }
    
   
    
}//end class 
