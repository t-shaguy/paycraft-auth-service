/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraft.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "profile_audit")
@NamedQueries({
    @NamedQuery(name = "ProfileAudit.findAll", query = "SELECT p FROM ProfileAudit p"),
    @NamedQuery(name = "ProfileAudit.findByTid", query = "SELECT p FROM ProfileAudit p WHERE p.tid = :tid"),
    @NamedQuery(name = "ProfileAudit.findByAccountNo", query = "SELECT p FROM ProfileAudit p WHERE p.accountNo = :accountNo"),
    @NamedQuery(name = "ProfileAudit.findByActivityDate", query = "SELECT p FROM ProfileAudit p WHERE p.activityDate = :activityDate")})
public class ProfileAudit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tid")
    private Long tid;
    @Column(name = "accountNo")
    private String accountNo;
    @Lob
    @Column(name = "activity_info")
    private String activityInfo;
    @Column(name = "activity_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activityDate;
    @JoinColumn(name = "profile_link", referencedColumnName = "tid")
    @ManyToOne
    private ProfileSync profileLink;

    public ProfileAudit() {
    }

    public ProfileAudit(Long tid) {
        this.tid = tid;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getActivityInfo() {
        return activityInfo;
    }

    public void setActivityInfo(String activityInfo) {
        this.activityInfo = activityInfo;
    }

    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }

    public ProfileSync getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(ProfileSync profileLink) {
        this.profileLink = profileLink;
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
        if (!(object instanceof ProfileAudit)) {
            return false;
        }
        ProfileAudit other = (ProfileAudit) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.paycraftsystems.digitalbank.auth.entities.ProfileAudit[ tid=" + tid + " ]";
    }
    
}
