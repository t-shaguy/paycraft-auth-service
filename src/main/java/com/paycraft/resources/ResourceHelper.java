/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraft.resources;

import com.paycraft.dto.ProfileSyncOBJ;
import com.paycraft.entities.ProfileSync;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author root
 */
public class ResourceHelper {
    
    
    public static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    
    
    public boolean isValidEmail(String email) {
      
        boolean valid = false;
        try 
        {
        
            Pattern pattern = Pattern.compile(EMAIL_REGEX);

            Matcher matcher = pattern.matcher(email);
            System.out.println(email +" : "+ matcher.matches());
            
            valid =  matcher.matches();

        } 
        catch (Exception e) {
        
        
            e.printStackTrace();
        
        }
   
     return valid;
    }
    
    
   public static String toDefault(String value) {
        
        return (value == null || value.isEmpty())?"NA":value.trim();
   }
    
   public String toDefault(String value, String defaultValue) {
        
        return (value == null || value.isEmpty())?defaultValue:value.trim();
   }
     
   public String toDefault(Date value) {
        
        return (value == null)?"0000-NON-00":new SimpleDateFormat("yyyy-MMM-dd HH:mm a").format(value);
    }
     
     
    public String doVhash1(String key, String iv, ProfileSyncOBJ obj) throws SecException {
        String tohash = obj.getCode()+""+obj.getCodeLink();
        return  doSHA512(new AESCrypter(key, iv).encrypt(tohash));
    }
    
    public boolean isNullorEmpty(String value) {
        
     return (value == null || value.trim().equals(""));
    }
    
    
    public String doPassword(String key, String iv, ProfileSyncOBJ obj) throws SecException {
        String tohash = obj.getCode()+obj.getCodeLink()+obj.getNewPassword();
        return  new AESCrypter(key, iv).encrypt(tohash);
    }
    
    public String doVerifyPassword(String key, String iv, ProfileSyncOBJ obj) throws SecException {
        String tohash = obj.getCode()+obj.getCodeLink()+obj.getPassword();
        return  new AESCrypter(key, iv).encrypt(tohash);
    }
    
    public String doVerifyPIN(String key, String iv, ProfileSyncOBJ obj) throws SecException {
        String tohash = obj.getCode()+obj.getPin();
        return  new AESCrypter(key, iv).encrypt(tohash);
    }
    
    public String doForcePIN(String key, String iv, ProfileSyncOBJ obj) throws SecException {
        String tohash = obj.getCode()+obj.getVerifyPin();
        return  new AESCrypter(key, iv).encrypt(tohash);
    }
    
    /*
    public String doPassword(String key, String iv, String plainPass) throws SecException {
     
     return  new AESCrypter(key, iv).encrypt(plainPass);
    }*/
    
    
     public  String doSHA512(String shaRequest)
	    {
		   String resp = "";
                   //  amount, terminal id , hash

	       try
	       {

	           MessageDigest md = MessageDigest.getInstance("SHA-512");
	           md.update(shaRequest.getBytes());

	           byte byteData[] = md.digest();

	           //convert the byte to hex format method 1
	           StringBuffer sb = new StringBuffer();
	           for (int i = 0; i < byteData.length; i++) {
	            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	           }

	           resp = sb.toString();
	          // System.out.println("Hex format : " + sb.toString());

	       }
	       catch(Exception e)
	       {
	    	   System.out.println("WARNING : [ERROR] : In doSHA512 :");
	    	   e.printStackTrace();

	       }

        return resp.toUpperCase();
     }
    
     
     public static String doCategoryDescription(int catId) {
       String desc = "";
       
         try 
         {
             switch(catId)
             {
                 case 1:
                 desc = "CATEGORY 1";
                 break;
                 case 2:
                 desc = "CATEGORY 2";
                 break;
                 case 3:
                 desc = "CATEGORY 3";
                 break;
                 case 4:
                 desc = "CATEGORY 4";
                 break;
                 default:
                 desc = "CATEGORY "+catId;
                 break;      
            }
                     
         } 
         catch (Exception e) {
         }
       return desc;
    }
     
     public String doStatusDesc(int tid) {
        String desc = "";
        try 
        {
             switch(tid)
             {
                 case 0:
                 desc = "IN-ACTIVE";
                 break;
                 case 1:
                 desc = "ACTIVE";
                 break;
                 case 3:
                 desc = "PENDING RESET";
                 break;
                 case 10:
                 desc = "DELETED";
                 break;
                 default:
                  desc = "UNKNOWN -> "+tid;
                 break;
                 
             }
            
            
        } 
        catch (Exception e) {
       
        }
     return desc;
    }
    
    
    
}
