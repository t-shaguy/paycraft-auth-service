/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraft.controller;

import io.quarkus.scheduler.Scheduled;
import java.time.LocalDateTime;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author root
 */
@ApplicationScoped
public class TokenLifespanNotification {
    
    
    @ConfigProperty(name="reset.notification.cron")
    String resetCron;
    
    
    @Inject
    ClientInfoHelper clientHelper;
    
    
   // reset.notification.cron = 0 0/5 15 * * ?
    
    
    @Scheduled(cron = " 0 0/7 * * * ?")
    public void doReminderx() {
        
        try 
        {
           // System.out.println("this = doReminder  XX " + LocalDateTime.now());
        } 
        catch (Exception e) {
        
            e.printStackTrace();
        
        }
        
    }
    
    
    @Scheduled(cron = "{reset.notification.cron}")
    public void doReminder() {
        
        try 
        {
            //System.out.println("this = doReminder resetCron  " + LocalDateTime.now());
        } 
        catch (Exception e) {
        
            e.printStackTrace();
        
        }
        
    }
    
    
     
    //@Scheduled(every = "60s")
    public void doReminderRaw() {
        
        try 
        {
            System.out.println("this = doReminderRaw  " + LocalDateTime.now());
        } 
        catch (Exception e) {
        
            e.printStackTrace();
        
        }
        
    }
    
    
    
    
    
    
    
    
}
