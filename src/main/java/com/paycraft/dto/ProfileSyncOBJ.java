/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraft.dto;

/**
 *
 * @author root
 */
public class ProfileSyncOBJ {
    
    
    private String code;
    private String codeLink;
    private String password;
    private String verifyPassword;
    private String pin;
    private String verifyPin;
    private String newPassword;
    private String newPin;
    private String msisdn;
    private String userCode;
    private String channel;
    private long pid;
    private String controlCode;
    private String principal;
    private String principalControlCode;
    private String appId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeLink() {
        return codeLink;
    }

    public void setCodeLink(String codeLink) {
        this.codeLink = codeLink;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifypassword) {
        this.verifyPassword = verifypassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getControlCode() {
        return controlCode;
    }

    public void setControlCode(String controlCode) {
        this.controlCode = controlCode;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getVerifyPin() {
        return verifyPin;
    }

    public void setVerifyPin(String verifyPin) {
        this.verifyPin = verifyPin;
    }

    public String getNewPin() {
        return newPin;
    }

    public void setNewPin(String newPin) {
        this.newPin = newPin;
    }

    public String getPrincipalControlCode() {
        return principalControlCode;
    }

    public void setPrincipalControlCode(String principalControlCode) {
        this.principalControlCode = principalControlCode;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return "ProfileSyncOBJ{" + "code=" + code + ", codeLink=" + codeLink + ", password=" + password + ", verifyPassword=" + verifyPassword + ", pin=" + pin + ", verifyPin=" + verifyPin + ", newPassword=" + newPassword + ", newPin=" + newPin + ", msisdn=" + msisdn + ", userCode=" + userCode + ", channel=" + channel + ", pid=" + pid + ", controlCode=" + controlCode + ", principal=" + principal + ", principalControlCode=" + principalControlCode + ", appId=" + appId + '}';
    }

}
