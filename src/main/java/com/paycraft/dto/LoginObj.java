/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraft.dto;

/**
 *
 * @author taysayshaguy
 */
public class LoginObj {
    
    
    private String ux;
    private String iv;
    private String key;

    public LoginObj() {
    }

    public LoginObj(String user, String password, String randomTok) {
        this.ux = user;
        this.iv = password;
        this.key = randomTok;
    }
    
    public String getUx() {
        return ux;
    }

    public void setUx(String user) {
        this.ux = user;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "LoginObj{" + "ux=" + ux + ", iv=" + iv + ", key=" + key + '}';
    }

}
