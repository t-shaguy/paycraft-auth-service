/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraft.controller;

import com.paycraft.dto.ClientsInfoObj;
import com.paycraft.dto.LoginObj;
import com.paycraft.entities.ClientsInfo;
import com.paycraft.resources.RandomCharacter;
import com.paycraft.resources.ResourceHelper;
import com.paycraftsystems.error.messages.ErrorCodes;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.bind.JsonbBuilder;
import javax.validation.Valid;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.slf4j.LoggerFactory;

/**
 *
 * @author taysayshaguy
 */

@ApplicationScoped
public class AuthController {
    
    private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    
    
    @Context
    private UriInfo uriInfo;
    
    //@Inject
    //MicroConnector msconnector;
    
    @Inject
    TokenHelper tokenHelper;
    
    
    /*
    @Inject
    @Dedicated
    ExecutorService instantNotifications;
    */
    
    //@Inject
    //SysActivityHelper  sysActivityHelper;
    
    @Inject
    ClientInfoHelper clientInfoHelper;
    
    @Inject
    SysDataHelper sysPropsController;
    
   
    
    ResourceHelper rh = new ResourceHelper();
    
    @Inject
    Logger logger;
    
    public Response doResend(final String jsonRequest) //SysDataHelper sysPropsController,
    {
        
        JsonObject jsonObject = null;
        String ivee = "";
        String key = "";
        String responseDesc = "";
        String activityInfo = "";
        String activityby = "";
        LoginObj doLoginObj = null;
        try 
        {
            
            
            doLoginObj = JsonbBuilder.create().fromJson(jsonRequest.toString(), LoginObj.class);
            
            LOGGER.info("# doResend parseRequest = " + doLoginObj);
            
            activityby = doLoginObj.ux;
            
            ClientsInfo byUniqueID = clientInfoHelper.getByName(doLoginObj.ux);
            
             if(byUniqueID !=null)
             { 
                   if(byUniqueID.getIv() != null && byUniqueID.getCKey() !=null)
                   {
                        ivee = byUniqueID.getIv();
                        key = byUniqueID.getCKey();

                        jsonObject = Json.createObjectBuilder().add("responseDesc", "Reset Successful, Please use new keys going forward ").add("iv", ivee).add("key", key).build();

                        return Response.accepted().entity(jsonObject).type(MediaType.APPLICATION_JSON).header("iv", key).header("key", ivee).build();

                   }
                   else
                   {
                       
                       LOGGER.info("?????  had to reset due to null keys = ");
                      return  doForceReset (jsonRequest);
                   }
             }
             else
             {
                 jsonObject = Json.createObjectBuilder().add("responseDesc", responseDesc).add("iv", ivee).add("key", key).build();
             
                 return Response.status(ErrorCodes.INVALID_CLIENT).entity(jsonObject).build();
               
                 
             }
             
        }
        catch (WebApplicationException e) 
        {
            
            jsonObject = Json.createObjectBuilder().add("responseDesc", "System errror "+e.getMessage()).add("px", "").add("rx", "").build();
           // e.printStackTrace();
            
            LOGGER.error("WebApplicationException & doResend ", e);
            
            return Response.status(e.getResponse().getStatus()).entity(jsonObject).build();
                    
        }
        catch (Exception e) 
        {
             e.printStackTrace();
             
             LOGGER.error("Exception & doResend ", e);
            
            jsonObject = Json.createObjectBuilder().add("responseDesc", "System errror "+e.getMessage()).add("iv", "").add("key", "").build();
           
            return Response.status(ErrorCodes.SYSTEM_ERROR).entity(jsonObject).build();
                    
        }
        
    }
    
