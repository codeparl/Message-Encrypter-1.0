/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package massageencrypter;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.StyledEditorKit;



/**
 *
 * @author Hassan
 */
public class MassageEncrypter extends JPanel  implements ActionListener,PropertyChangeListener, DocumentListener{
    
    
    private static final JFrame   win = new JFrame("Message Encrypter - By Hassan Mugabo");
    private  Color  paneColor = new Color(202,232,237);
    private Color bg = new Color(217,231,232);
    private JButton  encryptBtn;
    private JPasswordField  encKeyField;
    private JTextArea  textBody;
    private JTextArea  depTxtBody;
    
    private JButton    restBtn;
    private JButton    restBtn2;
    
    private JCheckBox  inputMask;
    private JCheckBox  inputMask2;
    
    private JButton  pastBtn;
    private JButton  pastBtn2;

    

    private final DES myDes = new DES();
    private String plainMessage = "";
    private String commonKey; 
    private String encryptedMessage = "";
    
    private JLabel  decLen  = new JLabel("0");
    private     JLabel  encLen  = new JLabel("0");

    private JButton decypBtn;
    private JPasswordField  dcypField;
    private JPopupMenu popupMenu;
    private JPanel  optionPane;
    private JProgressBar  progressBar;
    private JLabel   progressLabel;
    private JTabbedPane  tab;
    private Task task;
    private Random random;

    
    private final ImageIcon icon1 =new ImageIcon( MassageEncrypter.class.getResource("Lock_1.png"));
    private final ImageIcon icon2 =new ImageIcon( MassageEncrypter.class.getResource("Erase.png"));
    private   ImageIcon  exitIcon = new ImageIcon(MassageEncrypter.class.getResource("Exit.png"));
    private   ImageIcon  past = new ImageIcon(MassageEncrypter.class.getResource("Paste.png"));



    private static JMenuBar  menuBar;
    public MassageEncrypter(){
    
    super(new BorderLayout());
   
    
    popupMenu = createPopupMenu();
    encryptBtn = new JButton("Encrypt",icon1);
   encryptBtn.setEnabled(false);
   restBtn = new JButton("Clear");
   restBtn.addActionListener(this);
   restBtn2 = new JButton("Clear");
   restBtn2.addActionListener(this);
   random = new Random();
   inputMask = new JCheckBox("Mask");
   inputMask2 = new JCheckBox("Mask");
   
   pastBtn = new JButton("Paste");
   pastBtn2 = new JButton("Paste");

   
   optionPane = createOptionControlPane();
   this.progressBar = creatProgressBar();
   progressLabel = new JLabel("Progress: ");
   tab = new JTabbedPane();
   textBody = new JTextArea();
   textBody.setLineWrap(true);
   textBody.setWrapStyleWord(true);
   textBody.setMargin(new Insets(10,10,10,10));
   textBody.getDocument().addDocumentListener(this);
   textBody.addMouseListener(new MyMouseHandler(popupMenu));
   
   depTxtBody = new JTextArea();
   depTxtBody.getDocument().addDocumentListener(this);
   depTxtBody.addMouseListener(new MyMouseHandler(popupMenu));

   depTxtBody.setMargin(new Insets(10,10,10,10));

   
    encKeyField  = new JPasswordField(20);
    encKeyField.addActionListener(this);
  //  textFieldHandler = new myDocListener(encKeyField);
    encKeyField.getDocument().addDocumentListener(new myDocListener(encKeyField,encryptBtn,restBtn));
   // encKeyField.addMouseListener(new MyMouseHandler(popupMenu));
    
      
    encryptBtn.addActionListener(this);
    
   dcypField = new JPasswordField(20);
   decypBtn  = new JButton("Decrypt");
   decypBtn.setEnabled(false);
   decypBtn.addActionListener(this);
   
   dcypField.getDocument().addDocumentListener(new myDocListener(dcypField,decypBtn,restBtn2));
   
   
   JPanel  cp = new JPanel();
    menuBar = new MyMenuBar(textBody,depTxtBody);
    cp.setLayout(new BoxLayout(cp,BoxLayout.Y_AXIS));
    setBorder(BorderFactory.createEmptyBorder(7,7,7,7));
    JPanel encryptPane = setupEncryptPane();
    tab.addTab("Message to encrypt", encryptPane);
    tab.setBackground(bg);
    
    JPanel  decryptPane = setupDecryptPane();
    tab.addTab("Message to decrypt", decryptPane);
    
    cp.add(tab);
    
    setBackground(bg);
    add(createNorthPane(),BorderLayout.NORTH);
    add(cp,BorderLayout.CENTER);
    add(createStatusPane(),BorderLayout.SOUTH);
    add(optionPane,BorderLayout.WEST);
    
    }
    
