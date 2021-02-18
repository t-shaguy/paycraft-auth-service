/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.mailer.dto;

/**
 *
 * @author root
 */
public class MailParams {
    
    
  public String notificationType;
  public String recepient;
  public String copyTo;
  public String addressee;
  public String subject;
  public String date;
  public String message; 
  public String shortMessage;
  public String dashboardUrl;

    @Override
    public String toString() {
        return "MailParams{" + "notificationType=" + notificationType + ", recepient=" + recepient + ", copyTo=" + copyTo + ", addressee=" + addressee + ", subject=" + subject + ", date=" + date + ", message=" + message + ", shortMessage=" + shortMessage + ", dashboardUrl = "+dashboardUrl+'}';
    }
  
    
}
