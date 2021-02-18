/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraft.controller;

import com.paycraft.dto.ClientsInfoObj;
import com.paycraft.entities.ClientsInfo;
import com.paycraftsystems.error.messages.ErrorCodes;
import com.paycraft.resources.KeyGenerator;
import com.paycraft.resources.ServiceException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tshaguy
 */
@ApplicationScoped
public class ClientInfoHelper {
    
    private static Logger LOGGER = LoggerFactory.getLogger(ClientInfoHelper.class);
    
    
    @Inject
    EntityManager em;
    
    @Inject
    KeyGenerator keyGenerator;
    
    @Inject
    SysDataHelper sysDataHelper;
    
    
    public ClientsInfo doFind(Long  tid)
    {
        return em.find(ClientsInfo.class,tid);
    }
    
    
    public void doRemove(ClientsInfo  objx)
    {
        em.remove(objx.getTid());
    }
    
   
    public ClientsInfo doLoadByCode(String code) {
        ClientsInfo obj =  null;
        try 
        {
               System.out.println("ClientsInfo = " + code);
               TypedQuery<ClientsInfo> query = em.createNamedQuery(ClientsInfo.BY_CODE, ClientsInfo.class).setParameter("passed", code);
                //System.out.println(" doLoadByCode query = " + query);
                List<ClientsInfo> resultList = query.getResultList();
                if(resultList != null && resultList.size() > 0)
                {
                     // obj = resultList.get(0);
                     return resultList.get(0);
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
    
    /*
    public Key getByClientKey(String userid) throws ServiceException, Exception
    {
        ClientsInfo resp = null;
        Key  k = null;
        
              System.out.println(": getByName : objid  "+userid);
              if(userid ==null) userid = "";
              Map<String, Object> params = new HashMap<>();
              params.put("passed", userid.trim());
              resp = em.createNamedQuery(ErrorCodes.INVALID_CLIENT, params);
        
              if(resp == null)
              {
                  throw new ServiceException(ErrorCodes.INVALID_CLIENT,"Invalid or Unknown Client");
              }
              else if(resp != null)
              {
                 k =  keyGenerator.generateKey(resp.getClientName(), resp.getIv(), resp.getCKey());
              }
              else
              {
                  throw new ServiceException(ResponseCodes.CLIENTKEY_RETRIEVAL_ERROR,"Could retrieve key ** ");
              }
              
              if(k == null)
              {
                  //System.out.println(" key is null ???? ");
                  throw new ServiceException(ResponseCodes.CLIENTKEY_RETRIEVAL_ERROR," Could retrieve key * ");
              }
              
     return k;
     }
    */
    
    @Transactional
    public ClientsInfo getByName(String userid) {
        ClientsInfo obj =  null;
        try 
        {
               if(userid == null ) userid ="";
               TypedQuery<ClientsInfo> query = em.createNamedQuery(ClientsInfo.BY_CLIENT_NAME, ClientsInfo.class).setParameter("passed", userid.trim());
                //System.out.println(" getByName query = " + query);
                List<ClientsInfo> resultList = query.getResultList();
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
    
    
    // @Transactional
    public List<ClientsInfo> doListAllClients() {
        List<ClientsInfo> resultList =  new ArrayList<>();
        try 
        {
                resultList = em.createNamedQuery(ClientsInfo.ALL, ClientsInfo.class).getResultList();
               
        
        } catch (Exception e) {
        
             e.printStackTrace();
             
              return null;
        }
     return resultList;
    }
    
    
    @Transactional
    public ClientsInfo getByName(String userid, String code) {
        ClientsInfo obj =  null;
        try 
        {
               if(userid == null ) userid ="";
               if(code == null ) code ="";
               TypedQuery<ClientsInfo> query = em.createNamedQuery(ClientsInfo.BY_CLIENT_NAME_AND_CODE, ClientsInfo.class).setParameter("passed", userid.trim()).setParameter("passed2", code.trim());
                //System.out.println(" getByName query = " + query);
                List<ClientsInfo> resultList = query.getResultList();
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
    public ClientsInfo getByCredentials(String userid,String iv  , String key) {
        ClientsInfo obj =  null;
        try 
        {
               /*
                px = iv
                rx = key
               */
               if(userid == null ) userid ="";
               if(iv == null ) iv ="";
               if(key == null ) key ="";
               TypedQuery<ClientsInfo> query = em.createNamedQuery(ClientsInfo.BY_CREDZ, ClientsInfo.class).setParameter("passed", userid.trim()).setParameter("passed2", iv.trim()).setParameter("passed3",  key.trim());
                //System.out.println(" doLoadByCode query = " + query);
                List<ClientsInfo> resultList = query.getResultList();
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
        
     
    /*  
    @Transactional
    public ClientsInfo getByName(String userid) throws ServiceException, Exception
    {
        ClientsInfo resp = null;
        
              System.out.println(": getByName : objid  "+userid);
              if(userid ==null) userid = "";
              Map<String, Object> params = new HashMap<>();
              params.put("passed", userid.trim());
              resp = em.createNamedQuery(ClientsInfo.BY_CLIENT_NAME).;
        
              if(resp == null)
              {
                  throw new ServiceException(ErrorCodes.INVALID_CLIENT,"Invalid or Unknown Client");
              }
              
       return resp;
    }
   
    public List<ClientsInfo>  ListAllByQuery(String createQueryName,  int start, int end)
    {
     return findWithNamedQuery( createQueryName, start,end);
    }
    
     public List<ClientsInfo>  ListAllByQueryWithOptions(String createQueryName,  int start, int end, Map<String, Object> params)
     {
        System.out.println(":RequestLog: ListAllByQueryWithOptions  : params "+params);
        List<ClientsInfo> objs = null;
        try
        {
              objs = findWithNamedQuery(createQueryName, params , 0) ;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
     return objs;
    }
    */
    
   
    
    @Transactional
    public ClientsInfo doSync(ClientsInfo  obj) throws ServiceException, Exception
    {
        ClientsInfo resp = null;
          
            ClientsInfo trxLog = getByName(obj.getClientName(), obj.getCode());
            if(trxLog !=null)
            {
                
               trxLog.setIpAddress(obj.getIpAddress());
               trxLog.setCode(obj.getCode());
               trxLog.setEnforceIp(obj.getEnforceIp());
               trxLog.setCKey(obj.getCKey());
               trxLog.setIv(obj.getIv());
               trxLog.setLastresetDT(new Date());
               if(obj.getTokenLifespanDays() > 0)
               {
                   trxLog.setTokenLifespanDays(obj.getTokenLifespanDays());
               }
               //trxLog.setUpBy(obj.getUpBy());
               trxLog.setStatus(obj.getStatus());
               resp  = em.merge(trxLog);
               
            }
           
       
             if(trxLog == null)
             {
                  throw new ServiceException(ErrorCodes.INVALID_CLIENT,"Invalid or Unknown Client");
             }
             
       return resp;
    }
    
    
    @Transactional
    public Response doLog(ClientsInfoObj  obj) throws ServiceException, Exception
    {
        ClientsInfo resp = null;
          
        
        try 
        {
            
       
            ClientsInfo trxLog = getByName(obj.getClientName(), obj.getCode());
            if(trxLog ==null)
            {
                
               ClientsInfo  newobj = new ClientsInfo();
               newobj.setIpAddress((obj.getIpAddress()==null || obj.getIpAddress().trim().equals(""))?"127.0.0.1":obj.getIpAddress());
               newobj.setCode(obj.getCode());
               newobj.setEnforceIp(obj.getEnforceIp());
               newobj.setClientName(obj.getClientName());
               if(obj.getTokenLifespanDays() > 0)
               {
                  newobj.setTokenLifespanDays(obj.getTokenLifespanDays());
               }
               else
               {
                   newobj.setTokenLifespanDays(Integer.parseInt(sysDataHelper.getProps("DEFAULT-TOKEN-AGE-IN-DAYS", "1")));
               }
               newobj.setCKey("NA");
               newobj.setIv("NA");
               newobj.setPartnerCode(obj.getCode());
               newobj.setPartnerID(obj.getCode());
               newobj.setEnforceIp(obj.getEnforceIp());
               //trxLog.setUpBy(obj.getUpBy());
               newobj.setStatus(obj.getStatus());
               resp  = em.merge(newobj);
               
               if(resp == null)
               {
                   return Response.status(ErrorCodes.DATABASE_ERROR).build();
               }
               else
               {
                    return Response.ok().entity(resp.toJson()).build();
               }
               
            }
            else
            {
                return Response.status(ErrorCodes.DUPLICATE_TRANSACTION).build();
            }
           
       
        } catch (Exception e) {
        
         
            e.printStackTrace();
            
             return Response.status(ErrorCodes.SYSTEM_ERROR).build();
        }
     
    }
    
    
    @Transactional
    public Response doSyncClient(ClientsInfoObj  obj) throws ServiceException, Exception
    {
        ClientsInfo resp = null;
          
        
        try 
        {
            
       
            ClientsInfo trxLog = getByName(obj.getClientName(), obj.getCode());
            if(trxLog !=null)
            {
                
               trxLog.setIpAddress(obj.getIpAddress());
               trxLog.setCode(obj.getCode());
               trxLog.setTokenLifespanDays(trxLog.getTokenLifespanDays());
               
               trxLog.setEnforceIp(obj.getEnforceIp());
               trxLog.setCKey("NA");
               trxLog.setIv("NA");
               //trxLog.setUpBy(obj.getUpBy());
               trxLog.setStatus(obj.getStatus());
               resp  = em.merge(trxLog);
               
               if(resp == null)
               {
                   return Response.status(ErrorCodes.DATABASE_ERROR).build();
               }
               else
               {
                    return Response.ok().entity(resp.toJson()).build();
               }
               
            }
            else
            {
                return Response.status(ErrorCodes.INVALID_CLIENT).build();
            }
           
       
        } catch (Exception e) {
        
         
            e.printStackTrace();
            
             return Response.status(ErrorCodes.SYSTEM_ERROR).build();
        }
     
    }
    
   
    
    @Transactional
    public ClientsInfo doLog(ClientsInfo  objx) throws javax.validation.ConstraintViolationException,Exception
    {
        ClientsInfo resp = null;
        
           resp  = em.merge(objx);
           
       
       return resp;
    }
    
    
    
    
    
}