    private JProgressBar creatProgressBar(){
    
   JProgressBar     progressBar = new JProgressBar(0,100);
   
  progressBar.setValue(0);
  progressBar.setStringPainted(true);
   
    
    return progressBar;
    }
   
   
    private final JPanel createNorthPane(){
    
    JPanel np = new JPanel(new FlowLayout(FlowLayout.LEFT));
    np.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    
    Font  font = new Font(Font.SANS_SERIF,Font.BOLD,14);
    Color clr = Color.BLUE;
    JLabel   head = new JLabel("Type a message you wish to encrypt and then press encrypt button after");
    JLabel   head3 = new JLabel("specifying your encryption KEY.");
    head.setFont(font);
    head3.setFont(font);
    head.setForeground(clr);
    head3.setForeground(clr);
    
    
    JPanel  hp = new JPanel();
    hp.setOpaque(false);
    hp.setLayout(new BoxLayout(hp, BoxLayout.Y_AXIS));
    hp.add(head);
    hp.add(head3);
    np.add(hp);
    np.setBackground(bg);
    np.setOpaque(false);
    return np;
    
    }
    
    private final JPanel  setupEncryptPane(){
    
    JPanel  ep = new JPanel(new BorderLayout());
    ep.setBorder(BorderFactory.createTitledBorder("Massage Encryption"));
    ep.add(createInputPane(encKeyField,inputMask,encryptBtn,restBtn,pastBtn),BorderLayout.NORTH);
    ep.setBackground(paneColor);
    textBody.append("Enter your message here to encrypt.\n\n");
    
    JScrollPane  sp = new JScrollPane(textBody);
    sp.setPreferredSize(new Dimension(400,250));
    sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    ep.add(sp);
    
    
    
    return ep;
    }
    
    private JPanel createInputPane(JPasswordField pf,JCheckBox mask, JButton btn,JButton rst, JButton pastBtn){
    
    JPanel ip = new JPanel();
   
    JLabel  lb = new JLabel("Key: ");
    mask.setOpaque(false);
    lb.setLabelFor(pf);
    
    Action  action = new StyledEditorKit.PasteAction();
    action.putValue(Action.NAME, "");
    pastBtn.addActionListener(action);
    
    ip.add(rst);
    rst.setIcon(icon2);
    ip.add(pastBtn);
    pastBtn.setOpaque(false);
    pastBtn.setIcon(past);
    ip.add(lb);
    ip.add(pf);
    ip.add(mask);
    ip.add(btn);
    btn.setOpaque(false);
    rst.setOpaque(false);
    ip.setOpaque(false);
    return ip;
    
    
    }
    
    private JPopupMenu createPopupMenu(){
    
    JPopupMenu  pm = new JPopupMenu();
    
    //create actions of this popup menu
    Action action = new StyledEditorKit.CutAction();
    action.putValue(Action.NAME, "Cut");
    pm.add(action);
    
    action = new StyledEditorKit.CopyAction();
    action.putValue(Action.NAME, "Copy");
    pm.add(action);
    
    action = new StyledEditorKit.PasteAction();
    action.putValue(Action.NAME, "Paste");
    pm.add(action);
    
     pm.setBorder(new LineBorder(new Color(188,227,234)));

    
    
    
    return pm;
    
    }
    
    
    
