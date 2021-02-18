/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraft.controller;

import com.paycraft.dto.CryptoObj;
import com.paycraft.resources.AESCrypter;
import com.paycraftsystems.error.messages.ErrorCodes;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author taysayshaguy
 */

@ApplicationScoped
//@Path("cryptoguy")
public class CryptoHelper {
    
    
    //@GET
    //@Path("aboutme")
    public Response aboutME() {
        
        
        return Response.accepted().entity(Json.createObjectBuilder().add("about-me","I am just an ordinary guy helping you to encrypt and decrypt stuff based on the AES Standard as documented in the API Yes I am glad to be of help....(the parameters I expect are key,iv, val in a json object and i respond with the following responseInfo and errorDesc alongside a custom http error code 902 for just any error, when I am simply not fine)").build()).build();
    }
    
    
    //@GET
    //@Path("decrypt")
    public Response doDecrypt(final String jsonStr) {
        
         try 
         {
             CryptoObj fromJson = JsonbBuilder.create().fromJson(jsonStr, CryptoObj.class);
             if(fromJson.getToPlain()== null || fromJson.getToPlain().trim().equals(""))
             {
                 
                  return Response.status(ErrorCodes.FORMAT_ERROR).entity(Json.createObjectBuilder().add("responseInfo", "the expected parameters are key, iv, toJibrish ").add("responseDesc", "passed  "+fromJson.getToJibrish()+" to decrypt ").build().toString()).build();//.type(MediaType.APPLICATION_JSON)
                 
             }
             
             if(fromJson.getIv()== null || fromJson.getIv().trim().equals("") || fromJson.getIv().length() != 16)
             {
                 
                  return Response.status(ErrorCodes.FORMAT_ERROR).entity(Json.createObjectBuilder().add("responseInfo", "the expected parameters are key, iv, toJibrish ").add("responseDesc", "passed  "+fromJson.getToJibrish()+" to decrypt ").build().toString()).build();//.type(MediaType.APPLICATION_JSON)
                 
             }
             
             if(fromJson.getKey()== null || fromJson.getKey().trim().equals("") || fromJson.getKey().length() != 16)
             {
                 
                  return Response.status(ErrorCodes.FORMAT_ERROR).entity(Json.createObjectBuilder().add("responseInfo", "the expected parameters are key, iv, toJibrish ").add("responseDesc", "passed  "+fromJson.getToJibrish()+" to decrypt ").build().toString()).build();//.type(MediaType.APPLICATION_JSON)
                 
             }
             String decrypt = new AESCrypter(fromJson.getKey(),fromJson.getIv()).decrypt(fromJson.getToPlain());
            
             return Response.accepted(Json.createObjectBuilder().add("toPlain", decrypt).build()).build();
             
        
         
         }catch (JsonbException e) {
         
             
             return Response.status(ErrorCodes.INVALID_JSON_FORMAT).entity(Json.createObjectBuilder().add("responseInfo", "the expected parameters are key, iv, toJibrish ").add("responseDesc",e.getMessage()).build()).type(MediaType.APPLICATION_JSON).build();
         
         } catch (Exception e) {
         
             
             return Response.status(ErrorCodes.DECRYPTION_ERROR).entity(Json.createObjectBuilder().add("responseInfo", "the expected parameters are key, iv, toJibrish ").add("responseDesc",e.getMessage()).build()).type(MediaType.APPLICATION_JSON).build();
         
         }
       
    }
    
    
  
    public Response doEncrypt(final String jsonStr) {
        //System.out.println("doEncrypt jsonStr = " +  jsonStr);
        try 
         {
             CryptoObj fromJson = JsonbBuilder.create().fromJson(jsonStr, CryptoObj.class);
             
             if(fromJson.getToJibrish() == null || fromJson.getToJibrish().trim().equals(""))
             {
                 
                  return Response.status(ErrorCodes.FORMAT_ERROR).entity(Json.createObjectBuilder().add("responseInfo", "the expected parameters are key, iv, toJibrish ").add("responseDesc", "passed  "+fromJson.getToJibrish()+" to decrypt ").build().toString()).build();//.type(MediaType.APPLICATION_JSON)
                 
             }
             
             if(fromJson.getIv()== null || fromJson.getIv().trim().equals("") || fromJson.getIv().length() != 16)
             {
                 
                  return Response.status(ErrorCodes.FORMAT_ERROR).entity(Json.createObjectBuilder().add("responseInfo", "the expected parameters are key, iv, toJibrish ").add("responseDesc", "passed  "+fromJson.getToJibrish()+" to decrypt ").build().toString()).build();//.type(MediaType.APPLICATION_JSON)
                 
             }
             
              if(fromJson.getKey()== null || fromJson.getKey().trim().equals("") || fromJson.getKey().length() != 16)
             {
                 
                  return Response.status(ErrorCodes.FORMAT_ERROR).entity(Json.createObjectBuilder().add("responseInfo", "the expected parameters are key, iv, toJibrish ").add("responseDesc", "passed  "+fromJson.getToJibrish()+" to decrypt ").build().toString()).build();//.type(MediaType.APPLICATION_JSON)
                 
             }
             
             String decrypt = new AESCrypter(fromJson.getKey(),fromJson.getIv()).encrypt(fromJson.getToJibrish());
            
             return Response.accepted(Json.createObjectBuilder().add("toJibrish", decrypt).build()).build();
             
         
         
         }
         catch (JsonbException e) {
         
             
             return Response.status(ErrorCodes.INVALID_JSON_FORMAT).entity(Json.createObjectBuilder().add("responseInfo", "the expected parameters are key, iv, toJibrish ").add("responseDesc",e.getMessage()).build()).type(MediaType.APPLICATION_JSON).build();
         
         } 
         catch (Exception e) {
         
             e.printStackTrace();
             return Response.status(ErrorCodes.DECRYPTION_ERROR).entity(Json.createObjectBuilder().add("responseInfo", "the expected parameters are key, iv, toPlain ").add("responseDesc",e.getMessage()).build()).type(MediaType.APPLICATION_JSON).build();
         
         }
        
    }
    
    
}
