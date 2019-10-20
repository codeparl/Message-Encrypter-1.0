/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package massageencrypter;

//import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;



/**
 *
 * @author Hassan
 */
public class DES {
    
    private Cipher  cipher = null;
    private SecretKey secretKey = null;
    private SecretKeyFactory  skf = null;
    private DESKeySpec  keySpec = null;
    
    
   public DES(){
   
   
   
   } 
    
    
   public String encrypt(String message, String commonKey) throws Exception{
       
       String  ev = "";
       SecretKey  key = getSecretKey(commonKey);
       cipher.init(Cipher.ENCRYPT_MODE, key);
       
       byte[] inputBytes = message.getBytes();
       byte[] outputBytes = cipher.doFinal(inputBytes);
       
       ev = new HexBinaryAdapter().marshal(outputBytes);
       
      
   return ev;
   }
   public String decrypt(String encryptedMessage, String commonKey) throws Exception{
   
     String decryptedValue = "";  
       
     encryptedMessage  = encryptedMessage.replace(' ', '+');
     SecretKey key =  getSecretKey(commonKey);
     cipher.init(Cipher.DECRYPT_MODE, key);
     byte[] recorvedBytes = null;
     
     try{
     recorvedBytes = cipher.doFinal(new HexBinaryAdapter().unmarshal(encryptedMessage));
     
     
     }catch(Exception exp){
     exp.printStackTrace();
     return null;
     }
     
     decryptedValue = new String(recorvedBytes);
     
       
   return decryptedValue;
   }
   
   
   private SecretKey getSecretKey(String secretPass){
   
   SecretKey key = null;
   try{
   
   cipher = Cipher.getInstance("DES");
   keySpec = new DESKeySpec(secretPass.getBytes("UTF8"));
   skf = SecretKeyFactory.getInstance("DES");
   key = skf.generateSecret(keySpec);
  
   }catch(Exception exp){
 //  exp.printStackTrace();
   
   
   }
       
       
       
   return key;
   }
   
   
   
    
}