    public Response doRefresh(final String jsonRequest) //SysDataHelper sysPropsController,
    {
        
        JsonObject jsonObject = null;
        String ivee = "";
        String key = "";
        String responseDesc = "";
        String activityInfo = "";
        String activityby = "";
        LoginObj doLoginObj = null;
        try 
        {
          
            doLoginObj = JsonbBuilder.create().fromJson(jsonRequest.toString(), LoginObj.class);
            
            LOGGER.info("doResend parseRequest = " + doLoginObj);
            
            activityby = doLoginObj.ux;
            
            ClientsInfo byUniqueID = clientInfoHelper.getByName(doLoginObj.ux);
            
             if(byUniqueID !=null)
             { 
                   if(byUniqueID.getIv() != null && byUniqueID.getCKey() !=null)
                   {
                        ivee = byUniqueID.getIv();
                        key = byUniqueID.getCKey();

                        jsonObject = Json.createObjectBuilder().add("responseDesc", "Reset Successful, Please use new keys going forward ").add("iv", ivee).add("key", key).build();

                        String token = tokenHelper.issueToken(byUniqueID, uriInfo);
            
            
           
                        LOGGER.info("token = " + token);

                        if(token !=null)
                        {
                            return Response.accepted(Json.createObjectBuilder().add(AUTHORIZATION, "Bearer " + token).add("iv", key).add("key", ivee).build().toString()).header(AUTHORIZATION, "Bearer " + token).header("iv", key).header("key", ivee).build();


                        }
                        else
                        {
                            return Response.status(ErrorCodes.SYSTEM_ERROR).build();
                        }
                       
                   }
                   else
                   {
                       
                       LOGGER.info("?????  had to reset due to null keys = ");
                      return  doForceRefresh(jsonRequest);
                      
                   }
             }
             else
             {
                 jsonObject = Json.createObjectBuilder().add("responseDesc", responseDesc).add("iv", ivee).add("key", key).build();
             
                 return Response.status(ErrorCodes.SYSTEM_ERROR).entity(jsonObject).build();
                 
             }
             
        }/*
        catch (ServiceException e) 
        {
            
            jsonObject = Json.createObjectBuilder().add("responseDesc", "System errror "+e.getMessage()).add("px", "").add("rx", "").build();
            e.printStackTrace();
            
            return Response.status(e.getCode()).entity(jsonObject).build();
                    
        }*/
        catch (Exception e) 
        {
             e.printStackTrace();
            
            jsonObject = Json.createObjectBuilder().add("responseDesc", "System errror "+e.getMessage()).add("iv", "").add("key", "").build();
           
            return Response.status(ErrorCodes.SYSTEM_ERROR).entity(jsonObject).build();
                    
        }
        
    }
    
    
   public Response doReset(@Valid final JsonObject jsonRequest) {
        System.out.println("doReset = " + jsonRequest);
        JsonObject jsonObject = null;
        String ivee = "";
        String key = "";
        String responseDesc = "";
        String activityInfo = "";
        String activityby = "";
        LoginObj doLoginObj = null;
        try 
        {
            doLoginObj = JsonbBuilder.create().fromJson(jsonRequest.toString(), LoginObj.class);
            LOGGER.info("@@@@*** parseRequest doReset = " + doLoginObj);
            
            activityby = doLoginObj.ux;
            
            ClientsInfo byUniqueID = clientInfoHelper.getByName(doLoginObj.ux);
            LOGGER.info("@@@@*** parseRequest byUniqueID = " + byUniqueID);
             if(byUniqueID !=null)
             { 
                   ivee = RandomCharacter.doRandomPass(16);
                   key = RandomCharacter.doRandomPass(16);
                   byUniqueID.setIv(ivee);
                   byUniqueID.setCKey(key);
                   byUniqueID.setStatus(BigInteger.ONE);
                   byUniqueID.setStatusStr(rh.doStatusDesc(byUniqueID.getStatus().intValue()));
                   byUniqueID.setClientCategory(doLoginObj.clientCategory);
                   ClientsInfo doSync = clientInfoHelper.doSync(byUniqueID);
                   System.out.println("doReset doSync = " +  doSync);
                   if(doSync == null)
                   {
                     
                       activityInfo = "Failed Keys reset by ";
                       
                       return Response.status(ErrorCodes.DATABASE_ERROR).build();
                       
                   }
                   else
                   {
                       // 190 800 2488  0702 065 7345
                       // gt collections / subscription // smile
                       jsonObject = Json.createObjectBuilder().add("responseDesc", "Reset Successful, Please use new keys going forward (pass iv as the IV and key as the Key in AES encryption ) ").add("iv", ivee).add("key", key).add("maxage",byUniqueID.getTokenLifespanDays()).build();
           
                       activityInfo = "Successful Keys reset by "+doSync.getCode();
                       /*
                       NotificationLog  notifLog  = new NotificationLog();
                       notifLog.setBankEmail(byUniqueID.getEmailAddress());
                       notifLog.setMessage("Reset Successful, Please use new keys going forward");
                       notifLog.setOtherInfo(ivee);
                       notifLog.setOtherInfo2(key);
                       
                       String info = "Reset Successful, please login with the following credentials px : "+ivee+" rx : "+key;
                       
                       JsonObject build = Json.createObjectBuilder().add("subject", "PASSWORD RESET").add("px", ivee).add("rx", key).add("message", info).add("email", doSync.getEmailAddress()).build();
                     
                       msconnector.doEmailCredz(build);//doSync.getEmailAddress(), info);
                       
                       //instantNotifications.submit(new CallableMailerService(sysPropsController,  emailSender,  notificationFacade,  notifLog ,  "0"));
                       */
                       System.out.println("doSync = iv " + ivee+" key : "+key);
                      return Response.accepted().entity(jsonObject).type(MediaType.APPLICATION_JSON).header("iv", key).header("key", ivee).header("maxage", doSync.getTokenLifespanDays()).build();
                   }
             }
             else
             {
                 jsonObject = Json.createObjectBuilder().add("responseDesc", responseDesc).add("iv", ivee).add("key", key).build();
             
                 return Response.status(ErrorCodes.INVALID_CLIENT).entity(jsonObject).build();
                 
             }
             
        }/*
        catch (ServiceException e) 
        {
            
            jsonObject = Json.createObjectBuilder().add("responseDesc", "System errror "+e.getMessage()).add("px", "").add("rx", "").build();
            e.printStackTrace();
            
            return Response.status(e.getCode()).entity(jsonObject).build();
                    
        }*/
        catch (Exception e) 
        {
            LOGGER.error(" Exceptiom in doReset ",e);
            
            jsonObject = Json.createObjectBuilder().add("responseDesc", "System errror "+e.getMessage()).add("iv", "").add("key", "").build();
           
            return Response.status(ErrorCodes.SYSTEM_ERROR).entity(jsonObject).build();
                    
        }
        
    }
   
