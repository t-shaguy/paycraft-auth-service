/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraft.controller;



import com.paycraft.entities.SysData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

/**
 *
 * @author taysayshaguy
 */

@ApplicationScoped
public class SysDataHelper
{
   
    @Inject
    EntityManager em;
   
   
    private List<SysData>  all;
    
    
    
    @PostConstruct
    @Transactional
    public List<SysData> doReadAll() {
        TypedQuery<SysData> query =  null;
        try 
        {
               query = em.createNamedQuery(SysData.ALL, SysData.class);
              
        } catch (Exception e) {
        
             e.printStackTrace();
             
             return new ArrayList<SysData>(){};
        }
        
      
     return (List<SysData>) query.getResultList();
    }
    
    
    public int countAll()
    {
        return doReadAll().size();
    }
    
    public int countAll(String query)
    {
        return em.createNamedQuery(query).getResultList().size();
    }
    
    
    public SysData doFind(Long  tid)
    {
        return em.find(SysData.class, tid);
    }
    
    
     /*
    
    public void doRemove(SysData  objx)
    {
        delete(objx.getTid());
    }
   
    public SysData getById(Long  requestId)
    {
        SysData resp = null;
        try
        {
              Map<String, Object> params = new HashMap<>();
              params.put("passed", requestId);
              resp = findOneResult(SysData.BY_ID, params);
        }
        catch(RuntimeException ex)
        { 
            ex.printStackTrace();
        }
       return resp;
    }
    
    
   
    public List<SysData>  ListAllByQuery(String createQueryName,  int start, int end)
    {
     return findWithNamedQuery( createQueryName, start,end);
    }
    
     public List<SysData>  ListAllByQueryWithOptions(String createQueryName,  int start, int end, Map<String, Object> params)
     {
        System.out.println(":SysData: ListAllByQueryWithOptions  : params "+params);
        List<SysData> objs = null;
        try
        {
              objs = em.findWithNamedQuery(createQueryName, params , 0) ;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
     return objs;
    }
    */
    
    public SysData syncObj(SysData  vcode)
    {
        SysData resp = null;
        try
        {
           resp  = em.merge(vcode);
           
        }
        catch(Exception ex)
        {  
            ex.printStackTrace();
        }
       return resp;
    }
    
    
    public SysData createObj(SysData  objx)
    {
        SysData resp = null;
        try
        {
           resp  = em.merge(objx);
           
        }
        catch(Exception ex)
        {  
            ex.printStackTrace();
        }
       return resp;
    }
    

    public List<SysData> getAll() {
        return all;
    }

    public void setAll(List<SysData> all) {
        this.all = all;
    }
    
    public String getProps(final List<SysData> props, final String paraname)
   {      
       
       SysData orElse = props.stream().filter(a-> paraname.trim().equals(a.getParamName())).findFirst().orElse(null);
       
       return (orElse == null)?"NA":orElse.getParamValue();
   }
  
   public String getProps(final String paraname)
   {      
       
       SysData orElse = doReadAll().stream().filter(a-> paraname.trim().equals(a.getParamName())).findFirst().orElse(null);
       
       return (orElse == null)?"NA":orElse.getParamValue();
   }
   
   public String getProps(final String paraname, String orDefaultTo)
   {      
       
       SysData orElse = doReadAll().stream().filter(a-> paraname.trim().equals(a.getParamName())).findFirst().orElse(null);
       
       return (orElse == null)?orDefaultTo:orElse.getParamValue();
   }
    
    
}
