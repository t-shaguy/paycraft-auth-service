/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraft.entities;

import com.paycraft.dto.LoginObj;
import com.paycraft.resources.ResourceHelper;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author root
 */
@Entity
@Table(name = "clients_info")
@NamedQueries({
    @NamedQuery(name = ClientsInfo.ALL, query = "SELECT c FROM ClientsInfo c"),
    @NamedQuery(name = ClientsInfo.BY_CODE, query = "SELECT c FROM ClientsInfo c WHERE c.code = :passed"),
    @NamedQuery(name = ClientsInfo.BY_CLIENT_NAME, query = "SELECT c FROM ClientsInfo c WHERE c.clientName = :passed"),
    @NamedQuery(name = ClientsInfo.BY_CLIENT_NAME_AND_CODE, query = "SELECT c FROM ClientsInfo c WHERE c.clientName = :passed and c.code =:passed2"),
    @NamedQuery(name = ClientsInfo.BY_CLIENT_NAME_AND_PARTNER_ID, query = "SELECT c FROM ClientsInfo c WHERE c.clientName = :passed and c.partnerID =:passed2"),
    @NamedQuery(name = ClientsInfo.BY_CLIENT_NAME_AND_PARTNER_CODE, query = "SELECT c FROM ClientsInfo c WHERE c.clientName = :passed and c.partnerCode =:passed2"),
   @NamedQuery(name = ClientsInfo.BY_CLIENT_NAME_OR_PARTNER_ID, query = "SELECT c FROM ClientsInfo c WHERE c.clientName = :passed or c.partnerID =:passed2"),
    @NamedQuery(name = ClientsInfo.BY_PARTNER_CODE, query = "SELECT c FROM ClientsInfo c WHERE  c.partnerCode =:passed"),
    @NamedQuery(name = ClientsInfo.BY_CREDZ, query = "SELECT c FROM ClientsInfo c WHERE c.clientName = :passed and c.iv = :passed2  and c.cKey = :passed3 and c.status = 1 ")
})
public class ClientsInfo extends ResourceHelper implements Serializable {
    
    public static final String ALL = "ClientsInfo.findAll";
    public static final String BY_CREDZ = "ClientsInfo.findByCredz";
    public static final String BY_CODE = "ClientsInfo.findByCode";
    public static final String BY_CLIENT_NAME = "ClientsInfo.findByClientName";
    public static final String BY_CLIENT_NAME_AND_CODE = "ClientsInfo.findByClientNameAndCode";
    public static final String BY_CLIENT_NAME_AND_PARTNER_ID = "ClientsInfo.findByClientNameAndPartnerId";
    public static final String BY_CLIENT_NAME_AND_PARTNER_CODE = "ClientsInfo.findByClientNameAndPartnerCode";
    
    public static final String BY_PARTNER_CODE = "ClientsInfo.findByPartnerCode";
    public static final String BY_CLIENT_NAME_OR_PARTNER_ID = "ClientsInfo.findByClientNameOrPartnerId";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TID")
    private Long tid;
    @Column(name = "IP_ADDRESS")
    private String ipAddress;
    @Column(name = "ENFORCE_IP")
    private Boolean enforceIp;
    @Column(name = "STATUS")
    private BigInteger status;
    
    @Column(name="status_str")
    private String statusStr;
    