   public Response doResetOwnKeys(@Valid final JsonObject jsonRequest) {
        
        JsonObject jsonObject = null;
        String ivee = "";
        String key = "";
        String responseDesc = "";
        String activityInfo = "";
        //String activityby = "";
        LoginObj doLoginObj = null;
        try 
        {
            doLoginObj = JsonbBuilder.create().fromJson(jsonRequest.toString(), LoginObj.class);
            LOGGER.info("parseRequest = " + doLoginObj);
            
            if(doLoginObj !=null && (doLoginObj.iv == null || doLoginObj.iv.trim().length() !=16))
            {
                  return Response.status(ErrorCodes.INVALID_PASSWORD).build();
            }
            
            if(doLoginObj !=null && (doLoginObj.key == null || doLoginObj.key.trim().length() !=16))
            {
                  return Response.status(ErrorCodes.INVALID_PASSWORD).build();
            }
            
           // activityby = doLoginObj.getUx();
            
            ClientsInfo byUniqueID = clientInfoHelper.getByName(doLoginObj.ux);
            
             if(byUniqueID !=null)
             { 
                   //ivee = RandomCharacter.doRandomPass(16);
                   //key = RandomCharacter.doRandomPass(16);
                   byUniqueID.setIv(doLoginObj.iv);
                   byUniqueID.setCKey(doLoginObj.key);
                   ClientsInfo doSync = clientInfoHelper.doSync(byUniqueID);
                  
                   if(doSync == null)
                   {
                     
                       activityInfo = "Failed Keys reset by ";
                       
                       return Response.status(ErrorCodes.DATABASE_ERROR).build();
                       
                   }
                   else
                   {
                       // 190 800 2488  0702 065 7345
                       // gt collections / subscription // smile
                       jsonObject = Json.createObjectBuilder().add("responseDesc", "Reset Successful, Please use new keys going forward (pass iv as the IV and key as the Key in AES encryption ) ").add("iv", ivee).add("key", key).build();
           
                       activityInfo = "Successful Keys reset by "+doSync.getCode();
                      
                       
                      return Response.accepted().entity(jsonObject).type(MediaType.APPLICATION_JSON).header("iv", key).header("key", ivee).build();
                   }
             }
             else
             {
                 jsonObject = Json.createObjectBuilder().add("responseDesc", responseDesc).add("iv", ivee).add("key", key).build();
             
                 return Response.status(ErrorCodes.INVALID_CLIENT).entity(jsonObject).build();
                 
             }
             
        }/*
        catch (ServiceException e) 
        {
            
            jsonObject = Json.createObjectBuilder().add("responseDesc", "System errror "+e.getMessage()).add("px", "").add("rx", "").build();
            e.printStackTrace();
            
            return Response.status(e.getCode()).entity(jsonObject).build();
                    
        }*/
        catch (Exception e) 
        {
            LOGGER.error(" Exception doResetOwnKeys ",e); 
            
            
            jsonObject = Json.createObjectBuilder().add("responseDesc", "System errror "+e.getMessage()).add("iv", "").add("key", "").build();
           
            return Response.status(ErrorCodes.SYSTEM_ERROR).entity(jsonObject).build();
                    
        }
        
    }
    
    
    
