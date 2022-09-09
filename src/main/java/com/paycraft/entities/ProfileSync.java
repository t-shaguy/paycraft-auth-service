/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraft.entities;

import com.paycraft.resources.ResourceHelper;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author root
 */
@Entity
@Table(name = "profile_sync")
@NamedQueries({
    @NamedQuery(name = ProfileSync.ALL, query = "SELECT p FROM ProfileSync p"),
    @NamedQuery(name =  ProfileSync.BY_CODE, query = "SELECT p FROM ProfileSync p WHERE p.code = :passed and p.codeLink = :passed2"),
    @NamedQuery(name =  ProfileSync.BY_CODE_AND_HASH, query = "SELECT p FROM ProfileSync p WHERE p.code = :passed and p.codeLink = :passed2 and p.vHash = :passed3"),
    @NamedQuery(name =  ProfileSync.BY_CODE_AND_HASH2, query = "SELECT p FROM ProfileSync p WHERE p.code = :passed and p.codeLink = :passed2 and p.vHash2 = :passed3"),
    @NamedQuery(name =  ProfileSync.BY_CODE_AND_TXP_HASH2, query = "SELECT p FROM ProfileSync p WHERE p.code = :passed and p.vHash2 = :passed2"),
    @NamedQuery(name =  ProfileSync.BY_CODE_OR_MSISDN_AND_TXP_HASH2, query = "SELECT p FROM ProfileSync p WHERE (p.code = :passed or p.msisdn = :passed)   and p.vHash2 = :passed2"),
})
public class ProfileSync implements Serializable {
    
    private static Logger LOGGER =  LoggerFactory.getLogger(ProfileSync.class);
    
    public static final String ALL = "ProfileSync.findAll";
    public static final String BY_CODE = "ProfileSync.findByCodeNLink";
    public static final String BY_CODE_AND_HASH = "ProfileSync.findByCodeNHash";
    public static final String BY_CODE_AND_HASH2 = "ProfileSync.findByCodeNHash2";
    public static final String BY_CODE_AND_TXP_HASH2 = "ProfileSync.findByCodeNTxpHash2";
    public static final String BY_CODE_OR_MSISDN_AND_TXP_HASH2 = "ProfileSync.findByCodeOrMSISDNTxpHash2";
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tid")
    private Long tid;
    @Basic(optional = false)
    @Column(name = "code")
    private String code;
    
    @Column(name = "msisdn")
    private String msisdn;
    
    @Column(name = "code_link")
    private String codeLink;
    
    @Column(name = "user_code")
    private String userCode;
    
    @Column(name = "pid")
    private long pid;
    
    @Basic(optional = false)
    @Lob
    @Column(name = "v_hash")
    private String vHash;
    
    @Basic(optional = false)
    @Lob
    @Column(name = "v_hash_1")
    private String vHash1;
    
    @Basic(optional = false)
    @Lob
    @Column(name = "v_hash_2")
    private String vHash2;
    
    @Column(name = "control_code")
    private String controlCode;
    
    
    @Column(name = "class")
    private BigInteger class1;
    @OneToMany(mappedBy = "profileLink", fetch = FetchType.EAGER)
    private List<ProfileAudit> profileAuditList;
    
    @Column(name = "sync_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date syncDate;
    
    @Column(name = "px_change_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pxChangeDate;
    
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    
    @Column(name = "last_access_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAccessDate;

    public ProfileSync() {
    }

    public ProfileSync(Long tid) {
        this.tid = tid;
    }

    public ProfileSync(Long tid, String code, String vHash, String vHash1) {
        this.tid = tid;
        this.code = code;
        this.vHash = vHash;
        this.vHash1 = vHash1;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

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

    public String getVHash() {
        return vHash;
    }

    public void setVHash(String vHash) {
        this.vHash = vHash;
    }

    public String getVHash1() {
        return vHash1;
    }

    public void setVHash1(String vHash1) {
        this.vHash1 = vHash1;
    }

    public BigInteger getClass1() {
        return class1;
    }

    public void setClass1(BigInteger class1) {
        this.class1 = class1;
    }

    public List<ProfileAudit> getProfileAuditList() {
        return profileAuditList;
    }

    public void setProfileAuditList(List<ProfileAudit> profileAuditList) {
        this.profileAuditList = profileAuditList;
    }

    public Date getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(Date syncDate) {
        this.syncDate = syncDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Date getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(Date lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
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

    public String getVHash2() {
        return vHash2;
    }

    public void setVHash2(String vHash2) {
        this.vHash2 = vHash2;
    }

    public Date getPxChangeDate() {
        return pxChangeDate;
    }

    public void setPxChangeDate(Date pxChangeDate) {
        this.pxChangeDate = pxChangeDate;
    }
    
    
    public JsonObject toJson() {
        ResourceHelper rh = new ResourceHelper();
        JsonObjectBuilder  job = Json.createObjectBuilder();
        try 
        {
              job.add("tid",this.tid)
                 .add("pid",this.pid)
                 .add("code",rh.toDefault(this.code))
                 .add("userCode",rh.toDefault(this.userCode))
                 .add("msisdn",rh.toDefault(this.msisdn))
                 .add("codeLink",rh.toDefault(this.codeLink))
                 .add("vHash1",rh.toDefault(this.vHash1))
                 .add("vHash",rh.toDefault(this.vHash))
                 .add("syncDate",rh.toDefault(this.syncDate))
                 .add("controlCode",rh.toDefault(this.controlCode))
                 .add("lastLoginDate",rh.toDefault(getLastAccessDate()))
                 .add("modifiedDate",rh.toDefault(this.modifiedDate));
            
        } catch (Exception e) {
        
           // e.printStackTrace();
            
            LOGGER.error(" --  Exception toJson() --", e);
        
        }
        
    return job.build();
    }
    
     public JsonObject toJson(String respDesc) {
        ResourceHelper rh = new ResourceHelper();
        JsonObjectBuilder  job = Json.createObjectBuilder();
        try 
        {
            
              job.add("tid",this.tid)
                 .add("pid",this.pid)
                 .add("code",rh.toDefault(this.code))
                 .add("userCode",rh.toDefault(this.userCode))
                 .add("msisdn",rh.toDefault(this.msisdn))
                 .add("codeLink",rh.toDefault(this.codeLink))
                 .add("vHash1",rh.toDefault(this.vHash1))
                 .add("vHash",rh.toDefault(this.vHash))
                 .add("responseDesc",rh.toDefault(respDesc))
                 .add("syncDate",rh.toDefault(this.syncDate))
                 .add("controlCode",rh.toDefault(this.controlCode))
                 .add("lastLoginDate",rh.toDefault(getLastAccessDate()))
                 .add("modifiedDate",rh.toDefault(this.modifiedDate));
            
        } catch (Exception e) {
        
           // e.printStackTrace();
            
            LOGGER.error(" --  Exception toJson(--) --", e);
        
        }
        
    return job.build();
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
        if (!(object instanceof ProfileSync)) {
            return false;
        }
        ProfileSync other = (ProfileSync) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProfileSync{" + "tid=" + tid + ", code=" + code + ", msisdn=" + msisdn + ", codeLink=" + codeLink + ", userCode=" + userCode + ", pid=" + pid + ", vHash=" + vHash + ", vHash1=" + vHash1 + ", vHash2=" + vHash2 + ", controlCode=" + controlCode + ", class1=" + class1 + ", profileAuditList=" + profileAuditList + ", syncDate=" + syncDate + ", pxChangeDate=" + pxChangeDate + ", modifiedDate=" + modifiedDate + ", lastAccessDate=" + lastAccessDate + '}';
    }

}
