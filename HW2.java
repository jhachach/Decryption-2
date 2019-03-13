import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.BadPaddingException;
import java.security.MessageDigest;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class HW2 {
  static void P1() throws Exception {
    byte[] cipherText = Files.readAllBytes(Paths.get("cipher1.txt"));
    String algoritmn1 = "";
    // BEGIN SOLUTION
    byte[] iv = new byte[] { 0, 0, 0, 0, 
                             0, 0, 0, 0, 
                             0, 0, 0, 0, 
                             0, 0, 0, 0 };
    
    byte[] key = new byte[] { 0, 0, 0, 0, 
                             0, 0, 0, 0, 
                             0, 0, 0, 0, 
                             0, 0, 0, 0 };
    SecretKeySpec secret = new SecretKeySpec(key,"AES");
    IvParameterSpec ivSpec = new IvParameterSpec(iv);
    Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding");
    cipher.init(Cipher.DECRYPT_MODE, secret,ivSpec);
    byte[] plainText = cipherText;
    
    plainText = cipher.doFinal(plainText);
    // END SOLUTION
    
    System.out.println(new String(plainText, StandardCharsets.UTF_8));
  }

  static void P2() throws Exception {    
    // BEGIN SOLUTION
    
    for (int i = 0; i < 999; i++) {
        byte[] message = Files.readAllBytes(Paths.get(String.format("messages/plain2%d.txt", i)));
        MessageDigest hash = MessageDigest.getInstance("SHA-256");
        hash.update(message);
        byte[] value = hash.digest();
        if(value[0]== 3 && value[1]==59)
             System.out.println("FIle: plain2" + i);  
        }
    }
    
   
    // END SOLUTION
  

  static void P3() throws Exception {
    byte[] cipherText = Files.readAllBytes(Paths.get("cipher3.txt"));
    
    // BEGIN SOLUTION
    byte[] iv = new byte[] { 0, 0, 0, 0, 
                             0, 0, 0, 0, 
                             0, 0, 0, 0, 
                             0, 0, 0, 0 };
    
    byte[] key = new byte[] { 0, 0, 0, 0, 
                             0, 0, 0, 0, 
                             0, 0, 0, 0, 
                             0, 0, 0, 0 };
    byte[] block1 = new byte[16];
    byte[] block2 = new byte[16];
    byte[] block3 = new byte[16];
    

    for(int i = 0; i < 48; i++)
    {
        if(i < 16)
            block1[i] = cipherText[i];
        else if(i < 32)
            block2[i-16] = cipherText[i];
        else
            block3[i-32] = cipherText[i];
    }
    for(int i = 0; i < 48; i++)
    {
        if(i < 16)
         cipherText[i] =   block2[i];
        else if(i < 32)
            cipherText[i] =   block1[i-16];
        else
            cipherText[i] =   block3[i-32];
    }
    
    SecretKeySpec secret = new SecretKeySpec(key,"AES");
    IvParameterSpec ivSpec = new IvParameterSpec(iv);
    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
    cipher.init(Cipher.DECRYPT_MODE, secret,ivSpec);
    
   
    byte[] plainText = cipherText;    
    plainText = cipher.doFinal(plainText);
    // END SOLUTION
    
    System.out.println(new String(plainText, StandardCharsets.UTF_8));
  }

  static void P4() throws Exception {
   
    byte[] key = new byte[] { 2, 0, 0, 0, 
                             0, 0, 0, 0, 
                             0, 0, 0, 0, 
                             0, 0, 0, 0 };
    byte[] cipherText = Files.readAllBytes(Paths.get("cipher4.txt"));
    
    // BEGIN SOLUTION
    for(byte i = 1; i <= 30; i++)
    {
        for(byte j = 1; j <= 30; j++)
        {
           try
           {
           boolean correct = true;
           key[0] = i;
           key[1] = j;
            SecretKeySpec secret = new SecretKeySpec(key,"AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/ISO10126Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            byte[] plainText = cipherText; 
            plainText = cipher.doFinal(plainText);
            for(int k = 0; k < plainText.length; k++)
            {
                if(plainText[k] <0 || plainText[k] > 127)
                    correct = false;
            }
            if(correct)
                System.out.println(new String(plainText, StandardCharsets.UTF_8));
            }
           catch(BadPaddingException bpe){
               
           }
        }
    }
}
  
                
    // END SOLUTION
    
    

  public static void main(String [] args) {
    try {  
      P1();
      P2();
      P3();
      P4();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}