    public Response doForceReset(final String jsonRequest) {
        
        JsonObject jsonObject = null;
        String ivee = "";
        String key = "";
        String responseDesc = "";
        String activityInfo = "";
        String activityby = "";
        LoginObj doLoginObj = null;
        try 
        {
          
            doLoginObj = JsonbBuilder.create().fromJson(jsonRequest, LoginObj.class);
            LOGGER.info("doForceReset parseRequest = " + doLoginObj);
            
            activityby = doLoginObj.ux;
            
            ClientsInfo byUniqueID = clientInfoHelper.getByName(doLoginObj.ux);
            
             if(byUniqueID !=null)
             { 
                   ivee = RandomCharacter.doRandomPass(16);
                   key = RandomCharacter.doRandomPass(16);
                   byUniqueID.setIv(ivee);
                   byUniqueID.setCKey(key);
                   ClientsInfo doSync = clientInfoHelper.doSync(byUniqueID);
                  
                   if(doSync == null)
                   {
                     
                       activityInfo = "Failed Keys reset by ";
                       
                       return Response.status(ErrorCodes.DATABASE_ERROR).build();
                       
                   }
                   else
                   {
                       // 190 800 2488  0702 065 7345
                       // gt collections / subscription // smile
                       jsonObject = Json.createObjectBuilder().add("responseDesc", "Reset Successful, Please use new keys going forward ").add("iv", ivee).add("key", key).build();
           
                       activityInfo = "Successful Keys reset by "+doSync.getCode();
                       /*
                       NotificationLog  notifLog  = new NotificationLog();
                       notifLog.setBankEmail(byUniqueID.getEmailAddress());
                       notifLog.setMessage("Reset Successful, Please use new keys going forward");
                       notifLog.setOtherInfo(ivee);
                       notifLog.setOtherInfo2(key);
                       
                       String info = "Reset Successful, please login with the following credentials px : "+ivee+" rx : "+key;
                       
                       JsonObject build = Json.createObjectBuilder().add("subject", "PASSWORD RESET").add("px", ivee).add("rx", key).add("message", info).add("email", doSync.getEmailAddress()).build();
                     
                       msconnector.doEmailCredz(build);//doSync.getEmailAddress(), info);
                       
                       //instantNotifications.submit(new CallableMailerService(sysPropsController,  emailSender,  notificationFacade,  notifLog ,  "0"));
                       */
                       
                      return Response.accepted().entity(jsonObject).type(MediaType.APPLICATION_JSON).header("iv", key).header("key", ivee).build();
                   }
             }
             else
             {
                 jsonObject = Json.createObjectBuilder().add("responseDesc", responseDesc).add("iv", ivee).add("key", key).build();
             
                 return Response.status(ErrorCodes.SYSTEM_ERROR).entity(jsonObject).build();
                 
             }
             
        }/*
        catch (ServiceException e) 
        {
            
            jsonObject = Json.createObjectBuilder().add("responseDesc", "System errror "+e.getMessage()).add("px", "").add("rx", "").build();
            e.printStackTrace();
            
            return Response.status(e.getCode()).entity(jsonObject).build();
                    
        }*/
        catch (Exception e) 
        {
             LOGGER.error(" Exception doForceReset ",e); 
            
            jsonObject = Json.createObjectBuilder().add("responseDesc", "System errror "+e.getMessage()).add("iv", "").add("key", "").build();
           
            return Response.status(ErrorCodes.SYSTEM_ERROR).entity(jsonObject).build();
                    
        }
        
    }
    
