/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraft.controller;



import com.paycraft.dto.LoginObj;
import com.paycraft.entities.ClientsInfo;
import com.paycraft.entities.ProfileSync;
import com.paycraft.resources.AESCrypter;
import com.paycraft.resources.KeyGenerator;
import com.paycraft.resources.SecException;
import com.paycraft.resources.ServiceException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import org.slf4j.LoggerFactory;

/**
 *
 * @author taysayshaguy
 */
@Singleton
public class TokenHelper 
{
    private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TokenHelper.class);
    
    @Context
    private UriInfo uriInfo;
    
    @Inject
    KeyGenerator keyGenerator;
    
    @Inject
    SysDataHelper sysPropsController;
    
    
    
    //@Inject
    //Logger logger;
    
    
    public String issueToken(String login) {
        Key key = keyGenerator.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusHours(24))) //.plusMinutes(15L)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        LOGGER.info("#### generating token for a key : " + jwtToken + " - " + key);
        return jwtToken;

    }
    
    public String issueToken(String login, UriInfo uriInfo) {
        Key key = keyGenerator.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusHours(24))) //.plusMinutes(15L)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        LOGGER.info("#### generating token for a key : " + jwtToken + " - " + key);
        return jwtToken;

    }
    
    public String issueToken(ClientsInfo cinfo, UriInfo uriInfo, SysDataHelper sysPropsController) throws ServiceException, SecException 
    {
        Key key = keyGenerator.generateKey(sysPropsController.getProps("SYS_IV"), sysPropsController.getProps("DS_KEY"));
        String jwtToken = Jwts.builder()
                .setSubject(new AESCrypter(sysPropsController.getProps("DS_KEY"),sysPropsController.getProps("SYS_IV")).encrypt(ClientsInfo.toJson(cinfo))) 
                .setIssuer("")//uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusDays(cinfo.getTokenLifespanDays()))) //.plusMinutes(15L)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        LOGGER.info("#### generating token for a key : " + jwtToken + " - " + key);
        return jwtToken;

    }
    
    public String issueToken(ClientsInfo cinfo, LoginObj doLoginObj, UriInfo uriInfo) throws ServiceException, SecException 
    {
        LOGGER.info(" --  ############ uriInfo = " + cinfo);
        System.out.println("doLoginObj = " + doLoginObj);
         String jwtToken =  null;
        try 
        {
             Key key = keyGenerator.generateKey(sysPropsController.getProps("SYS_IV"), sysPropsController.getProps("SYS_KEY"));
             
               System.out.println("issueToken sub checkes "+doLoginObj+" =to pass # " + ClientsInfo.toJson(cinfo, doLoginObj));
               System.out.println("TOKEN NEXT XPRIRARTION = " + LocalDateTime.now().plusDays(cinfo.getTokenLifespanDays()));
               System.out.println(" = +++++++++B4 Credz  = " + ClientsInfo.toJson(cinfo, doLoginObj) );
               jwtToken = Jwts.builder()
                .setSubject(new AESCrypter(sysPropsController.getProps("SYS_KEY"),sysPropsController.getProps("SYS_IV")).encrypt(ClientsInfo.toJson(cinfo, doLoginObj))) 
                .setIssuer("")//uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                
                .setExpiration(toDate(LocalDateTime.now().plusDays(cinfo.getTokenLifespanDays()))) //.plusMinutes(15L)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        LOGGER.info("#### generating token for a key : " + jwtToken + " - " + key);
            
        }
        catch (NullPointerException e) {
       
            //e.printStackTrace();
            
           LOGGER.info("   ############ NullPointerException issueToken = ",e);
        
        }
        catch (Exception e) {
       
            //e.printStackTrace();
            
           LOGGER.info("   ############ Exception issueToken = ",e);
        
        }
       
        return jwtToken;

    }
    
    public String issueToken(ClientsInfo cinfo, UriInfo uriInfo) throws ServiceException, SecException 
    {
        LOGGER.info(" --  ############ uriInfo = " + cinfo);
         String jwtToken =  null;
        try 
        {
             Key key = keyGenerator.generateKey(sysPropsController.getProps("SYS_IV"), sysPropsController.getProps("SYS_KEY"));
             jwtToken = Jwts.builder()
                .setSubject(new AESCrypter(sysPropsController.getProps("SYS_KEY"),sysPropsController.getProps("SYS_IV")).encrypt(ClientsInfo.toJson(cinfo))) 
                .setIssuer("")//uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusDays(cinfo.getTokenLifespanDays()))) //.plusMinutes(15L)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        LOGGER.info("#### generating token for a key : " + jwtToken + " - " + key);
            
        }
        catch (NullPointerException e) {
       
            //e.printStackTrace();
            
           LOGGER.info("   ############ NullPointerException issueToken = ",e);
        
        }
        catch (Exception e) {
       
            //e.printStackTrace();
            
           LOGGER.info("   ############ Exception issueToken = ",e);
        
        }
       
        return jwtToken;

    }
    
    public String issueToken(ProfileSync profile, UriInfo uriInfo) throws ServiceException, SecException 
    {
        LOGGER.info(" $$ @@@ ############ uriInfo = " + profile);
         String jwtToken =  null;
        try 
        {
             Key key = keyGenerator.generateKey(sysPropsController.getProps("SYS_IV"), sysPropsController.getProps("SYS_KEY"));
             jwtToken = Jwts.builder()
                .setSubject(new AESCrypter(sysPropsController.getProps("SYS_KEY"),sysPropsController.getProps("SYS_IV")).encrypt(profile.toJson().toString())) 
                .setIssuer("")//uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusDays(1))) //.plusMinutes(15L)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        LOGGER.info("-->#### generating token for a key : " + jwtToken + " - " + key);
            
        } catch (Exception e) {
       
            //e.printStackTrace();
            
            LOGGER.info(" --  ############ Exception issueToken = ",e);
        
        
        }
       
        return jwtToken;

    }
    
    public String issueToken(ProfileSync profile,String principal, String principalControl, UriInfo uriInfo) throws ServiceException, SecException 
    {
        LOGGER.info(" $$ @@@ ############ uriInfo = " + profile);
         String jwtToken =  null;
        try 
        {
             Key key = keyGenerator.generateKey(sysPropsController.getProps("SYS_IV"), sysPropsController.getProps("SYS_KEY"));
             jwtToken = Jwts.builder()
                .setSubject(new AESCrypter(sysPropsController.getProps("SYS_KEY"),sysPropsController.getProps("SYS_IV")).encrypt(profile.toJson(principal, principalControl).toString()))
                .setIssuer("")//uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusDays(1))) //.plusMinutes(15L)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        LOGGER.info("-->#### generating token for a key : " + jwtToken + " - " + key);
            
        } catch (Exception e) {
       
            //e.printStackTrace();
            
            LOGGER.info(" --  ############ Exception issueToken = ",e);
        
        
        }
       
        return jwtToken;

    }
    
    public String issueToken(String login, String password) {
        Key key = keyGenerator.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        LOGGER.info("#### generating token for a key : " + jwtToken + " - " + key);
        return jwtToken;

    }
    
    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
    
}
