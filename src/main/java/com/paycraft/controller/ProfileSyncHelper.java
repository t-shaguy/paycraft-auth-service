/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraft.controller;

import com.paycraft.dto.ProfileSyncOBJ;
import com.paycraft.entities.ProfileSync;
import com.paycraftsystems.error.messages.ErrorCodes;
import com.paycraft.resources.ResourceHelper;
import com.paycraft.resources.ServiceException;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.bind.JsonbBuilder;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.core.Context;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tshaguy
 */
@ApplicationScoped
public class ProfileSyncHelper {
    
    
    private static Logger LOGGER = LoggerFactory.getLogger(ProfileSyncHelper.class);
    
    
    
    
    @Inject
    EntityManager em;
    
    @Inject
    SysDataHelper sysDataHelper;
    
    @Context
    private UriInfo uriInfo;
    
    //@Inject
    //MicroConnector msconnector;
    
    @Inject
    TokenHelper tokenHelper;
    
    
    ResourceHelper rh = new ResourceHelper();
    
    
    public ProfileSync doFind(Long  tid)
    {
        return em.find(ProfileSync.class,tid);
    }
    
    
    public void doRemove(ProfileSync  objx)
    {
        em.remove(objx.getTid());
    }
   
    public ProfileSync doLoadByFullCode(String code, String codeLink) {
        LOGGER.info("doLoadByFullCode codeLink = " + codeLink+"  code -- "+code);
        ProfileSync obj =  null;
        try 
        {
               //System.out.println("ProfileSync = " + code);
               TypedQuery<ProfileSync> query = em.createNamedQuery(ProfileSync.BY_CODE, ProfileSync.class).setParameter("passed", code).setParameter("passed2", codeLink);
                //System.out.println(" ProfileSync query = " + query);
                List<ProfileSync> resultList = query.getResultList();
                if(resultList != null && resultList.size() > 0)
                {
                      obj = resultList.get(0);
                     return obj;
                }
                else
                {
                    return null;// Response.status(ErrorCodes.UNKNOWN_DOC).build();
                }
       
        
        } catch (Exception e) {
        
             e.printStackTrace();
             
             LOGGER.error(" -- doLoadByFullCode -- ",e);
             
              return null;
        }
      
    }
    
    
    public ProfileSync doVerify(ProfileSyncOBJ objx) {
        ProfileSync obj =  null;
        try 
        { 
               LOGGER.info("ProfileSyncOBJ = " + objx);
               //LOGGER.info(" plain pass  = " + rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx));
               TypedQuery<ProfileSync> query = em.createNamedQuery(ProfileSync.BY_CODE_AND_HASH, ProfileSync.class).setParameter("passed", objx.getCode()).setParameter("passed2", objx.getCodeLink()).setParameter("passed3", rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx));
                //System.out.println(" ProfileSync query = " + query);
                List<ProfileSync> resultList = query.getResultList();
                if(resultList != null && resultList.size() > 0)
                {
                      obj = resultList.get(0);
                      
                      doSyncLoginInfo(obj);
                      
                     return obj;
                }
                else
                {
                    return null;// Response.status(ErrorCodes.UNKNOWN_DOC).build();
                }
       
        
        } catch (Exception e) {
        
             e.printStackTrace();
             
              return null;
        }
      
    }
    
     public ProfileSync doVerifyPIN(ProfileSyncOBJ objx) {
        ProfileSync obj =  null;
        try 
        { 
               LOGGER.info("ProfileSyncOBJ doVerifyPIN = " + objx);
               //LOGGER.info(" plain pass  = " + rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx));
               TypedQuery<ProfileSync> query = em.createNamedQuery(ProfileSync.BY_CODE_AND_HASH2, ProfileSync.class).setParameter("passed", objx.getCode()).setParameter("passed2", objx.getCodeLink()).setParameter("passed3", rh.doVerifyPIN(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx));
                //System.out.println(" ProfileSync query = " + query);
                List<ProfileSync> resultList = query.getResultList();
                if(resultList != null && resultList.size() > 0)
                {
                      obj = resultList.get(0);
                      
                      doSyncLoginInfo(obj);
                     
                     return obj;
                }
                else
                {
                    return null;// Response.status(ErrorCodes.UNKNOWN_DOC).build();
                }
       
        
        } catch (Exception e) {
        
             e.printStackTrace();
             
              return null;
        }
      
    }
     
     
     
    public ProfileSync doVerifyTxPIN(ProfileSyncOBJ objx) {
        ProfileSync obj =  null;
        try 
        { 
               LOGGER.info("ProfileSyncOBJ doVerifyTxPIN = " + objx);
               //LOGGER.info(" plain pass  = " + rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx));
               TypedQuery<ProfileSync> query = em.createNamedQuery(ProfileSync.BY_CODE_AND_TXP_HASH2, ProfileSync.class).setParameter("passed", objx.getCode()).setParameter("passed2", rh.doVerifyPIN(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx));
                //System.out.println(" ProfileSync query = " + query);
                List<ProfileSync> resultList = query.getResultList();
                if(resultList != null && resultList.size() > 0)
                {
                      obj = resultList.get(0);
                      
                      doSyncLoginInfo(obj);
                     
                     return obj;
                }
                else
                {
                    return null;// Response.status(ErrorCodes.UNKNOWN_DOC).build();
                }
       
        
        } catch (Exception e) {
        
             e.printStackTrace();
             
              return null;
        }
      
    }
   
    
    
    public ProfileSync getByCodeAndHash(String userid, String hash) {
        ProfileSync obj =  null;
        try 
        {
               if(userid == null ) userid ="";
               TypedQuery<ProfileSync> query = em.createNamedQuery(ProfileSync.BY_CODE_AND_HASH, ProfileSync.class).setParameter("passed", userid.trim()).setParameter("passed2", hash.trim());
                //System.out.println(" getByCodeAndHash query = " + query);
                List<ProfileSync> resultList = query.getResultList();
                if(resultList != null && resultList.size() > 0)
                {
                      obj = resultList.get(0);
                     return obj;
                }
                else
                {
                    return null;// Response.status(ErrorCodes.UNKNOWN_DOC).build();
                }
       
        
        } catch (Exception e) {
        
             e.printStackTrace();
             
              return null;
        }
    }
    
    
    @Transactional
    public ProfileSync doSync(ProfileSyncOBJ  obj) throws ServiceException, Exception
    {
        
        ProfileSync resp = null;
        
            ProfileSync psync =  doLoadByFullCode(obj.getCode(), obj.getCodeLink());//obj
            if(psync !=null)
            {  
               //psync.setCode(obj.getCode());
              // psync.setCodeLink(obj.getCodeLink());
               if(obj.getControlCode() !=null && obj.getControlCode().length() > 0)
               {
                  psync.setControlCode(obj.getControlCode());
               }
               psync.setModifiedDate(new Date());
               psync.setVHash1(rh.doVhash1(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               psync.setVHash(rh.doPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
              
               resp  = em.merge(psync);
              
            }
        
       return resp;
    }
    
    
    @Transactional
    public ProfileSync doSyncLoginInfo(ProfileSync  obj) throws ServiceException, Exception
    {
        
        ProfileSync resp = null;
        
            ProfileSync psync =  doLoadByFullCode(obj.getCode(), obj.getCodeLink());//obj
            if(psync !=null)
            {  
               //psync.setCode(obj.getCode());
              // psync.setCodeLink(obj.getCodeLink());
               psync.setLastAccessDate(new Date());
              
               resp  = em.merge(psync);
              
            }
        
       return resp;
    }
    
    @Transactional
    public ProfileSync doForceSyncProfileInfo(ProfileSyncOBJ  obj) throws ServiceException, Exception
    {
        
        ProfileSync resp = null;
         try 
         {
            
       
            ProfileSync psync =  doLoadByFullCode(obj.getCode(), obj.getCodeLink());//obj
            LOGGER.info(" ##-- doForceSyncProfileInfo -- "+psync);
            if(psync !=null)
            {
               
               psync.setVHash1(rh.doVhash1(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               psync.setVHash(rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               LOGGER.info(" doForceSyncProfileInfo -- psync.getVHash2() "+psync.getVHash2() );
               if(psync.getVHash2() == null || obj.getPin() ==null || "".equals(obj.getPin()))
               {
                    psync.setVHash2("NA");//rh.doForcePIN(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               }
              
               psync.setPxChangeDate(new Date());
               psync.setModifiedDate(new Date());
               resp  = em.merge(psync);
              
            }
            
         } catch (Exception e) {
        
         
             LOGGER.error(" -- doForceSyncProfileInfo  ",e);
         
         }
        
       return resp;
    }
    
    
    @Transactional
    public ProfileSync doForceSyncProfileTxP(ProfileSyncOBJ  obj) throws ServiceException, Exception
    {
        
        ProfileSync resp = null;
        
            ProfileSync psync =  doLoadByFullCode(obj.getCode(), obj.getCodeLink());//obj
            LOGGER.info(" -- doForceSyncProfileTxP -- "+psync);
            if(psync !=null)
            {
               
               psync.setVHash2(rh.doForcePIN(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               psync.setPxChangeDate(new Date());
               resp  = em.merge(psync);
              
            }
        
       return resp;
    }
    
    @Transactional
    public ProfileSync doSyncProfileTxP(ProfileSyncOBJ  obj) throws ServiceException, Exception
    {
        
        ProfileSync resp = null;
        
            ProfileSync psync =  doLoadByFullCode(obj.getCode(), obj.getCodeLink());//obj
            LOGGER.info(" -- doSyncProfileTxP -- "+psync);
            if(psync !=null)
            {
               
               psync.setVHash2(rh.doVerifyPIN(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               psync.setPxChangeDate(new Date());
               resp  = em.merge(psync);
              
            }
        
       return resp;
    }
    
    
   
    @Transactional
    public ProfileSync doLog(ProfileSyncOBJ  objx) 
    {
        ProfileSync obj = new ProfileSync();
        
        try 
        {
            
               obj.setPid(objx.getPid());
               obj.setUserCode(objx.getUserCode());
               obj.setMsisdn(objx.getMsisdn());
               obj.setCode(objx.getCode());
               obj.setControlCode(objx.getControlCode());
               obj.setSyncDate(new Date());
               obj.setCodeLink(objx.getCodeLink());
               obj.setVHash1(rh.doVhash1(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx));
               obj.setVHash(rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx));
               obj.setVHash2(rh.doVerifyPIN(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx));
             
               obj  = em.merge(obj);
           
            
        } catch (Exception e) {
        
            e.printStackTrace();
        
        }
       
       return obj;
    }
    
    
    public Response doSync(@Valid  final JsonObject json) {
        System.out.println(" doSync json = " +  json);
        String resp = "";
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
        
            ProfileSyncOBJ fromJson = JsonbBuilder.create().fromJson(json.toString(), ProfileSyncOBJ.class);
        
            
            if(fromJson ==null)
            {
                 return Response.status(ErrorCodes.FORMAT_ERROR).build();
            }
            
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getNewPassword()))
            {
                return Response.status(ErrorCodes.INVALID_PASSWORD).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCode()))
            {
                return Response.status(ErrorCodes.INVALID_USER_CODE).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCodeLink()))
            {
               return Response.status(ErrorCodes.INVALID_USER_CODELINK).build();
            }
            
            if(fromJson !=null  && !fromJson.getNewPassword().equals(fromJson.getVerifyPassword()))
            {
               return Response.status(ErrorCodes.INVALID_CONFIRM_PASSWORD).build();
               
            }
            
             ProfileSync doVerify = doVerify(fromJson);
            
            if(doVerify !=null)
            {
            
                    ProfileSync doSync = doSync(fromJson);

                    if(doSync != null)
                    {
                         return Response.status(ErrorCodes.ACCEPTED).entity(job.add("responseDesc", ErrorCodes.doErroDesc(ErrorCodes.ACCEPTED)).build()).build();

                    }
                    else
                    {
                          return Response.status(ErrorCodes.DATABASE_ERROR).build();//.entity(job.add("responseDesc", ErrorCodes.doErroDesc(ErrorCodes.INVALID_USER_OR_PASSWORD)).build()).build();

                    }
            }
            else
            {
                 return Response.status(ErrorCodes.INVALID_USER_OR_PASSWORD).entity(job.add("responseDesc", ErrorCodes.doErroDesc(ErrorCodes.INVALID_USER_OR_PASSWORD)).build()).build();

            }
            
            
        }
        catch (Exception e) {
        
            e.printStackTrace();
            return Response.status(ErrorCodes.SYSTEM_ERROR).entity(job.add("responseDesc", rh.toDefault(e.getMessage(), "NA")).build()).build();
        
        }
      
    }
    
    public Response doSyncTxp(@Valid  final JsonObject json) {
        System.out.println(" doSyncTxp json = " +  json);
        String resp = "";
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
        
            ProfileSyncOBJ fromJson = JsonbBuilder.create().fromJson(json.toString(), ProfileSyncOBJ.class);
        
            
            if(fromJson ==null)
            {
                 return Response.status(ErrorCodes.FORMAT_ERROR).build();
            }
            
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getPin()))
            {
                return Response.status(ErrorCodes.INVALID_PIN).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCode()))
            {
                return Response.status(ErrorCodes.INVALID_USER_CODE).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCodeLink()))
            {
               return Response.status(ErrorCodes.INVALID_USER_CODELINK).build();
            }
            
            if(fromJson !=null  && !fromJson.getNewPin().equals(fromJson.getVerifyPin()))
            {
               return Response.status(ErrorCodes.INVALID_VERIFY_PIN).build();
               
            }
            
             ProfileSync doVerify = doVerifyPIN(fromJson);
            
            if(doVerify !=null)
            {
            
                    ProfileSync doSync = doSyncProfileTxP(fromJson);

                    if(doSync != null)
                    {
                         return Response.status(ErrorCodes.ACCEPTED).entity(job.add("responseDesc", ErrorCodes.doErroDesc(ErrorCodes.ACCEPTED)).build()).build();

                    }
                    else
                    {
                          return Response.status(ErrorCodes.DATABASE_ERROR).build();//.entity(job.add("responseDesc", ErrorCodes.doErroDesc(ErrorCodes.INVALID_USER_OR_PASSWORD)).build()).build();

                    }
            }
            else
            {
                 return Response.status(ErrorCodes.INVALID_USER_OR_PASSWORD).entity(job.add("responseDesc", ErrorCodes.doErroDesc(ErrorCodes.INVALID_USER_OR_PASSWORD)).build()).build();

            }
            
            
        }
        catch (Exception e) {
        
            e.printStackTrace();
            return Response.status(ErrorCodes.SYSTEM_ERROR).entity(job.add("responseDesc", rh.toDefault(e.getMessage(), "NA")).build()).build();
        
        }
      
    }
    
    
    public Response doLog(@Valid  final JsonObject json) {
        
        String resp = "";
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
        
            ProfileSyncOBJ fromJson = JsonbBuilder.create().fromJson(json.toString(), ProfileSyncOBJ.class);
        
            
            if(fromJson ==null)
            {
                 return Response.status(ErrorCodes.FORMAT_ERROR).build();
            }
            
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getPassword()))
            {
                return Response.status(ErrorCodes.INVALID_PASSWORD).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCode()))
            {
                return Response.status(ErrorCodes.INVALID_USER_CODE).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCodeLink()))
            {
               return Response.status(ErrorCodes.INVALID_USER_CODELINK).build();
            }
            
            if(fromJson !=null  && !fromJson.getPassword().equals(fromJson.getVerifyPassword()))
            {
               return Response.status(ErrorCodes.INVALID_CONFIRM_PASSWORD).build();
               
            }
            
            
            if(doLoadByFullCode(fromJson.getCode(),fromJson.getCodeLink()) == null)
            {

                    ProfileSync doSync = doLog(fromJson);

                    if(doSync != null)
                    {
                         return Response.status(ErrorCodes.ACCEPTED).entity(job.add("responseDesc", "SUCCESSFUL").build()).build();

                    }
                    else
                    {
                          return Response.status(ErrorCodes.DATABASE_ERROR).entity(job.add("responseDesc", "DATABASE ERROR").build()).build();

                    }
            }
            else
            {
                 return Response.status(ErrorCodes.DUPLICATE_TRANSACTION).entity(job.add("responseDesc", "DUPLICATE TRANSACTION").build()).build();
            }

            
        }
        catch (Exception e) {
        
            
            return Response.status(ErrorCodes.SYSTEM_ERROR).entity(job.add("responseDesc", rh.toDefault(e.getMessage(), "NA")).build()).build();
        
        }
      
    }
    
    /*
    public Response doLog(@Valid  final JsonObject json) {
        
        String resp = "";
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
        
            ProfileSyncOBJ fromJson = JsonbBuilder.create().fromJson(json.toString(), ProfileSyncOBJ.class);
        
            
            if(fromJson ==null)
            {
                 return Response.status(ErrorCodes.FORMAT_ERROR).build();
            }
            
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getPassword()))
            {
                return Response.status(ErrorCodes.INVALID_PASSWORD).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCode()))
            {
                return Response.status(ErrorCodes.INVALID_USER_CODE).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCodeLink()))
            {
               return Response.status(ErrorCodes.INVALID_USER_CODELINK).build();
            }
            
            if(fromJson !=null  && !fromJson.getPassword().equals(fromJson.getVerifyPassword()))
            {
               return Response.status(ErrorCodes.INVALID_CONFIRM_PASSWORD).build();
               
            }
            
            
            if(doLoadByFullCode(fromJson.getCode(),fromJson.getCodeLink()) == null)
            {

                    ProfileSync doSync = doLog(fromJson);

                    if(doSync != null)
                    {
                         return Response.status(ErrorCodes.ACCEPTED).entity(job.add("responseDesc", "SUCCESSFUL").build()).build();

                    }
                    else
                    {
                          return Response.status(ErrorCodes.DATABASE_ERROR).entity(job.add("responseDesc", "DATABASE ERROR").build()).build();

                    }
            }
            else
            {
                 return Response.status(ErrorCodes.DUPLICATE_TRANSACTION).entity(job.add("responseDesc", "DUPLICATE TRANSACTION").build()).build();
            }

            
        }
        catch (Exception e) {
        
            
            return Response.status(ErrorCodes.SYSTEM_ERROR).entity(job.add("responseDesc", rh.toDefault(e.getMessage(), "NA")).build()).build();
        
        }
      
    }
    */
    public Response doLogWTP(@Valid  final JsonObject json) {
        
        String resp = "";
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
        
            ProfileSyncOBJ fromJson = JsonbBuilder.create().fromJson(json.toString(), ProfileSyncOBJ.class);
        
            if(fromJson ==null)
            {
                 return Response.status(ErrorCodes.FORMAT_ERROR).build();
            }
            
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getPassword()))
            {
                return Response.status(ErrorCodes.INVALID_PASSWORD).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCode()))
            {
                return Response.status(ErrorCodes.INVALID_USER_CODE).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCodeLink()))
            {
               return Response.status(ErrorCodes.INVALID_USER_CODELINK).build();
            }
            
            if(fromJson !=null  && !fromJson.getPassword().equals(fromJson.getVerifyPassword()))
            {
               return Response.status(ErrorCodes.INVALID_CONFIRM_PASSWORD).build();
               
            }
             
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getPin()))
            {
                return Response.status(ErrorCodes.INVALID_PIN).build();
            }
            
            if(fromJson !=null  && !fromJson.getPin().equals(fromJson.getVerifyPin()))
            {
               return Response.status(ErrorCodes.INVALID_VERIFY_PIN).build();
               
            }
            
            
            if(doLoadByFullCode(fromJson.getCode(),fromJson.getCodeLink()) == null)
            {

                    ProfileSync doSync = doLog(fromJson);

                    if(doSync != null)
                    {
                         return Response.status(ErrorCodes.ACCEPTED).entity(job.add("responseDesc", "SUCCESSFUL").build()).build();

                    }
                    else
                    {
                          return Response.status(ErrorCodes.DATABASE_ERROR).entity(job.add("responseDesc", "DATABASE ERROR").build()).build();

                    }
            }
            else
            {
                 return Response.status(ErrorCodes.DUPLICATE_TRANSACTION).entity(job.add("responseDesc", "DUPLICATE TRANSACTION").build()).build();
            }

            
        }
        catch (Exception e) {
        
            
            return Response.status(ErrorCodes.SYSTEM_ERROR).entity(job.add("responseDesc", rh.toDefault(e.getMessage(), "NA")).build()).build();
        
        }
      
    }
    
    
    public Response doForceSync(@Valid  final JsonObject json) {
        
        String resp = "";
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
        
            ProfileSyncOBJ fromJson = JsonbBuilder.create().fromJson(json.toString(), ProfileSyncOBJ.class);
        
            
            if(fromJson ==null)
            {
                 return Response.status(ErrorCodes.FORMAT_ERROR).build();
            }
            
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getPassword()))
            {
                return Response.status(ErrorCodes.INVALID_PASSWORD).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCode()))
            {
                return Response.status(ErrorCodes.INVALID_USER_CODE).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCodeLink()))
            {
               return Response.status(ErrorCodes.INVALID_USER_CODELINK).build();
            }
            
            if(fromJson !=null  && !fromJson.getPassword().equals(fromJson.getVerifyPassword()))
            {
               return Response.status(ErrorCodes.INVALID_CONFIRM_PASSWORD).build();
               
            }
           
            ProfileSync doForceSyncProfileInfo = doForceSyncProfileInfo(fromJson);
            
            if(doForceSyncProfileInfo != null)
            {

               return Response.status(ErrorCodes.ACCEPTED).entity(job.add("responseDesc", "SUCCESSFUL").build()).build();
            }
            else
            {
                return Response.status(ErrorCodes.INVALID_USER_CODELINK).entity(job.add("responseDesc", ErrorCodes.doErroDesc(ErrorCodes.INVALID_USER_CODELINK)).build()).build();

            }
            
        }
        catch (Exception e) {
        
            LOGGER.error(" Error Exception doForceSync ",e);
            
            return Response.status(ErrorCodes.SYSTEM_ERROR).entity(job.add("responseDesc", rh.toDefault(e.getMessage(), "NA")).build()).build();
        
        }
      
    }
    
    
    public Response doForceSyncTxP(@Valid  final JsonObject json) {
        LOGGER.info(" --- doForceSyncTxP --- "+json);
        String resp = "";
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
        
            ProfileSyncOBJ fromJson = JsonbBuilder.create().fromJson(json.toString(), ProfileSyncOBJ.class);
        
            
            if(fromJson ==null)
            {
                 return Response.status(ErrorCodes.FORMAT_ERROR).build();
            }
            
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getVerifyPin()))
            {
                return Response.status(ErrorCodes.INVALID_PIN).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCode()))
            {
                return Response.status(ErrorCodes.INVALID_USER_CODE).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCodeLink()))
            {
               return Response.status(ErrorCodes.INVALID_USER_CODELINK).build();
            }
            /*
            if(fromJson !=null  && !fromJson.getNewPin().equals(fromJson.getVerifyPin()))
            {
               return Response.status(ErrorCodes.INVALID_VERIFY_PIN).build();
               
            }*/
           
            ProfileSync doForceSyncProfileInfo = doForceSyncProfileTxP(fromJson);
            
            if(doForceSyncProfileInfo != null)
            {

               return Response.status(ErrorCodes.ACCEPTED).entity(job.add("responseDesc", "SUCCESSFUL").build()).build();
            }
            else
            {
                return Response.status(ErrorCodes.DATABASE_ERROR).entity(job.add("responseDesc", "DATABASE ERROR").build()).build();

            }
            
        }
        catch (Exception e) {
        
            LOGGER.error(" Error Exception doForceSync ",e);
            
            return Response.status(ErrorCodes.SYSTEM_ERROR).entity(job.add("responseDesc", rh.toDefault(e.getMessage(), "NA")).build()).build();
        
        }
      
    }
    
    
    public Response doSyncProfileAndLogin(@Valid  final JsonObject json) {
        
        String resp = "";
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
        
            ProfileSyncOBJ fromJson = JsonbBuilder.create().fromJson(json.toString(), ProfileSyncOBJ.class);
        
            
            if(fromJson ==null)
            {
                 return Response.status(ErrorCodes.FORMAT_ERROR).build();
            }
            
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getPassword()))
            {
                return Response.status(ErrorCodes.INVALID_PASSWORD).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCode()))
            {
                return Response.status(ErrorCodes.INVALID_USER_CODE).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCodeLink()))
            {
               return Response.status(ErrorCodes.INVALID_USER_CODELINK).build();
            }
            
            if(fromJson !=null  && !fromJson.getPassword().equals(fromJson.getVerifyPassword()))
            {
               return Response.status(ErrorCodes.INVALID_CONFIRM_PASSWORD).build();
               
            }
            
            
            if(doLoadByFullCode(fromJson.getCode(),fromJson.getCodeLink()) == null)
            {

                    ProfileSync doSync = doLog(fromJson);

                    if(doSync != null)
                    {
                        
                        
                           
                           String issuedToken = tokenHelper.issueToken(doSync, uriInfo);
                           LOGGER.info("@@--*doSyncProfileAndLogin  X doVerify X *--@"+issuedToken);
                        
                            ProfileSync doSyncLoginInfo = doSyncLoginInfo(doSync);

                            if(doSyncLoginInfo != null)
                            {
                                // return Response.status(ErrorCodes.ACCEPTED).entity(job.add("responseDesc", ErrorCodes.doErroDesc(ErrorCodes.ACCEPTED)).build()).build();

                                 return Response.accepted(Json.createObjectBuilder().add(AUTHORIZATION, "Bearer " + issuedToken).build().toString()).entity(doSyncLoginInfo.toJson("SUCCESSFUL")).header(AUTHORIZATION, "Bearer " + issuedToken).build();

                            }
                            else
                            {
                                  return Response.status(ErrorCodes.DATABASE_ERROR).build();//.entity(job.add("responseDesc", ErrorCodes.doErroDesc(ErrorCodes.INVALID_USER_OR_PASSWORD)).build()).build();

                            }
                        
                      
                    }
                    else
                    {
                          return Response.status(ErrorCodes.DATABASE_ERROR).entity(job.add("responseDesc", "DATABASE ERROR").build()).build();

                    }
            }
            else
            {
                 return Response.status(ErrorCodes.DUPLICATE_TRANSACTION).entity(job.add("responseDesc", "DUPLICATE TRANSACTION").build()).build();
            }

            
        }
        catch (Exception e) {
        
            
            return Response.status(ErrorCodes.SYSTEM_ERROR).entity(job.add("responseDesc", rh.toDefault(e.getMessage(), "NA")).build()).build();
        
        }
      
    }
    
    
    
    public Response doVerify(@Valid  final JsonObject json) {
        
        String resp = "";
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
        
            ProfileSyncOBJ fromJson = JsonbBuilder.create().fromJson(json.toString(), ProfileSyncOBJ.class);
            
            if(fromJson ==null)
            {
                 return Response.status(ErrorCodes.FORMAT_ERROR).build();
            }
            
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getPassword()))
            {
                return Response.status(ErrorCodes.INVALID_PASSWORD).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCode()))
            {
                return Response.status(ErrorCodes.INVALID_USER_CODE).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCodeLink()))
            {
               return Response.status(ErrorCodes.INVALID_USER_CODELINK).build();
            }
           
            ProfileSync doSync = doVerify(fromJson);
            LOGGER.info(" @- doVerify  --@ "+doSync);
            if(doSync != null)
            {
              
                  LOGGER.info(" @- doSync  doSync.toJson( --@ "+doSync.toJson());
                  
                String issuedToken = tokenHelper.issueToken(doSync, uriInfo);
                LOGGER.info("@@--* X doVerify X *--@"+issuedToken);
                 //return Response.status(ErrorCodes.ACCEPTED).entity(job.add("responseDesc", "SUCCESSFUL").build()).header(AUTHORIZATION, "Bearer " + issuedToken).build();
        
                    ProfileSync doSyncLoginInfo = doSyncLoginInfo(doSync);

                    //LOGGER.info("-- doSyncLoginInfo -  "+doSyncLoginInfo);
                    
                    if(doSyncLoginInfo != null)
                    {
                        // return Response.status(ErrorCodes.ACCEPTED).entity(job.add("responseDesc", ErrorCodes.doErroDesc(ErrorCodes.ACCEPTED)).build()).build();

                         return Response.accepted(Json.createObjectBuilder().add(AUTHORIZATION, "Bearer " + issuedToken).build().toString()).entity(doSyncLoginInfo.toJson("SUCCESSFUL")).header(AUTHORIZATION, "Bearer " + issuedToken).build();
            
                    }
                    else
                    {
                          return Response.status(ErrorCodes.DATABASE_ERROR).build();//.entity(job.add("responseDesc", ErrorCodes.doErroDesc(ErrorCodes.INVALID_USER_OR_PASSWORD)).build()).build();

                    }
               
               
            }
            else
            {
                  return Response.status(ErrorCodes.INVALID_USER_OR_PASSWORD).entity(job.add("responseDesc", ErrorCodes.doErroDesc(ErrorCodes.INVALID_USER_OR_PASSWORD)).build()).build();
        
            }
            
            
        }
        catch (Exception e) {
        
            
            LOGGER.error(" -- doVerify ERROR -- ", e);
            
            return Response.status(ErrorCodes.SYSTEM_ERROR).entity(job.add("responseDesc", rh.toDefault(e.getMessage(), "NA")).build()).build();
        
            
            
        }
      
    }
    
    
    public Response doVerifyTxp(@Valid  final JsonObject json) {
        LOGGER.info(" --- doVerifyTxp -- "+json);
        String resp = "";
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
        
            ProfileSyncOBJ fromJson = JsonbBuilder.create().fromJson(json.toString(), ProfileSyncOBJ.class);
            
            if(fromJson ==null)
            {
                 return Response.status(ErrorCodes.FORMAT_ERROR).build();
            }
            
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getPin()))
            {
                return Response.status(ErrorCodes.INVALID_PIN).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCode()))
            {
                return Response.status(ErrorCodes.INVALID_USER_CODE).build();
            }
          
           
            ProfileSync doSync = doVerifyTxPIN(fromJson);
            LOGGER.info(" @- doVerifyTxPIN  --@ "+doSync);
            if(doSync != null)
            {
              
               return Response.accepted().entity(doSync.toJson("SUCCESSFUL")).build();
            
            }
            else
            {
                  return Response.status(ErrorCodes.INVALID_PIN).entity(job.add("responseDesc", ErrorCodes.doErroDesc(ErrorCodes.INVALID_PIN)).build()).build();
        
            }
            
            
        }
        catch (Exception e) {
        
            
            LOGGER.error(" -- doVerifyTxPIN ERROR -- ", e);
            
            return Response.status(ErrorCodes.SYSTEM_ERROR).entity(job.add("responseDesc", rh.toDefault(e.getMessage(), "NA")).build()).build();
        
            
            
        }
      
    }
    
    
    
    
}