    public Response doForceRefresh(final String jsonRequest) {
        
        JsonObject jsonObject = null;
        String ivee = "";
        String key = "";
        String responseDesc = "";
        String activityInfo = "";
        String activityby = "";
        LoginObj doLoginObj = null;
        try 
        {
          
            doLoginObj = JsonbBuilder.create().fromJson(jsonRequest, LoginObj.class);
            LOGGER.info("doForceReset parseRequest = " + doLoginObj);
            
            activityby = doLoginObj.ux;
            
            ClientsInfo byUniqueID = clientInfoHelper.getByName(doLoginObj.ux);
            
             if(byUniqueID !=null)
             { 
                   ivee = RandomCharacter.doRandomPass(16);
                   key = RandomCharacter.doRandomPass(16);
                   byUniqueID.setIv(ivee);
                   byUniqueID.setCKey(key);
                   ClientsInfo doSync = clientInfoHelper.doSync(byUniqueID);
                  
                   if(doSync == null)
                   {
                     
                       activityInfo = "Failed Keys reset by ";
                       
                       return Response.status(ErrorCodes.DATABASE_ERROR).build();
                       
                   }
                   else
                   {
                       // 190 800 2488  0702 065 7345
                       // gt collections / subscription // smile
                       jsonObject = Json.createObjectBuilder().add("responseDesc", "Reset Successful, Please use new keys going forward ").add("iv", ivee).add("key", key).build();
           
                       activityInfo = "Successful Keys reset by "+doSync.getCode();
                       
                       String token = tokenHelper.issueToken(doSync, uriInfo);
            
            
           
                        LOGGER.info("# token = " + token);

                        if(token !=null)
                        {
                            return Response.accepted(Json.createObjectBuilder().add(AUTHORIZATION, "Bearer " + token).add("iv", key).add("key", ivee).build().toString()).header(AUTHORIZATION, "Bearer " + token).header("iv", key).header("key", ivee).build();


                        }
                        else
                        {
                            return Response.status(ErrorCodes.SYSTEM_ERROR).build();
                        }
                      
                       
                      //return Response.accepted().entity(jsonObject).type(MediaType.APPLICATION_JSON).header("iv", key).header("key", ivee).build();
                   }
             }
             else
             {
                 jsonObject = Json.createObjectBuilder().add("responseDesc", responseDesc).build();
             
                 // jsonObject = Json.createObjectBuilder().add("responseDesc", responseDesc).add("iv", ivee).add("key", key).build();
                 return Response.status(ErrorCodes.SYSTEM_ERROR).entity(jsonObject).build();
                 
             }
             
        }
        catch (Exception e) 
        {
             LOGGER.error(" Exception doForceRefresh ",e); 
            
            jsonObject = Json.createObjectBuilder().add("responseDesc", "System errror "+e.getMessage()).add("iv", "").add("key", "").build();
           
            return Response.status(ErrorCodes.SYSTEM_ERROR).entity(jsonObject).build();
                    
        }
        
    }
    
    
    public Response doLogin(SysDataHelper sysPropsController,final JsonObject jsonRequest) {

        LOGGER.info(" on the service layer .... "+jsonRequest);
        LoginObj doLoginObj = null;
        try 
        {

            doLoginObj = JsonbBuilder.create().fromJson(jsonRequest.toString(), LoginObj.class);
            
            ClientsInfo byCredentials = clientInfoHelper.getByCredentials(doLoginObj.ux, doLoginObj.iv, doLoginObj.key);
          
            LOGGER.info("byCredentials = " + byCredentials);
           
            if(byCredentials == null)
            {
             
                return Response.status(ErrorCodes.INVALID_CLIENT).build();
            }

            // Issue a token for the user
            String token = tokenHelper.issueToken(byCredentials, uriInfo,sysPropsController);
           
            LOGGER.info("token = " + token);

            // Return the token on the response
            //return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
            
            if(token !=null)
            {
                return Response.accepted(Json.createObjectBuilder().add(AUTHORIZATION, "Bearer " + token).build().toString()).header(AUTHORIZATION, "Bearer " + token).build();
            
            
            }
            else
            {
                return Response.status(ErrorCodes.SYSTEM_ERROR).build();
            }
        }/*
        catch (JsonGenerationException | JsonMappingException e) 
        {
            //e.printStackTrace();
            return Response.status(ErrorCodes.FORMAT_ERROR).header("responseDesc", e.getMessage()).build();
        }
        catch (IOException e) 
        {
            //e.printStackTrace();
            return Response.status(ErrorCodes.INVALID_JSON_FORMAT).header("responseDesc", e.getMessage()).build();
        }*/
        catch (Exception e) 
        {
            LOGGER.info(" --doLogin - ",e);
            //e.printStackTrace();
            return Response.status(ErrorCodes.INVALID_CLIENT).header("responseDesc", e.getMessage()).build();
        }
    }
    
    
    public Response doLogin(final String jsonRequest) {

        LOGGER.info("@@@  #### on the service layer @@@@.... "+jsonRequest);
        LoginObj doLoginObj = null;
        try 
        {

            doLoginObj = JsonbBuilder.create().fromJson(jsonRequest, LoginObj.class);
            
            System.out.println("@@--> doLoginObj = " +  doLoginObj);
            
            ClientsInfo byCredentials = clientInfoHelper.getByCredentials(doLoginObj.ux, doLoginObj.iv, doLoginObj.key);
            
            LOGGER.info(" @@ ### byCredentials = " + byCredentials);
           
            if(byCredentials == null)
            {
             
                return Response.status(ErrorCodes.INVALID_CLIENT).build();
            }

            String token = tokenHelper.issueToken(byCredentials,doLoginObj, uriInfo);
            
            LOGGER.info("@s-- doLogin token = " + token);
            
            if(token !=null)
            {
                return Response.accepted(Json.createObjectBuilder().add(AUTHORIZATION, "Bearer " + token).add("customerCode", rh.toDefault(doLoginObj.customerCode)).add("customerName", rh.toDefault(doLoginObj.apiUserCustomerName)).add("max-age", byCredentials.getTokenLifespanDays()).build().toString()).header(AUTHORIZATION, "Bearer " + token).build();
            
            
            }
            else
            {
                return Response.status(ErrorCodes.SYSTEM_ERROR).build();
            }

            // Return the token on the response
          

        }/*
        catch (JsonGenerationException | JsonMappingException e) 
        {
            //e.printStackTrace();
            return Response.status(ErrorCodes.FORMAT_ERROR).header("responseDesc", e.getMessage()).build();
        }
        catch (IOException e) 
        {
            //e.printStackTrace();
            return Response.status(ErrorCodes.INVALID_JSON_FORMAT).header("responseDesc", e.getMessage()).build();
        }*/
        catch (Exception e) 
        {
            LOGGER.info(" --doLogin Exception --  ",e);
            //e.printStackTrace();
            return Response.status(ErrorCodes.INVALID_CLIENT).entity(Json.createObjectBuilder().add("responseDesc", e.getMessage()).build()).build();
        }
    }
    
    
    