    @Column(name = "CR_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date crDt;
    @Basic(optional = false)
    @Column(name = "CR_BY")
    private long crBy;
    @Column(name = "UP_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date upDt;
    @Column(name = "CODE")
    private String code;
    @Column(name = "IV")
    private String iv;
    @Column(name = "C_KEY")
    private String cKey;
    @Column(name = "CLIENT_NAME")
    private String clientName;
    @Basic(optional = false)
    @Column(name = "UP_BY")
    private long upBy;
    @Basic(optional = false)
    @Column(name = "token_lifespan_days") 
    private int tokenLifespanDays;
    @Basic(optional = false)
    @Column(name = "partner_ID")
    private String partnerID;
    @Basic(optional = false)
    @Column(name = "partner_code")
    private String partnerCode;
    @Column(name = "last_reset_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastresetDT;
    @Basic(optional = false)
    @Column(name = "client_cat", columnDefinition="int(11) default 0")
    private Integer clientCategory;
    
    @Column(name="fee_code")
    private String feeCode;

    public ClientsInfo() {
    }

    public ClientsInfo(Long tid) {
        this.tid = tid;
    }

    public ClientsInfo(Long tid, long crBy, long upBy, int tokenLifespanDays, String partnerID, String partnerCode) {
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

    public Date getCrDt() {
        return crDt;
    }

    public void setCrDt(Date crDt) {
        this.crDt = crDt;
    }

    public long getCrBy() {
        return crBy;
    }

    public void setCrBy(long crBy) {
        this.crBy = crBy;
    }

    public Date getUpDt() {
        return upDt;
    }

    public void setUpDt(Date upDt) {
        this.upDt = upDt;
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

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public Integer getClientCategory() {
        return clientCategory;
    }

    public void setClientCategory(Integer clientCategory) {
        this.clientCategory = clientCategory;
    }
    
    
    
    public static String toJson(ClientsInfo  obj, LoginObj doLoginObj)
    {
        System.out.println("on jsons doLoginObj = " +doLoginObj);
        return Json.createObjectBuilder()
                .add("tid",obj.getTid())
                .add("clientName",toDefault(obj.getClientName()))
                .add("ip", toDefault(obj.getIpAddress()))
                .add("enforceIp", obj.getEnforceIp()==null?false:obj.getEnforceIp())
                .add("tokenLifespanDays", obj.getTokenLifespanDays())
                .add("iv", toDefault(obj.getIv()))
                .add("key", toDefault(obj.getCKey()))
                .add("code", toDefault(obj.getCode()))
                .add("categoryDesc", doCategoryDescription(obj.clientCategory==null?0:obj.clientCategory))
                .add("clientCategory", obj.clientCategory==null?0:obj.clientCategory)
                .add("partnerCode", toDefault(obj.partnerCode))
                .add("customerCode", toDefault(doLoginObj.customerCode))
                .add("statusStr", toDefault(obj.getStatusStr()))
                .add("providerId", doLoginObj.providerId)
                .add("providerStr", toDefault(doLoginObj.providerStr))
                .add("partnerId", toDefault(obj.partnerID))
                .add("apiUserCustomerName", toDefault(doLoginObj.apiUserCustomerName))
                .add("flwSecKey", toDefault(doLoginObj.flwSecKey))
                .add("flwPubKey", toDefault(doLoginObj.flwPubKey))
                .add("flwEncKey", toDefault(doLoginObj.flwEncKey))
                
       .build().toString();
       
    }
    
    public static String toJson(ClientsInfo  obj)
    {
        
        return Json.createObjectBuilder()
                .add("tid",obj.getTid())
                .add("clientName",toDefault(obj.getClientName()))
                .add("ip", toDefault(obj.getIpAddress()))
                .add("enforceIp", obj.getEnforceIp()==null?false:obj.getEnforceIp())
                .add("tokenLifespanDays", obj.getTokenLifespanDays())
                .add("iv", toDefault(obj.getIv()))
                .add("key", toDefault(obj.getCKey()))
                .add("code", toDefault(obj.getCode()))
                .add("categoryDesc", doCategoryDescription(obj.clientCategory))
                .add("clientCategory", obj.clientCategory==null?0:obj.clientCategory)
                .add("partnerCode", toDefault(obj.partnerCode))
                .add("statusStr", toDefault(obj.getStatusStr()))
                .add("partnerId", toDefault(obj.partnerID))
                
                
       .build().toString();
       
    }
    
     public JsonObject toJson()
    {
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
                job.add("tid",this.tid)
                .add("clientName",toDefault(this.clientName))
                .add("ipAddress", toDefault(this.ipAddress))
                .add("enforceIp", (this.enforceIp==null)?false:this.enforceIp)
                .add("tokenLifespanDays", this.tokenLifespanDays)
                .add("iv", toDefault(this.iv))
                .add("key", toDefault(this.cKey))
                .add("code", toDefault(this.code))
                .add("statusStr", toDefault(this.statusStr))
               // .add("categoryDesc", doCategoryDescription(this.clientCategory)) 
               // .add("clientCategory", this.clientCategory)
                .add("partnerCode", toDefault(this.partnerCode))
                .add("partnerId", toDefault(this.partnerID));
            
        } 
        catch (Exception e) {
        }
      return job.build();
    }
     
    public JsonObject toJsonTrim()
    {
        JsonObjectBuilder job = Json.createObjectBuilder();
        
        try 
        {
             job.add("tid",this.tid)
                .add("clientName",toDefault(this.clientName))
                .add("ux",toDefault(this.clientName))
                .add("ipAddress", toDefault(this.ipAddress))
                .add("enforceIp", this.enforceIp==null?false:this.enforceIp)
                .add("tokenLifespanDays", this.tokenLifespanDays)
                .add("iv", (this.iv !=null && this.iv.trim().length() ==16)?"DATA-PRIVACY":"NA")
                .add("key", (this.cKey !=null && this.cKey.trim().length() ==16)?"DATA-PRIVACY":"NA")
                .add("code", toDefault(this.code))
                .add("lastResetDate", toDefault(this.lastresetDT))
                .add("partnerCode", toDefault(this.partnerCode))
                .add("feeCode", toDefault(this.feeCode))
                .add("statusStr", toDefault(this.statusStr))
                .add("categoryDesc", doCategoryDescription(this.clientCategory))
                .add("clientCategory", this.clientCategory)
                .add("partnerId", toDefault(this.partnerID));
            
        } catch (Exception e) {
       
        
            e.printStackTrace();
        }
        
        
        
       
                
       return job.build();//.toString();
       
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tid != null ? tid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientsInfo)) {
            return false;
        }
        ClientsInfo other = (ClientsInfo) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ClientsInfo{" + "tid=" + tid + ", ipAddress=" + ipAddress + ", enforceIp=" + enforceIp + ", status=" + status + ", statusStr=" + statusStr + ", crDt=" + crDt + ", crBy=" + crBy + ", upDt=" + upDt + ", code=" + code + ", iv=" + iv + ", cKey=" + cKey + ", clientName=" + clientName + ", upBy=" + upBy + ", tokenLifespanDays=" + tokenLifespanDays + ", partnerID=" + partnerID + ", partnerCode=" + partnerCode + ", lastresetDT=" + lastresetDT + ", clientCategory=" + clientCategory + ", feeCode=" + feeCode + '}';
    }

   
    
    
}
