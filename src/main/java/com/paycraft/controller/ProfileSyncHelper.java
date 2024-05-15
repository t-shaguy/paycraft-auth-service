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
import com.paycraftsystems.exceptions.PaycraftException;
import static io.quarkus.hibernate.orm.panache.PanacheEntityBase.delete;
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
   
    public ProfileSync doLoadByFullCode(long pid,  String channelCode) {
        LOGGER.info("doLoadByFullCode   pid -- "+pid+" channelCode : "+channelCode);
        ProfileSync obj =  null;
        try 
        {
               //System.out.println("ProfileSync = " + code);
               TypedQuery<ProfileSync> query = em.createNamedQuery(ProfileSync.BY_PID_AND_CHANNEL, ProfileSync.class).setParameter("passed", pid).setParameter("passed2", channelCode);//.setParameter("passed3", channelCode); 
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
    
    public ProfileSync doLoadByFullCode(long pid,  String channelCode, String clientId) {
        LOGGER.info("doLoadByFullCode   pid -- "+pid+" channelCode : "+channelCode+" app id "+clientId);
        ProfileSync obj =  null;
        try 
        {
               //System.out.println("ProfileSync = " + code);
               TypedQuery<ProfileSync> query = em.createNamedQuery(ProfileSync.BY_PID_AND_CHANNEL_AND_CLIENT, ProfileSync.class).setParameter("passed", pid).setParameter("passed2", channelCode).setParameter("passed3", clientId);//.setParameter("passed3", channelCode); 
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
        ProfileSync main = null;
        try 
        { 
               TypedQuery<ProfileSync> queryx = em.createNamedQuery(ProfileSync.BY_PID_AND_CHANNEL, ProfileSync.class).setParameter("passed", objx.getPid()).setParameter("passed2", objx.getChannel());
               LOGGER.info("@@@ doVerifyV2 -- "+((queryx==null)?0:queryx.getResultList().size()));
               if(queryx == null)
               {
                   return null;
               }
               else if (queryx.getResultList().size() > 0)
               {
                   main = queryx.getResultList().get(0);
                   
                   LOGGER.info("PICKED MAIN "+main);
               }
               LOGGER.info("#ProfileSyncOBJ = " + objx);
               
                //LOGGER.info(" -- KEY -- "+sysDataHelper.getProps("SYS_KEY", "~")+" + IV + "+sysDataHelper.getProps("SYS_IV", "~"));
                LOGGER.info("-- ProfileSyncOBJ Paxx = " + rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx.getPassword(), main));
               
               //LOGGER.info(" plain pass  = " + rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx));
               //TypedQuery<ProfileSync> query = em.createNamedQuery(ProfileSync.BY_CODE_AND_HASH, ProfileSync.class).setParameter("passed", objx.getCode()).setParameter("passed2", objx.getCodeLink()).setParameter("passed3", rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx));
               TypedQuery<ProfileSync> query = em.createNamedQuery(ProfileSync.BY_CODE_AND_HASH, ProfileSync.class).setParameter("passed", objx.getCode()).setParameter("passed2", objx.getCodeLink()).setParameter("passed4", objx.getChannel()).setParameter("passed3", rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx.getPassword(), main));
               
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
    
    public ProfileSync doVerifyV2(ProfileSyncOBJ objx) {
        
        ProfileSync obj =  null;
        ProfileSync main =  null;
        String hash = "";
        try 
        { 
               
               TypedQuery<ProfileSync> queryx = em.createNamedQuery(ProfileSync.BY_PID_AND_CHANNEL, ProfileSync.class).setParameter("passed", objx.getPid()).setParameter("passed2", objx.getChannel());
               LOGGER.info("@ - doVerifyV2 -- "+queryx);
               if(queryx == null)
               {
                   return null;
               }
               else if (queryx.getResultList().size() > 0)
               {
                   main = queryx.getResultList().get(0);
                   
                   LOGGER.info("-PICKED MAIN "+main);
               }
               
               if(main == null)
               {
                   return null;
               }
               
               LOGGER.info("#ProfileSyncOBJ = doVerifyV2 " + objx); //doVerifyPassword(String key, String iv, String password, ProfileSync obj)
               LOGGER.info("-#- ProfileSyncOBJ Paxx = " + rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx.getPassword(), main));
               //p.pid = :passed and p.codeLink = :passed2 and p.vHash = :passed3 and p.channel = :passed4
               hash = rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx.getPassword(), main);
               LOGGER.info(" @@- pid - "+main.getPid()+" codelink "+main.getCodeLink()+" vhash - "+hash+" channel "+main.getChannel());
               
               LOGGER.info(" select * from profile_sync where pid =  "+main.getPid()+" and  code_link = '"+main.getCodeLink()+"' and  v_hash = '"+rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx.getPassword(), main)+"' and  channel = '"+main.getChannel()+"'");
               
                //LOGGER.info(" plain pass  = " + rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx));
               //TypedQuery<ProfileSync> query = em.createNamedQuery(ProfileSync.BY_CODE_AND_HASH, ProfileSync.class).setParameter("passed", objx.getCode()).setParameter("passed2", objx.getCodeLink()).setParameter("passed3", rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx));
                //p.pid = :passed and p.codeLink = :passed2 and p.vHash = :passed3 and p.channel = :passed4"
                
                List<ProfileSync> query = em.createNativeQuery("select * from profile_sync where pid =  "+main.getPid()+" and  code_link = '"+main.getCodeLink()+"' and  v_hash = '"+rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx.getPassword(), main)+"' and  channel = '"+main.getChannel()+"'",ProfileSync.class).getResultList();//.createNamedQuery(ProfileSync.BY_PID_AND_HASH, ProfileSync.class).setParameter("passed", main.getPid()).setParameter("passed2", objx.getCodeLink()).setParameter("passed3", hash).setParameter("passed4", objx.getChannel()).getResultList();
                //TypedQuery<ProfileSync> query = em.createNamedQuery(ProfileSync.BY_PID_AND_HASH, ProfileSync.class).setParameter("passed", main.getPid()).setParameter("passed2", objx.getCodeLink()).setParameter("passed3", rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx.getPassword(), main)).setParameter("passed4", objx.getChannel());
                
                LOGGER.info(" *@** ProfileSync query size = " + ((query==null)?0:query.size()));
               // List<ProfileSync> resultList = query.getResultList();
                if(query != null && query.size() > 0)
                {
                      obj = query.get(0);
                      
                      doSyncLoginInfo(obj);
                      
                     return obj;
                }
                else
                {
                    return null;// Response.status(ErrorCodes.UNKNOWN_DOC).build();
                }
       
        
        } catch (Exception e) {
        
             e.printStackTrace();
             
             LOGGER.error(": Exception @ : doVerifyV2 ",e);
             
              return null;
        }
      
    }
    
    /*
     by client Id and channel
    */
    
    public ProfileSync doVerifyV3(ProfileSyncOBJ objx) {
        
        ProfileSync obj =  null;
        ProfileSync main =  null;
        try 
        { 
               
               TypedQuery<ProfileSync> queryx = em.createNamedQuery(ProfileSync.BY_PID_AND_CHANNEL_AND_CLIENT, ProfileSync.class).setParameter("passed", objx.getPid()).setParameter("passed2", objx.getChannel()).setParameter("passed3", objx.getAppId());
               
               LOGGER.info("doVerifyV3 -- "+queryx);
               if(queryx == null)
               {
                   return null;
               }
               else if (queryx.getResultList().size() > 0)
               {
                   main = queryx.getResultList().get(0);
                   
                   LOGGER.info("- PICKED MAIN "+main);
               }
               
               if(main == null)
               {
                   return null;
               }
               
               LOGGER.info("#ProfileSyncOBJ = doVerifyV2 " + objx); //doVerifyPassword(String key, String iv, String password, ProfileSync obj)
               LOGGER.info("-@#@- ProfileSyncOBJ Paxx = " + rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx.getPassword(), main));
               
               //LOGGER.info(" plain pass  = " + rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx));
               //TypedQuery<ProfileSync> query = em.createNamedQuery(ProfileSync.BY_CODE_AND_HASH, ProfileSync.class).setParameter("passed", objx.getCode()).setParameter("passed2", objx.getCodeLink()).setParameter("passed3", rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx));
               TypedQuery<ProfileSync> query = em.createNamedQuery(ProfileSync.BY_PID_AND_HASH, ProfileSync.class).setParameter("passed", main.getPid()).setParameter("passed2", objx.getCodeLink()).setParameter("passed4", objx.getChannel()).setParameter("passed3", rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx.getPassword(), main));
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
        ProfileSync main = null;
        try 
        { 
               TypedQuery<ProfileSync> queryx = em.createNamedQuery(ProfileSync.BY_PID_AND_CHANNEL, ProfileSync.class).setParameter("passed", objx.getPid()).setParameter("passed2", objx.getChannel());
               LOGGER.info("doVerifyTxPIN -- "+queryx);
               if(queryx == null)
               {
                   return null;
               }
               else if (queryx.getResultList().size() > 0)
               {
                   main = queryx.getResultList().get(0);
                   
                   LOGGER.info(" doVerifyTxPIN PICKED MAIN "+main);
               }
            
            
               LOGGER.info("ProfileSyncOBJ doVerifyTxPIN = " + objx);
               //LOGGER.info(" plain pass  = " + rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx));
               TypedQuery<ProfileSync> query = em.createNamedQuery(ProfileSync.BY_PID_AND_TXP_HASH2, ProfileSync.class).setParameter("passed", main.getPid()).setParameter("passed2", rh.doVerifyPIN(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx.getPin(), main));
               // BY_CODE_AND_TXP_HASH2
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
        
            ProfileSync psync =  doLoadByFullCode(obj.getPid(), obj.getChannel());//obj
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
        
            ProfileSync psync =  doLoadByFullCode(obj.getPid(),obj.getChannel());//obj
            System.out.println("doSyncLoginInfo = " + psync);
            if(psync !=null)
            {  
               //psync.setCode(obj.getCode());
               psync.setChannel(obj.getChannel());
               psync.setLastAccessDate(new Date());
              
               resp  = em.merge(psync);
              
            }
        
       return resp;
    }
    
    @Transactional
    public ProfileSync doForceSyncProfileInfo(ProfileSyncOBJ  obj) throws ServiceException, Exception
    {
        System.out.println("doForceSyncProfileInfo obj = " + obj);
        ProfileSync resp = null;
         try 
         {
            
       
            ProfileSync psync =  doLoadByFullCode(obj.getPid(), obj.getChannel());//obj
            LOGGER.info(" ##-- doForceSyncProfileInfo --exists "+psync);
            if(psync !=null)
            {
                
               System.out.println(" ABOUT TO RESYNC GEN ");
               psync.setVHash1(rh.doVhash1(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               psync.setVHash(rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               
               LOGGER.info(" doForceSyncProfileInfo -- psync.getVHash1() "+psync.getVHash1() );
               
               LOGGER.info(" doForceSyncProfileInfo -- psync.getVHash() "+psync.getVHash() );
               
               if(psync.getVHash2() == null || obj.getPin() ==null || "".equals(obj.getPin()))
               {
                    psync.setVHash2("NA");//rh.doForcePIN(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               }
               psync.setPid(obj.getPid());
               psync.setChannel(obj.getChannel());
               psync.setPxChangeDate(new Date());
               psync.setModifiedDate(new Date());
               //psync.setPid(obj.getPid());
               resp  = em.merge(psync);
               
                System.out.println("psync =resp  " + resp);
              
            }
            else
            {
               ProfileSync newsync  = new ProfileSync();
               LOGGER.info(" ##--FRESH doForceSyncProfileInfo ->>- "+rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               newsync.setVHash1(rh.doVhash1(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               newsync.setVHash(rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               LOGGER.info(" doForceSyncProfileInfo -- psync.getVHash2() "+newsync.getVHash2() );
               if(newsync.getVHash2() == null || obj.getPin() ==null || "".equals(obj.getPin()))
               {
                    newsync.setVHash2("NA");//rh.doForcePIN(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               }
               newsync.setMsisdn(obj.getMsisdn());
               newsync.setCode(obj.getCode());
               newsync.setCodeLink(obj.getCodeLink());
               newsync.setPid(obj.getPid());
               newsync.setChannel(obj.getChannel());
               newsync.setControlCode(obj.getControlCode());
               newsync.setSyncDate(new Date());
               newsync.setPxChangeDate(new Date());
               newsync.setModifiedDate(new Date());
               resp  = em.merge(newsync);
               
               System.out.println("newsync = " + resp);
            }
            
         } catch (Exception e) {
        
         
             LOGGER.error(" -- doForceSyncProfileInfo  ",e);
         
         }
        
       return resp;
    }
    
    @Transactional
    public ProfileSync doForceSyncProfileInfoV2(ProfileSyncOBJ  obj) throws ServiceException, Exception
    {
        System.out.println("doForceSyncProfileInfo obj = " + obj);
        ProfileSync resp = null;
         try 
         {
            
       
            ProfileSync psync =  doLoadByFullCode(obj.getPid(), obj.getChannel(), obj.getAppId());//obj
            LOGGER.info(" ##-- doForceSyncProfileInfo --exists "+psync);
            if(psync !=null)
            {
                
               System.out.println(" ABOUT TO RESYNC GEN v2 ");
               psync.setVHash1(rh.doVhash1(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               psync.setVHash(rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               
               LOGGER.info(" doForceSyncProfileInfo -- psync.getVHash1() "+psync.getVHash1() );
               
               LOGGER.info(" doForceSyncProfileInfo -- psync.getVHash() "+psync.getVHash() );
               
               if(psync.getVHash2() == null || obj.getPin() ==null || "".equals(obj.getPin()))
               {
                    psync.setVHash2("NA");//rh.doForcePIN(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               }
               psync.setPid(obj.getPid());
               psync.setChannel(obj.getChannel());
               psync.setPxChangeDate(new Date());
               psync.setModifiedDate(new Date());
               //psync.setPid(obj.getPid());
               resp  = em.merge(psync);
               
                System.out.println("psync =resp  " + resp);
              
            }
            else
            {
               ProfileSync newsync  = new ProfileSync();
               LOGGER.info(" ##--FRESH doForceSyncProfileInfo ->>- "+rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               newsync.setVHash1(rh.doVhash1(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               newsync.setVHash(rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               LOGGER.info(" doForceSyncProfileInfo -- psync.getVHash2() "+newsync.getVHash2() );
               if(newsync.getVHash2() == null || obj.getPin() ==null || "".equals(obj.getPin()))
               {
                    newsync.setVHash2("NA");//rh.doForcePIN(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               }
               newsync.setMsisdn(obj.getMsisdn());
               newsync.setCode(obj.getCode());
               newsync.setCodeLink(obj.getCodeLink());
               newsync.setPid(obj.getPid());
               newsync.setChannel(obj.getChannel());
               newsync.setControlCode(obj.getControlCode());
               newsync.setClientId(obj.getAppId());
               newsync.setSyncDate(new Date());
               newsync.setPxChangeDate(new Date());
               newsync.setModifiedDate(new Date());
               resp  = em.merge(newsync);
               
               System.out.println("newsync = " + resp);
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
        
        try 
        {
            
       
            ProfileSync psync =  doLoadByFullCode(obj.getPid(), obj.getChannel());//obj
            
            LOGGER.info(" -- doForceSyncProfileTxP -- "+psync);
            /*
            if(psync !=null)
            {
               
               psync.setVHash2(rh.doForcePIN(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               psync.setPid(obj.getPid());
               psync.setPxChangeDate(new Date());
               resp  = em.merge(psync);
              
            }
            */
            if(psync !=null)
            {
                
               System.out.println(" ABOUT TO RESYNC PIN ");
                psync.setVHash2(rh.doForcePIN(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
              
               //psync.setVHash2(rh.doVhash1(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               //psync.setVHash(rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               
               LOGGER.info(" doForceSyncProfileInfo -- psync.getVHash1() "+psync.getVHash1() );
               
               LOGGER.info(" doForceSyncProfileInfo -- psync.getVHash() "+psync.getVHash() );
               /*
               if(psync.getVHash2() == null || obj.getPin() ==null || "".equals(obj.getPin()))
               {
                    psync.setVHash2("NA");//rh.doForcePIN(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               }*/
               psync.setPid(obj.getPid());
               psync.setChannel(obj.getChannel());
               psync.setPxChangeDate(new Date());
               psync.setModifiedDate(new Date());
               //psync.setPid(obj.getPid());
               resp  = em.merge(psync);
               
                System.out.println("PIN psync =resp  " + resp);
              
            }
            else
            {
               ProfileSync newsync  = new ProfileSync();
               LOGGER.info(" ##--FRESH doForceSyncProfileInfo ->>- "+rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
              
               newsync.setVHash2(rh.doForcePIN(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
              
               // newsync.setVHash1(rh.doVhash1(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               //newsync.setVHash(rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               LOGGER.info(" doForceSyncProfileInfo -- psync.getVHash2() "+newsync.getVHash2() );
               /*
               if(newsync.getVHash2() == null || obj.getPin() ==null || "".equals(obj.getPin()))
               {
                    newsync.setVHash2("NA");//rh.doForcePIN(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               }
               */
               newsync.setMsisdn(obj.getMsisdn());
               newsync.setCode(obj.getCode());
               newsync.setCodeLink(obj.getCodeLink());
               newsync.setPid(obj.getPid());
               newsync.setChannel(obj.getChannel());
               newsync.setControlCode(obj.getControlCode());
               newsync.setSyncDate(new Date());
               newsync.setPxChangeDate(new Date());
               newsync.setModifiedDate(new Date());
               resp  = em.merge(newsync);
               
               System.out.println(" pin -- newsync = " + resp);
            }
            
        }
        catch (Exception e) {
            
               /*
               if(e.getCause() instanceof com.paycraftsystems.exceptions.PaycraftException)
                {
                    System.err.println(" ** AHA = " + e.getMessage()+" cause "+e.getCause());
                   // PeeloException exp =   (PeeloException) e.getCause();
                    return Response.status(exp.getResponse().getStatus()).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErroDesc(exp.getResponse().getStatus())).build()).build();
                }
                */
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof javax.persistence.PersistenceException))
                {
                     throw new PaycraftException(ErrorCodes.DATABASE_ERROR, e.getMessage());
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                     throw new PaycraftException(ErrorCodes.IO_EXCEPTION, e.getMessage());
                }
                else
                {
                     throw new PaycraftException(ErrorCodes.SYSTEM_ERROR, e.getMessage());
                }
        }
        
        
       return resp;
    }
    
    @Transactional
    public ProfileSync doForceSyncProfileTxPV2(ProfileSyncOBJ  obj) throws ServiceException, Exception
    {
        
        ProfileSync resp = null;
        
        try 
        {
            
       
            ProfileSync psync =  doLoadByFullCode(obj.getPid(), obj.getChannel());//obj
            
            LOGGER.info(" -- doForceSyncProfileTxP -- "+psync);
            if(psync !=null)
            {
               
               psync.setVHash2(rh.doForcePIN(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               psync.setPid(obj.getPid());
               psync.setPxChangeDate(new Date());
               resp  = em.merge(psync);
              
            }
            else
            {
                
            }
            
        }
        catch (Exception e) {
            
               /*
               if(e.getCause() instanceof com.paycraftsystems.exceptions.PaycraftException)
                {
                    System.err.println(" ** AHA = " + e.getMessage()+" cause "+e.getCause());
                   // PeeloException exp =   (PeeloException) e.getCause();
                    return Response.status(exp.getResponse().getStatus()).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErroDesc(exp.getResponse().getStatus())).build()).build();
                }
                */
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof javax.persistence.PersistenceException))
                {
                     throw new PaycraftException(ErrorCodes.DATABASE_ERROR, e.getMessage());
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                     throw new PaycraftException(ErrorCodes.IO_EXCEPTION, e.getMessage());
                }
                else
                {
                     throw new PaycraftException(ErrorCodes.SYSTEM_ERROR, e.getMessage());
                }
        }
        
        
       return resp;
    }
    
    @Transactional
    public ProfileSync doSyncProfileTxP(ProfileSyncOBJ  obj) throws ServiceException, Exception
    {
        
        ProfileSync resp = null;
        
            ProfileSync psync =  doLoadByFullCode(obj.getPid(), obj.getChannel());//obj
            LOGGER.info(" -- doSyncProfileTxP -- "+psync);
            if(psync !=null)
            {
               
               psync.setVHash2(rh.doVerifyPIN(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), obj));
               psync.setPxChangeDate(new Date());
               resp  = em.merge(psync);
              
            }
        
       return resp;
    }
    
    public static long doClearProfile(String phoneNo)
    {
        long delete =0;
        ProfileSync obj = null;
        try 
        {
          
             delete = delete("code ", phoneNo); //.firstResult();
            
        } catch (Exception e) {
        
           LOGGER.error(" loadByParams - ",e);
            
        }
        
      return delete;
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
    
    @Transactional
    public ProfileSync doLogTXP(ProfileSyncOBJ  objx) 
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
               obj.setChannel(objx.getChannel());
               obj.setVHash1(rh.doVhash1(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx));
               obj.setVHash("NA");// obj.setVHash(rh.doVerifyPassword(sysDataHelper.getProps("SYS_KEY", "~"), sysDataHelper.getProps("SYS_IV", "~"), objx));
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
            
            if(1 > fromJson.getPid())
            {
               return Response.status(ErrorCodes.INVALID_USERNAME).build();
               
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
            
            if(rh.isNullorEmpty(fromJson.getPassword()))
            {
                return Response.status(ErrorCodes.INVALID_PASSWORD).build();
            }
            if(rh.isNullorEmpty(fromJson.getCode()))
            {
                return Response.status(ErrorCodes.INVALID_USER_CODE).build();
            }
            if(rh.isNullorEmpty(fromJson.getCodeLink()))
            {
               return Response.status(ErrorCodes.INVALID_USER_CODELINK).build();
            }
            
            if(!fromJson.getPassword().equals(fromJson.getVerifyPassword()))
            {
               return Response.status(ErrorCodes.INVALID_CONFIRM_PASSWORD).build();
               
            }
            
            if(rh.isNullorEmpty(fromJson.getChannel()))
            {
               return Response.status(ErrorCodes.INVALID_CHANNEL).build();
            }
            
            if(doLoadByFullCode(fromJson.getPid(), fromJson.getChannel()) == null)
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
            
            
            if(doLoadByFullCode(fromJson.getPid(),fromJson.getChannel()) == null)
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
        
            LOGGER.error("  --doLogWTP  Exception -- ", e);
            return Response.status(ErrorCodes.SYSTEM_ERROR).entity(job.add("responseDesc", rh.toDefault(e.getMessage(), "NA")).build()).build();
        
        }
      
    }
    
    public Response doLogTXP(@Valid  final JsonObject json) {
        
        String resp = "";
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
        
            ProfileSyncOBJ fromJson = JsonbBuilder.create().fromJson(json.toString(), ProfileSyncOBJ.class);
        
            if(fromJson ==null)
            {
                 return Response.status(ErrorCodes.FORMAT_ERROR).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCode()))
            {
                return Response.status(ErrorCodes.INVALID_USER_CODE).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCodeLink()))
            {
               return Response.status(ErrorCodes.INVALID_USER_CODELINK).build();
            }
           
             
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getPin()))
            {
                return Response.status(ErrorCodes.INVALID_PIN).build();
            }
            
            if(fromJson !=null  && !fromJson.getPin().equals(fromJson.getVerifyPin()))
            {
               return Response.status(ErrorCodes.INVALID_VERIFY_PIN).build();
               
            }
            
            
            if(doLoadByFullCode(fromJson.getPid(), fromJson.getChannel()) == null)
            {
               

                    ProfileSync doSync = doLogTXP(fromJson);

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
        
            LOGGER.error("  --doLogWTP  Exception -- ", e);
            return Response.status(ErrorCodes.SYSTEM_ERROR).entity(job.add("responseDesc", rh.toDefault(e.getMessage(), "NA")).build()).build();
        
        }
      
    }
    
    
    public Response doForceSync(@Valid  final JsonObject json) {
        System.out.println("doForceSync json = " +  json);
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
            
            if(fromJson !=null  && (rh.isNullorEmpty(fromJson.getChannel()) || "NA".equals(fromJson.getChannel())))
            {
               return Response.status(ErrorCodes.INVALID_CHANNEL).build();
               
            }
            
            if(1 > fromJson.getPid())
            {
               return Response.status(ErrorCodes.INVALID_USERNAME).build();
               
            }
           
            ProfileSync doForceSyncProfileInfo = doForceSyncProfileInfo(fromJson);
            
            if(doForceSyncProfileInfo != null)
            {

               return Response.status(ErrorCodes.ACCEPTED).entity(job.add("responseDesc", "SUCCESSFUL").build()).build();
            }
            else
            {
                return Response.status(ErrorCodes.SYSTEM_ERROR).entity(job.add("responseDesc", ErrorCodes.doErroDesc(ErrorCodes.SYSTEM_ERROR)).build()).build();

            }
            
        }
        catch (Exception e) {
        
            LOGGER.error(" Error Exception doForceSync ",e);
            
            return Response.status(ErrorCodes.SYSTEM_ERROR).entity(job.add("responseDesc", rh.toDefault(e.getMessage(), "NA")).build()).build();
        
        }
      
    }
    
    public Response doForceSyncByClient(@Valid  final JsonObject json) {
        System.out.println("doForceSyncByClient json = " +  json);
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
            
            if(fromJson !=null  && (rh.isNullorEmpty(fromJson.getChannel()) || "NA".equals(fromJson.getChannel())))
            {
               return Response.status(ErrorCodes.INVALID_CHANNEL).build();
               
            }
            
            if(fromJson !=null  && (rh.isNullorEmpty(fromJson.getAppId()) || "NA".equals(fromJson.getAppId())))
            {
               return Response.status(ErrorCodes.INVALID_APP_ID).build();
               
            }
            
            if(1 > fromJson.getPid())
            {
               return Response.status(ErrorCodes.INVALID_USERNAME).build();
               
            }
           
            ProfileSync doForceSyncProfileInfo = doForceSyncProfileInfoV2(fromJson);
            
            if(doForceSyncProfileInfo != null)
            {

               return Response.status(ErrorCodes.ACCEPTED).entity(job.add("responseDesc", "SUCCESSFUL").build()).build();
            }
            else
            {
                return Response.status(ErrorCodes.SYSTEM_ERROR).entity(job.add("responseDesc", ErrorCodes.doErroDesc(ErrorCodes.SYSTEM_ERROR)).build()).build();

            }
            
        }
        catch (Exception e) {
        
            LOGGER.error(" Error Exception doForceSync ",e);
            
            return Response.status(ErrorCodes.SYSTEM_ERROR).entity(job.add("responseDesc", rh.toDefault(e.getMessage(), "NA")).build()).build();
        
        }
      
    }
    
    
    public Response doForceSyncTxP(@Valid  final JsonObject json) {
        LOGGER.info(" @--- doForceSyncTxP --- "+json);
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
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getPin()))
            {
                return Response.status(ErrorCodes.INVALID_PIN).build();
            }
            if(fromJson !=null  && (!fromJson.getPin().equals(fromJson.getVerifyPin())))
            {
                return Response.status(ErrorCodes.INVALID_VERIFY_PIN).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCode()))
            {
                return Response.status(ErrorCodes.INVALID_USER_CODE).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCodeLink()))
            {
               return Response.status(ErrorCodes.INVALID_USER_CODELINK).build();
            }
           
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getChannel()))
            {
               return Response.status(ErrorCodes.INVALID_CHANNEL).build();
               
            }
            
            if(1 > fromJson.getPid())
            {
               return Response.status(ErrorCodes.INVALID_USERNAME).build();
               
            }
            LOGGER.info(" GOT HERE -- >  "+fromJson);
            ProfileSync doForceSyncProfileInfo = doForceSyncProfileTxP(fromJson);
            LOGGER.info(": doForceSyncProfileInfo ---> :"+doForceSyncProfileInfo);
            if(doForceSyncProfileInfo != null)
            {

               return Response.status(ErrorCodes.ACCEPTED).entity(job.add("responseDesc", "SUCCESSFUL").build()).build();
            }
            else
            {
                return Response.status(ErrorCodes.INVALID_USERNAME).entity(job.add("responseDesc", ErrorCodes.doErroDesc(ErrorCodes.INVALID_USERNAME)).build()).build();

            }
            
        }
        catch (Exception e) {
        
            LOGGER.error(" Error Exception doForceSync ",e);
            
            return Response.status(ErrorCodes.SYSTEM_ERROR).entity(job.add("responseDesc", rh.toDefault(e.getMessage(), "NA")).build()).build();
        
        }
      
    }
    
    
    public Response doForceSyncTxPV2(@Valid  final JsonObject json) {
        LOGGER.info(" @--- doForceSyncTxPV2 --- "+json);
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
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getPin()))
            {
                return Response.status(ErrorCodes.INVALID_PIN).build();
            }
            if(fromJson !=null  && (!fromJson.getPin().equals(fromJson.getVerifyPin())))
            {
                return Response.status(ErrorCodes.INVALID_VERIFY_PIN).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCode()))
            {
                return Response.status(ErrorCodes.INVALID_USER_CODE).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCodeLink()))
            {
               return Response.status(ErrorCodes.INVALID_USER_CODELINK).build();
            }
           
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getChannel()))
            {
               return Response.status(ErrorCodes.INVALID_CHANNEL).build();
               
            }
            
            if(1 > fromJson.getPid())
            {
               return Response.status(ErrorCodes.INVALID_USERNAME).build();
               
            }
            LOGGER.info(" GOT HERE -- >  "+fromJson);
            ProfileSync doForceSyncProfileInfo = doForceSyncProfileTxP(fromJson);
            
            LOGGER.info(": doForceSyncProfileInfo ---> :"+doForceSyncProfileInfo);
            if(doForceSyncProfileInfo != null)
            {

               return Response.status(ErrorCodes.ACCEPTED).entity(job.add("responseDesc", "SUCCESSFUL").build()).build();
            }
            else
            {
                return Response.status(ErrorCodes.INVALID_USERNAME).entity(job.add("responseDesc", ErrorCodes.doErroDesc(ErrorCodes.INVALID_USERNAME)).build()).build();

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
            
            
            if(doLoadByFullCode(fromJson.getPid(), fromJson.getChannel()) == null)
            {

                    ProfileSync doSync = doLog(fromJson);

                    if(doSync != null)
                    {
                        
                        
                           
                           String issuedToken = tokenHelper.issueToken(doSync, uriInfo);
                           LOGGER.info("@@--*doSyncProfileAndLogin  X doVerify X *--@"+issuedToken);
                        
                            ProfileSync doSyncLoginInfo = doSyncLoginInfo(doSync);

                            if(doSyncLoginInfo != null)
                            {
                              
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
        LOGGER.info("--@@  - doVerify - "+json);
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
            if(fromJson !=null  && 1 > fromJson.getPid())
            {
                return Response.status(ErrorCodes.INVALID_USERNAME).build();
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
            if(fromJson !=null  && (rh.isNullorEmpty(fromJson.getChannel()) || "NA".equals(fromJson.getChannel())))
            {
               return Response.status(ErrorCodes.INVALID_CHANNEL).build();
              
            }
            */
            ProfileSync doSync = doVerifyV2(fromJson);
           
            LOGGER.info(" -##- doVerify  --@- "+doSync);
            
            if(doSync != null)
            {
              
                 LOGGER.info(" @- doSync  doSync.toJson( --@ "+doSync.toJson());
                  
                String issuedToken = tokenHelper.issueToken(doSync,fromJson.getPrincipal(),fromJson.getPrincipalControlCode(),  uriInfo);  
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
    
    public Response doVerifyV2(@Valid  final JsonObject json) {
        LOGGER.info("--@@  - doVerifyV2 - "+json);
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
            if(fromJson !=null  && 1 > fromJson.getPid())
            {
                return Response.status(ErrorCodes.INVALID_USERNAME).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCode()))
            {
                return Response.status(ErrorCodes.INVALID_USER_CODE).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCodeLink()))
            {
               return Response.status(ErrorCodes.INVALID_USER_CODELINK).build();
            }
            if(fromJson !=null  && (rh.isNullorEmpty(fromJson.getChannel()) || "NA".equals(fromJson.getChannel())))
            {
               return Response.status(ErrorCodes.INVALID_CHANNEL).build();
              
            }
            
            ProfileSync doSync = doVerifyV2(fromJson);
           
            LOGGER.info(" -##- doVerify  --@- "+doSync);
            
            if(doSync != null)
            {
              
                 LOGGER.info(" @- doSync  doSync.toJson( --@ "+doSync.toJson());
                  
                String issuedToken = tokenHelper.issueToken(doSync,fromJson.getPrincipal(),fromJson.getPrincipalControlCode(),  uriInfo);  
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
    
    public Response doVerifyByClient(@Valid final JsonObject json) {
        LOGGER.info("--@@  - doVerifyByClient - "+json);
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
            if(fromJson !=null  && 1 > fromJson.getPid())
            {
                return Response.status(ErrorCodes.INVALID_USERNAME).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCode()))
            {
                return Response.status(ErrorCodes.INVALID_USER_CODE).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCodeLink()))
            {
               return Response.status(ErrorCodes.INVALID_USER_CODELINK).build();
            }
            if(fromJson !=null  && (rh.isNullorEmpty(fromJson.getChannel()) || "NA".equals(fromJson.getChannel())))
            {
               return Response.status(ErrorCodes.INVALID_CHANNEL).build();
              
            }
            if(fromJson !=null  && (rh.isNullorEmpty(fromJson.getAppId()) || "NA".equals(fromJson.getAppId())))
            {
               return Response.status(ErrorCodes.INVALID_APP_ID).build();
            }
            
            ProfileSync doSync = doVerifyV3(fromJson);
            
            LOGGER.info(" -@- doVerify  --@- "+doSync);
            
            if(doSync != null)
            {
              
                  LOGGER.info(" @- doSync  doSync.toJson( --@ "+doSync.toJson());
                  
                String issuedToken = tokenHelper.issueToken(doSync,fromJson.getPrincipal(),fromJson.getPrincipalControlCode(),  uriInfo);  
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
    
    
    public Response doClearProfile(@Valid  final JsonObject json) {
        
        String resp = "";
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
        
            ProfileSyncOBJ fromJson = JsonbBuilder.create().fromJson(json.toString(), ProfileSyncOBJ.class);
            
            if(fromJson ==null)
            {
                 return Response.status(ErrorCodes.FORMAT_ERROR).build();
            }
           
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCode()))
            {
                return Response.status(ErrorCodes.INVALID_USER_CODE).build();
            }
           
           
            long doSync = doClearProfile(fromJson.getCode());
            LOGGER.info(" #-@- doVerify  --@-# "+doSync);
            if(doSync > 0)
            {
              
                         return Response.ok().build();
            
            }
            else
            {
                          return Response.status(ErrorCodes.INVALID_USER_CODE).build();//.entity(job.add("responseDesc", ErrorCodes.doErroDesc(ErrorCodes.INVALID_USER_OR_PASSWORD)).build()).build();

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