    public Response doUserLogin(final String jsonRequest) {

        LOGGER.info("  #### on the service layer doUserLogin.... "+jsonRequest);
        LoginObj doLoginObj = null;
        try 
        {

            doLoginObj = JsonbBuilder.create().fromJson(jsonRequest, LoginObj.class);
            
            ClientsInfo byCredentials = clientInfoHelper.getByCredentials(doLoginObj.ux, doLoginObj.iv, doLoginObj.key);
          
            LOGGER.info(" ### doUserLogin byCredentials = " + byCredentials);
           
            if(byCredentials == null)
            {
             
                return Response.status(ErrorCodes.INVALID_CLIENT).build();
            }

            String token = tokenHelper.issueToken(byCredentials, uriInfo);
           
            LOGGER.info("token = " + token);
            
            if(token !=null)
            {
                return Response.accepted(Json.createObjectBuilder().add(AUTHORIZATION, "Bearer " + token).build().toString()).header(AUTHORIZATION, "Bearer " + token).build();
            }
            else
            {
                return Response.status(ErrorCodes.SYSTEM_ERROR).build();
            }

            // Return the token on the response
          

        }/*
        catch (JsonGenerationException | JsonMappingException e) 
        {
            //e.printStackTrace();
            return Response.status(ErrorCodes.FORMAT_ERROR).header("responseDesc", e.getMessage()).build();
        }
        catch (IOException e) 
        {
            //e.printStackTrace();
            return Response.status(ErrorCodes.INVALID_JSON_FORMAT).header("responseDesc", e.getMessage()).build();
        }*/
        catch (Exception e) 
        {
            //e.printStackTrace();
            return Response.status(ErrorCodes.INVALID_CLIENT).entity(Json.createObjectBuilder().add("responseDesc", e.getMessage()).build()).build();
        }
    }
    
    
     public Response doLogClient(JsonObject jsonRequest) {

        LOGGER.info(" @@@@ #### on the service layer doLogClient.... "+jsonRequest);
        ClientsInfoObj doClientObj = null;
        try 
        {
            
         
            doClientObj = JsonbBuilder.create().fromJson(jsonRequest.toString(), ClientsInfoObj.class);
            
           
           // LOGGER.info(" ### byCredentials = " + doClientObj);
           
            if(doClientObj == null)
            {
             
                return Response.status(ErrorCodes.INVALID_JSON_FORMAT).build();
            }
            
            if(doClientObj.clientName == null || doClientObj.clientName.trim().equals("") || doClientObj.clientName.trim().length() > 40 || !new ResourceHelper().isValidEmail(doClientObj.clientName))
            {
                 return Response.status(ErrorCodes.INVALID_CLIENT).build();
            }
            
            if(doClientObj.partnerCode == null || doClientObj.partnerCode.trim().equals("") || doClientObj.partnerCode.trim().length() > 40)
            {
                 return Response.status(ErrorCodes.INVALID_PARTNER_CODE).build();
            }
            

            return  clientInfoHelper.doLog(doClientObj);
         
        }
        catch (Exception e) 
        {
            //e.printStackTrace();
            LOGGER.error(" Exception ERROR  @ 1 ", e);
            return Response.status(ErrorCodes.INVALID_CLIENT).entity(Json.createObjectBuilder().add("responseDesc", e.getMessage()).build()).build();
        }
    }
     
