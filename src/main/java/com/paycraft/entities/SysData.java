/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraft.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "sys_data")
@NamedQueries({
    @NamedQuery(name = SysData.ALL, query = "SELECT s FROM SysData s")})
public class SysData implements Serializable {
    
    public static final String ALL = "SysData.findAll";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TID")
    private Long tid;
    @Column(name = "PARAM_NAME")
    private String paramName;
    @Column(name = "PARAM_VALUE")
    private String paramValue;
    @Column(name = "CREATED_BY")
    private BigInteger createdBy;
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "AUTH_BY")
    private BigInteger authBy;
    @Column(name = "AUTH_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date authDate;
    @Column(name = "OPR_COMMENT")
    private String oprComment;
    @JoinColumn(name = "STS", referencedColumnName = "TID")
    @ManyToOne
    private ResourceStsInfo sts;

    public SysData() {
    }

    public SysData(Long tid) {
        this.tid = tid;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public BigInteger getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public BigInteger getAuthBy() {
        return authBy;
    }

    public void setAuthBy(BigInteger authBy) {
        this.authBy = authBy;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
    }

    public String getOprComment() {
        return oprComment;
    }

    public void setOprComment(String oprComment) {
        this.oprComment = oprComment;
    }

    public ResourceStsInfo getSts() {
        return sts;
    }

    public void setSts(ResourceStsInfo sts) {
        this.sts = sts;
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
        if (!(object instanceof SysData)) {
            return false;
        }
        SysData other = (SysData) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.paycraftsystems.digitalbank.auth.entities.SysData[ tid=" + tid + " ]";
    }
    
}
