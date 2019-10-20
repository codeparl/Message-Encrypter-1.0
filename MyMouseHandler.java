/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package massageencrypter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;

/**
 *
 * @author Hassan
 */
public class MyMouseHandler  extends MouseAdapter{
    
    private JPopupMenu  pm;
    public MyMouseHandler(JPopupMenu  pm){
    
    this.pm = pm;
        
        
    
    
    
    }
    
    
    public void mousePressed(MouseEvent e){
    
        checkTrigledEvent(e);
    
    }
    
    public void mouseReleased(MouseEvent e){
    
    checkTrigledEvent(e);
    }
    
    
    
    
    private void checkTrigledEvent(MouseEvent e){
    
    if(e.isPopupTrigger()){
    
    this.pm.show(e.getComponent(), e.getX(), e.getY());
    
    }
    
    
    }

    
    
    
    
}





