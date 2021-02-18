/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraft.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author root
 */
@Entity
@Table(name = "resource_sts_info")
@NamedQueries({
    @NamedQuery(name = "ResourceStsInfo.findAll", query = "SELECT r FROM ResourceStsInfo r"),
    @NamedQuery(name = "ResourceStsInfo.findByTid", query = "SELECT r FROM ResourceStsInfo r WHERE r.tid = :tid"),
    @NamedQuery(name = "ResourceStsInfo.findByStsDesc", query = "SELECT r FROM ResourceStsInfo r WHERE r.stsDesc = :stsDesc"),
    @NamedQuery(name = "ResourceStsInfo.findByCreateDate", query = "SELECT r FROM ResourceStsInfo r WHERE r.createDate = :createDate"),
    @NamedQuery(name = "ResourceStsInfo.findByCreatedBy", query = "SELECT r FROM ResourceStsInfo r WHERE r.createdBy = :createdBy"),
    @NamedQuery(name = "ResourceStsInfo.findByUpdatedBy", query = "SELECT r FROM ResourceStsInfo r WHERE r.updatedBy = :updatedBy"),
    @NamedQuery(name = "ResourceStsInfo.findByUpdatedDate", query = "SELECT r FROM ResourceStsInfo r WHERE r.updatedDate = :updatedDate")})
public class ResourceStsInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TID")
    private Long tid;
    @Column(name = "STS_DESC")
    private String stsDesc;
    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "CREATED_BY")
    private BigInteger createdBy;
    @Column(name = "UPDATED_BY")
    private BigInteger updatedBy;
    @Column(name = "UPDATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @OneToMany(mappedBy = "sts")
    private List<SysData> sysDataList;

    public ResourceStsInfo() {
    }

    public ResourceStsInfo(Long tid) {
        this.tid = tid;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public String getStsDesc() {
        return stsDesc;
    }

    public void setStsDesc(String stsDesc) {
        this.stsDesc = stsDesc;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public BigInteger getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
    }

    public BigInteger getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(BigInteger updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<SysData> getSysDataList() {
        return sysDataList;
    }

    public void setSysDataList(List<SysData> sysDataList) {
        this.sysDataList = sysDataList;
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
        if (!(object instanceof ResourceStsInfo)) {
            return false;
        }
        ResourceStsInfo other = (ResourceStsInfo) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.paycraftsystems.digitalbank.auth.entities.ResourceStsInfo[ tid=" + tid + " ]";
    }
    
}
