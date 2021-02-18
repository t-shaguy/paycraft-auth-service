/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.mailer.resources;

import com.paycraftsystems.mailer.dto.MailObj;
import io.quarkus.mailer.Mail;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import io.quarkus.mailer.Mailer;

import io.quarkus.mailer.reactive.ReactiveMailer;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author root
 */

@ApplicationScoped
public class MailProcessor {
    
    
    @Inject
    Mailer mailer;

    @Inject
    ReactiveMailer reactiveMailer;
    
    
    @ConfigProperty(name="admin.mail.addy")
    String adminMail;
  

    public CompletionStage<Response> sendASimpleEmailAsync() {
        System.out.println(" called async mailer...... ");
        return reactiveMailer.send(
                Mail.withText("taysay_shaguy@yahoo.com", "FUNDDECK NOTIFICATION", "This is your transaction PIN and all that"),
                Mail.withText(adminMail, "FUNDDECK NOTIFICATION", "This is your transaction PIN and all that"))
                .subscribeAsCompletionStage()
                .thenApply(x -> Response.accepted().build());
    }
   
     public CompletionStage<Boolean> sendEmailAsync( MailObj mail) {
        System.out.println(" *** called async mailer...... ");
        
        
        /*
            MailObj mail = new MailObj();

            mail.setAmount("N"+payeeInfo.getAmount());
            mail.setMailto(payeeInfo.getEmail());
            mail.setPaymentDate(payeeInfo.getPaymentDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a")));
            mail.setPaymentReference(payeeInfo.getOurRef());
            mail.setPin(CUResponseObj !=null?CUResponseObj.getPin():"NA");
            mail.setReceiptNo(CUResponseObj !=null?CUResponseObj.getReceiptNo():"NA");
            mail.setReceipient(payeeInfo.getFullname());
            //mail.setOtherInfo(CUResponse.narrateCUResponse(CUResponseObj !=null?CUResponseObj.getStatus():"NA"));
           // mail.setSubject("CU PAYMENT NOTIFICATION");
            mail.setSubject("Covenant University PIN/RECEIPT Notification");
        */
        
        return reactiveMailer.send(
                Mail.withHtml(mail.getMailto(), mail.getSubject(), new MailTemplates().doWelcomeMail(mail)),
        Mail.withHtml(mail.getMailToCC(),mail.getSubject(), new MailTemplates().doWelcomeMail(mail)))
                .subscribeAsCompletionStage()
                .thenApply(x -> true);
    }
    
     
   public CompletionStage<Boolean> sendEmailAsync(String email, String payREef, String amount, String warningSub) {
        System.out.println(" *** called async mailer EX...... ");
        
        
            MailObj mail = new MailObj();

            mail.setAmount("N"+amount);
            mail.setMailto(email);
            mail.setEventDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a")));
           // mail.setPaymentReference(payREef);
            mail.setPin("NA");
            mail.setReceiptNo("NA");
            mail.setReceipient("Administrator");
            mail.setOtherInfo("SYSTEM DECLINED");
            mail.setSubject("ERROR NOTIFICATION");
        
        
        return reactiveMailer.send(
                Mail.withHtml(mail.getMailto(), mail.getSubject(), new MailTemplates().doMail(mail)))
                .subscribeAsCompletionStage()
                .thenApply(x -> true);
    }
   
    public boolean sendingMail(MailObj mail) {
        
        boolean sent = false;
        try 
        {
            /*
            MailObj mail = new MailObj();
            
            mail.setAmount(payeeInfo.getAmount());
          
            mail.setMailto(payeeInfo.getEmail());//payeeInfo.getEmail()
            mail.setPaymentDate(payeeInfo.getPaymentDate().toLocalDateTime().format(DateTimeFormatter.ISO_DATE));
            mail.setPaymentReference(payeeInfo.getOurRef());
            mail.setPin(CUResponseObj !=null?CUResponseObj.getPin():"NA");
            mail.setReceiptNo(CUResponseObj !=null?CUResponseObj.getReceiptNo():"NA");
            mail.setOtherInfo(CUResponse.narrateCUResponse(CUResponseObj !=null?CUResponseObj.getStatus():"NA"));
            mail.setReceipient(payeeInfo.getFullname());
            mail.setSubject("Covenant University PIN/RECEIPT Notification");//CU PAYMENT NOTIFICATION");
            */
            mailer.send(Mail.withHtml(mail.getMailto(), mail.getSubject(), new MailTemplates().doWelcomeMail(mail)),
                    Mail.withHtml(adminMail, mail.getSubject(), new MailTemplates().doMail(mail)));
 
            sent = true;
         
        
        }
        catch (Exception e) {
        
             e.printStackTrace();
         
         }
       return sent;
    }
    
    
    public boolean doResetPasswordMail(MailObj mail) {
        
        boolean sent = false;
        try 
        {
          
            mailer.send(Mail.withHtml(mail.getMailto(), mail.getSubject(), new MailTemplates().doResetMail(mail)),
                    Mail.withHtml(adminMail, mail.getSubject(), new MailTemplates().doResetMail(mail)));
 
            sent = true;
         
        
        }
        catch (Exception e) {
        
             e.printStackTrace();
         
         }
       return sent;
    }
    
    
    public boolean doDefaultPasswordMail(MailObj mail) {
        
        boolean sent = false;
        try 
        {
            mailer.send(Mail.withHtml(mail.getMailto(), mail.getSubject(), new MailTemplates().doDefaultPasswordMail(mail)),
                    Mail.withHtml(adminMail, mail.getSubject(), new MailTemplates().doDefaultPasswordMail(mail)));
 
            sent = true;
         
        
        }
        catch (Exception e) {
        
             e.printStackTrace();
         
         }
       return sent;
    }
    
}
