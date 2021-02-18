/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraft.dto;

import com.paycraft.resources.ResourceHelper;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.json.Json;

/**
 *
 * @author root
 */

public class ClientsInfoObj extends ResourceHelper implements Serializable {
    

    private static final long serialVersionUID = 1L;
    private Long tid;
    private String ipAddress;
    private Boolean enforceIp;
    private BigInteger status;
    private long crBy;
    private String code;
    private String iv;
    private String cKey;
    private String clientName;
    private long upBy;
    private int tokenLifespanDays;
    private String partnerID;
    private String partnerCode;
    private Date lastresetDT;

    public ClientsInfoObj() {
    }

    public ClientsInfoObj(Long tid) {
        this.tid = tid;
    }

    public ClientsInfoObj(Long tid, long crBy, long upBy, int tokenLifespanDays, String partnerID, String partnerCode) {
        this.tid = tid;
        this.crBy = crBy;
        this.upBy = upBy;
        this.tokenLifespanDays = tokenLifespanDays;
        this.partnerID = partnerID;
        this.partnerCode = partnerCode;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Boolean getEnforceIp() {
        return enforceIp;
    }

    public void setEnforceIp(Boolean enforceIp) {
        this.enforceIp = enforceIp;
    }

    public BigInteger getStatus() {
        return status;
    }

    public void setStatus(BigInteger status) {
        this.status = status;
    }

    public long getCrBy() {
        return crBy;
    }

    public void setCrBy(long crBy) {
        this.crBy = crBy;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getCKey() {
        return cKey;
    }

    public void setCKey(String cKey) {
        this.cKey = cKey;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public long getUpBy() {
        return upBy;
    }

    public void setUpBy(long upBy) {
        this.upBy = upBy;
    }

    public int getTokenLifespanDays() {
        return tokenLifespanDays;
    }

    public void setTokenLifespanDays(int tokenLifespanDays) {
        this.tokenLifespanDays = tokenLifespanDays;
    }

    public String getPartnerID() {
        return partnerID;
    }

    public void setPartnerID(String partnerID) {
        this.partnerID = partnerID;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public Date getLastresetDT() {
        return lastresetDT;
    }

    public void setLastresetDT(Date lastresetDT) {
        this.lastresetDT = lastresetDT;
    }
    
    public static String toJson(ClientsInfoObj  obj)
    {
        
        return Json.createObjectBuilder()
                .add("tid",obj.getTid())
                .add("ux",toDefault(obj.getClientName()))
                .add("ip", toDefault(obj.getIpAddress()))
                .add("enforceIp", obj.getEnforceIp())
                .add("tokenExpiryDays", obj.getTokenLifespanDays())
                .add("iv", toDefault(obj.getIv()))
                .add("key", toDefault(obj.getCKey()))
                .add("code", toDefault(obj.getCode()))
                .add("partnerCode", toDefault(obj.partnerCode))
                .add("partnerId", toDefault(obj.partnerID))
                
                
       .build().toString();
       
    }
    
     public String toJson()
    {
        
        return Json.createObjectBuilder()
                .add("tid",this.tid)
                .add("ux",toDefault(this.clientName))
                .add("ip", toDefault(this.ipAddress))
                .add("enforceIp", this.enforceIp)
                .add("tokenExpiryDays", this.tokenLifespanDays)
                .add("iv", toDefault(this.iv))
                .add("key", toDefault(this.cKey))
                .add("code", toDefault(this.code))
                .add("partnerCode", toDefault(this.partnerCode))
                .add("partnerId", toDefault(this.partnerID))
                
       .build().toString();
       
    }

    @Override
    public String toString() {
        return "ClientsInfoObj{" + "tid=" + tid + ", ipAddress=" + ipAddress + ", enforceIp=" + enforceIp + ", status=" + status + ", crBy=" + crBy + ", code=" + code + ", iv=" + iv + ", cKey=" + cKey + ", clientName=" + clientName + ", upBy=" + upBy + ", tokenLifespanDays=" + tokenLifespanDays + ", partnerID=" + partnerID + ", partnerCode=" + partnerCode + ", lastresetDT=" + lastresetDT + '}';
    }

}
