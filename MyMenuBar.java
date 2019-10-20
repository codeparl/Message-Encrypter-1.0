/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package massageencrypter;
import java.awt.*;
import java.awt.event.*;
import  javax.swing.*;


/**
 *
 * @author Hassan
 */
public class MyMenuBar  extends JMenuBar{
    
    private JTextArea  encryptText;
    private JTextArea  decryptText;
    
    public MyMenuBar(JTextArea text1, JTextArea text2){
    
    this.decryptText = text2;
    this.encryptText = text1;      
    
    createMenuBar();
    
    
    
    
    }
    
    
     //add menubar to this window
    private  void createMenuBar(){
    
    ImageIcon  openFileIcon = new ImageIcon(MassageEncrypter.class.getResource("Open.png"));
    ImageIcon  saveFileIcon = new ImageIcon(MassageEncrypter.class.getResource("Save.png"));
    ImageIcon  exitIcon = new ImageIcon(MassageEncrypter.class.getResource("Exit.png"));

    JMenu   file = new JMenu("File");
    JMenuItem  exit = new JMenuItem("Close",exitIcon);
    exit.addActionListener(new ActionListener(){
    
    public void actionPerformed(ActionEvent e){
    System.exit(0);
    }
    });
    
    JMenuItem  open = new JMenuItem("Open",openFileIcon);
    
    
    
    JMenuItem  save = new JMenuItem("Save",saveFileIcon);
    file.add(open);
    file.add(save);
    file.addSeparator();
    file.add(exit);
    
    this.add(file);
    this.add(createEditMenu());
   
    
    }
    
    
   private JMenu createEditMenu(){
    
    JMenu  edit = new JMenu("Edit");
    JMenuItem  copy = new JMenuItem("Copy");
    edit.add(copy);
    return edit;
    
    }
    
}
