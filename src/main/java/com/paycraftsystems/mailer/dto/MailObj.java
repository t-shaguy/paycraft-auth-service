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
public class MailObj {
    
    private String mailto;
    private String mailToCC;
    private String pin;
    private String eventDate;
    private String defaultPassword;
    private String receiptNo;
    private String amount;
    private String receipient;
    private String subject;
    private String otherInfo;
    private String verificationLink;
    private String roleName;

    public String getMailto() {
        return mailto;
    }

    public void setMailto(String mailto) {
        this.mailto = mailto;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String paymentDate) {
        this.eventDate = paymentDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReceipient() {
        return receipient;
    }

    public void setReceipient(String receipient) {
        this.receipient = receipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    public String getVerificationLink() {
        return verificationLink;
    }

    public void setVerificationLink(String verificationLink) {
        this.verificationLink = verificationLink;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getMailToCC() {
        return mailToCC;
    }

    public void setMailToCC(String mailToCC) {
        this.mailToCC = mailToCC;
    }
    
    

    @Override
    public String toString() {
        return "MailObj{" + "mailto=" + mailto + ", pin=" + pin + ", eventDate=" + eventDate + ", defaultPassword=" + defaultPassword + ", receiptNo=" + receiptNo + ", amount=" + amount + ", receipient=" + receipient + ", subject=" + subject + ", otherInfo=" + otherInfo + ", verificationLink=" + verificationLink +", roleName = "+roleName+ '}';
    }

}