     public Response doSynClient(JsonObject jsonRequest) {

        LOGGER.info(" --- #### on the service layer doSynClient.... "+jsonRequest);
        ClientsInfoObj doClientObj = null;
        try 
        {
            
         
            doClientObj = JsonbBuilder.create().fromJson(jsonRequest.toString(), ClientsInfoObj.class);
            
           
            LOGGER.info(" -- ### doSynClient byCredentials = " + doClientObj);
           
            if(doClientObj == null)
            {
             
                return Response.status(ErrorCodes.INVALID_JSON_FORMAT).build();
            }

            return  clientInfoHelper.doSyncClient(doClientObj);
         
        }
        catch (Exception e) 
        {
            //e.printStackTrace();
            
            LOGGER.info("-- doSynClient -- ",e);
            return Response.status(ErrorCodes.INVALID_CLIENT).entity(Json.createObjectBuilder().add("responseDesc", e.getMessage()).build()).build();
        }
    }
     
     public Response doDeleteClient(JsonObject jsonRequest) {

        LOGGER.info("  #### on the service layer doDeleteClient.... "+jsonRequest);
        ClientsInfoObj doClientObj = null;
        try 
        {
            
         
            doClientObj = JsonbBuilder.create().fromJson(jsonRequest.toString(), ClientsInfoObj.class);
            
           
            LOGGER.info(" ### doDeleteClient byCredentials = " + doClientObj);
           
            if(doClientObj == null)
            {
             
                return Response.status(ErrorCodes.INVALID_JSON_FORMAT).build();
            }
          

            return  clientInfoHelper.doDeleteClient(doClientObj);
         
        }
        catch (Exception e) 
        {
             LOGGER.info("-- doDeleteClient -- ",e);
            //e.printStackTrace();
            return Response.status(ErrorCodes.INVALID_CLIENT).entity(Json.createObjectBuilder().add("responseDesc", e.getMessage()).build()).build();
        }
    }
     
     
    public Response doApproveClient(JsonObject jsonRequest) {

        LOGGER.info("  #### on the service layer doApproveClient.... "+jsonRequest);
        ClientsInfoObj doClientObj = null;
        try 
        {
            
         
            doClientObj = JsonbBuilder.create().fromJson(jsonRequest.toString(), ClientsInfoObj.class);
            
           
            LOGGER.info(" ### doApproveClient byCredentials = " + doClientObj);
           
            if(doClientObj == null)
            {
             
                return Response.status(ErrorCodes.INVALID_JSON_FORMAT).build();
            }
          

            return  clientInfoHelper.doApproveClient(doClientObj);
          
         
        }
        catch (Exception e) 
        {
             LOGGER.info("-- doDeleteClient -- ",e);
            //e.printStackTrace();
            return Response.status(ErrorCodes.INVALID_CLIENT).entity(Json.createObjectBuilder().add("responseDesc", e.getMessage()).build()).build();
        }
    }
    
    
    
    
      public Response doListClients() {

        try 
        {
            
            List<ClientsInfo> byCredentials = clientInfoHelper.doListAllClients();
          
           // LOGGER.info(" ### byCredentials = " + byCredentials);
           
            if(byCredentials != null && !byCredentials.isEmpty())
            {
                
                //byCredentials.stream().map(a->a.toJson()).collect(toList());
             
                return Response.ok().entity(byCredentials.stream().map(a->a.toJson()).collect(toList())).build();
            }
            else
            {
                return Response.status(ErrorCodes.SYSTEM_ERROR).build();
            }
 
        }
        catch (Exception e) 
        {
            //e.printStackTrace();
            return Response.status(ErrorCodes.INVALID_CLIENT).entity(Json.createObjectBuilder().add("responseDesc", e.getMessage()).build()).build();
        }
    }
      