    private final JPanel setupDecryptPane(){
    
    JPanel  dp = new JPanel(new BorderLayout());
    dp.setBorder(BorderFactory.createTitledBorder("Encrypted Message"));
    dp.setBackground(paneColor);
    JScrollPane   sp = new JScrollPane(depTxtBody);
    depTxtBody.setLineWrap(true);
    depTxtBody.setWrapStyleWord(true);
    
    
    sp.setPreferredSize(new Dimension(400,250));
    sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    dp.add(createInputPane(dcypField,inputMask2,decypBtn,restBtn2,pastBtn2),BorderLayout.PAGE_START);
    dp.add(sp,BorderLayout.CENTER);
    return dp;
    
    }

    private JPanel createStatusPane(){
    
    JPanel   stp = new JPanel(new FlowLayout(FlowLayout.LEFT));
    stp.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    stp.setBackground(bg);
    
    stp.add(new JLabel("Encrypted Length: "));
    stp.add(encLen);
    
    JButton  exitBtn = new JButton(" Exit   ",exitIcon);
    exitBtn.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){System.exit(0);}});
    exitBtn.setToolTipText("Exit the application");
    stp.add(new JLabel("        Decrypted Length: "));
    stp.add(decLen);
    stp.add(progressLabel);
    progressLabel.setLabelFor(progressBar);
    stp.add(progressBar);
    stp.add(exitBtn);
    exitBtn.setOpaque(false);
    return stp;
    
    }
    
    
    public void propertyChange(PropertyChangeEvent e){
    if(e.getPropertyName() == "progress"){
    
       int progress = (Integer) e.getNewValue();
       try{
       this.progressBar.setValue(progress);

       }catch(Exception exp){}
    }
    
    
    }
    
    
    
    private class Task extends SwingWorker<Void,Void>{
    
    public Void doInBackground(){
    
        int progress = 0;
    this.setProgress(0);
    
    while(progress < 100){
    
        
        try{
           
 
           Thread.sleep(random.nextInt(0));

        
        
        }catch(Exception exp){}
        progress += random.nextInt(0);
        this.setProgress(Math.min(progress, 100));
    }
    
        
    
    return null;
    }
    
    public void done(){
        
        try{
   encryptedMessage = myDes.encrypt(plainMessage, commonKey);

    Toolkit.getDefaultToolkit().beep();
    textBody.setText(null);
    encryptBtn.setEnabled(true);
   textBody.setText(encryptedMessage);
   setCursor(null);
   
        }catch(Exception exp){}
    
    }
    
    
    
    
    }
    
    
    
    
    
    
    
    
    
    
    
    
    public void actionPerformed(ActionEvent e){
    
    if(e.getSource() == restBtn){
    
        textBody.setText(null);
        encKeyField.setText(null);
    
    }else if(e.getSource() == restBtn2){
    
        depTxtBody.setText(null);
        dcypField.setText(null);
    
    }else if(e.getSource() == encKeyField ){
        
    commonKey = e.getActionCommand();
    decLen.setText(commonKey);
    }else if(e.getSource() == encryptBtn){
        
        boolean passwordIncorrect = true;
      try{
          
              
          if(passwordIncorrect){
          textBody.setText(null);
          textBody.setText(encryptedMessage);
          task = new Task();
          task.addPropertyChangeListener(this);
          task.execute();
          
          }
      }catch(Exception exp){
          JOptionPane.showMessageDialog(encKeyField,"Invalid Secret-Key, please define it again and proceed"
                  + "\nYou must enter a long key or otherwise you will"
                  + "\nkeep recieving this error message",
                  "Wrong Secret-Key",JOptionPane.ERROR_MESSAGE);
               passwordIncorrect = false;  

      }//end catch

    }else if(e.getSource() == decypBtn){
            boolean passwordIncorrect = true;

        depTxtBody.setText(null);
        try{
        String dec = myDes.decrypt(encryptedMessage, commonKey);
        depTxtBody.setText(dec);
        
        }catch(Exception exp){
            
          JOptionPane.showMessageDialog(encKeyField,"Invalid Secret-Key, please enter your encryption key for this message."
                  + "\nYour key  must match the encyption key of this message to decrypt or otherwise you will"
                  + "\nkeep recieving this error message.",
                  "Wrong Secret-Key",JOptionPane.ERROR_MESSAGE);
               passwordIncorrect = false;
               
               
      }   
           
    }
        
    
    }
    
    //--------------option control pane------------------//
    private JPanel createOptionControlPane(){ 
    
    JPanel  otp = new JPanel();
    otp.setLayout(new BoxLayout(otp, BoxLayout.Y_AXIS));
    otp.setPreferredSize(new Dimension(130,170));
    otp.setBorder(BorderFactory.createTitledBorder("Option Control Pane"));
    otp.setBackground(bg);
    Box box1 = Box.createVerticalBox();
    box1.setBorder(BorderFactory.createTitledBorder("Window Options"));
    box1.add(Box.createVerticalStrut(10));
    
  final   JCheckBox resize = new JCheckBox("Resizable");
  final  JCheckBox move  = new JCheckBox("Movable");
    
    box1.add(resize);
    box1.add(Box.createVerticalStrut(5));
    resize.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){
    
        if(resize.isSelected()){
        
            win.setResizable(true);
        }else
            win.setResizable(false);
    }
    });
    resize.setOpaque(false);
    
    box1.add(move);
    box1.add(Box.createVerticalStrut(5));
    move.setOpaque(false);
    
   otp.add(box1);
        
    return otp;
    
    }
    public void insertUpdate(DocumentEvent e){
    
        int len  = e.getDocument().getLength();
        encLen.setText(len+"");
        
        if(len <= 0){
            restBtn.setEnabled(false);
        }else{ 
            restBtn.setEnabled(true);
        }
        
        
        try{
        plainMessage = e.getDocument().getText(0, len-1);
        
        }catch(Exception exp){
        exp.printStackTrace();
        
        }
    
    }
    
    public void removeUpdate(DocumentEvent e){
    
        int len  = e.getDocument().getLength();
        encLen.setText(len+"");
        
          
        if(len <= 0){
             restBtn.setEnabled(false);
        }else{ 
            restBtn.setEnabled(true);
        }
        
          if(depTxtBody.getDocument().getLength() <= 0){
         restBtn2.setEnabled(false);
                }else {
        restBtn2.setEnabled(true);
        
        }
        
    
    }
    public void changedUpdate(DocumentEvent e){
       int len  = e.getDocument().getLength();
        encLen.setText(len+"");
        
        if(len <= 0){
            restBtn.setEnabled(false);
        }else{ 
            restBtn.setEnabled(true);
        }
        
        
        try{
        plainMessage = e.getDocument().getText(0, len-1);
        
        }catch(Exception exp){
        exp.printStackTrace();
        
        }
    
        
    
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        JFrame.setDefaultLookAndFeelDecorated(false);
        
        final java.net.URL icon = MassageEncrypter.class.getResource("Lock.png");
        
        final MassageEncrypter  app = new MassageEncrypter();
        SwingUtilities.invokeLater(new Runnable(){
        
        public void run(){
        
        
        win.setDefaultCloseOperation(3);
        win.setIconImage(new ImageIcon(icon).getImage());
        win.setContentPane(app);
        win.setJMenuBar(menuBar);
        win.setSize(750, 550);
        win.setVisible(true);
        win.setResizable(false);
        win.setLocationRelativeTo(null);
        }
        });
        
        
        
        
        
    }
    
    
    
    
    //static code to obtain look-and-feel for the underlying system
    static{
    
    try{
    UIManager.LookAndFeelInfo[]  lookInfo  = UIManager.getInstalledLookAndFeels();

    
for(int i = 0; i < lookInfo.length; ++i){
    
   if(lookInfo[i].getName().equals("Windows Classic"))
       
   UIManager.setLookAndFeel(lookInfo[i].getClassName());
}//end if

SwingUtilities.updateComponentTreeUI(win);
    }catch(Exception e){}
    
    }
    
}//end main class
 