      //String partnerCode
      
   public Response doListTrimedClients(String partnerCode) {

        JsonArrayBuilder jar = Json.createArrayBuilder();
        try 
        {
            
            
            List<ClientsInfo> byCredentials = clientInfoHelper.doListAllClients(partnerCode);
          
           // LOGGER.info(" ### byCredentials = " + byCredentials);
           
            if(byCredentials != null && !byCredentials.isEmpty())
            {
                //byCredentials.stream().map(a->a.toJson()).collect(toList());
                
                byCredentials.stream().map(a->a.toJsonTrim()).forEach(x->jar.add(x));
             
                return Response.ok().entity(jar.build()).build();
            }
            else
            {
                return Response.status(ErrorCodes.SYSTEM_ERROR).build();
            }
 
        }
        catch (Exception e) 
        {
            //e.printStackTrace();
            return Response.status(ErrorCodes.INVALID_CLIENT).entity(Json.createObjectBuilder().add("responseDesc", (e.getMessage()==null?"NA":e.getMessage())).build()).build();
        }
    }
   
   public Response doListTrimedClients() {

        JsonArrayBuilder jar = Json.createArrayBuilder();
        try 
        {
            
            
            List<ClientsInfo> byCredentials = clientInfoHelper.doListAllClients();
          
           // LOGGER.info(" ### byCredentials = " + byCredentials);
           
            if(byCredentials != null && !byCredentials.isEmpty())
            {
                //byCredentials.stream().map(a->a.toJson()).collect(toList());
                
                byCredentials.stream().map(a->a.toJsonTrim()).forEach(x->jar.add(x));
             
                return Response.ok().entity(jar.build()).build();
            }
            else
            {
                return Response.status(ErrorCodes.SYSTEM_ERROR).build();
            }
 
        }
        catch (Exception e) 
        {
            //e.printStackTrace();
            return Response.status(ErrorCodes.INVALID_CLIENT).entity(Json.createObjectBuilder().add("responseDesc", (e.getMessage()==null?"NA":e.getMessage())).build()).build();
        }
    }
      
   public Response doLookupClient(String clientName) {

        try 
        {
            
            
            ClientsInfo byCredentials = clientInfoHelper.getByName(clientName);
          
           // LOGGER.info(" ### byCredentials = " + byCredentials);
           
            if(byCredentials != null )
            {
               
                return Response.ok().entity(byCredentials.toJson()).build();
            }
            else
            {
                return Response.status(ErrorCodes.SYSTEM_ERROR).build();
            }
 
        }
        catch (Exception e) 
        {
            //e.printStackTrace();
            return Response.status(ErrorCodes.INVALID_CLIENT).entity(Json.createObjectBuilder().add("responseDesc", e.getMessage()).build()).build();
        }
    }
   
   public Response doLookupClient(long tid) {

        try 
        {
            
            
            ClientsInfo byCredentials = clientInfoHelper.doFind(tid);
          
           // LOGGER.info(" ### byCredentials = " + byCredentials);
           
            if(byCredentials != null )
            {
               
                return Response.ok().entity(byCredentials.toJsonTrim()).build();
            }
            else
            {
                return Response.status(ErrorCodes.INVALID_FUNCTION).build();
            }
 
        }
        catch (Exception e) 
        {
            //e.printStackTrace();
            return Response.status(ErrorCodes.INVALID_CLIENT).entity(Json.createObjectBuilder().add("responseDesc", e.getMessage()).build()).build();
        }
    }
   
   public Response doLookupClientByCustomerCode(String customerCode) {
       LOGGER.info("doLookupClientByCustomerCode -- "+customerCode);
        try 
        {
            
            
            JsonArray byCredentials = clientInfoHelper.doLoadByParnerCode(customerCode);
          
           // LOGGER.info(" ### byCredentials = " + byCredentials);
           
            if(byCredentials != null  && !byCredentials.isEmpty())
            {
               
                return Response.ok().entity(byCredentials).build();
            }
            if(byCredentials != null  && byCredentials.isEmpty())
            {
               
                return Response.ok().entity(Json.createArrayBuilder().build()).build();
            }
            else
            {
                return Response.status(ErrorCodes.SYSTEM_ERROR).build();
            }
 
        }
        catch (Exception e) 
        {
            e.printStackTrace();
            return Response.status(ErrorCodes.INVALID_CLIENT).entity(Json.createObjectBuilder().add("responseDesc", e.getMessage()).build()).build();
        }
    }
    
}
