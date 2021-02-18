/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraft.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author root
 */
@Entity
@Table(name = "e_seq")
@NamedQueries({
    @NamedQuery(name = "ESeq.findAll", query = "SELECT e FROM ESeq e"),
    @NamedQuery(name = "ESeq.findByTid", query = "SELECT e FROM ESeq e WHERE e.tid = :tid"),
    @NamedQuery(name = "ESeq.findBySeqCode", query = "SELECT e FROM ESeq e WHERE e.seqCode = :seqCode"),
    @NamedQuery(name = "ESeq.findByLastSeq", query = "SELECT e FROM ESeq e WHERE e.lastSeq = :lastSeq"),
    @NamedQuery(name = "ESeq.findByLength", query = "SELECT e FROM ESeq e WHERE e.length = :length")})
public class ESeq implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TID")
    private Long tid;
    @Column(name = "SEQ_CODE")
    private String seqCode;
    @Column(name = "LAST_SEQ")
    private BigInteger lastSeq;
    @Column(name = "LENGTH")
    private Integer length;

    public ESeq() {
    }

    public ESeq(Long tid) {
        this.tid = tid;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public String getSeqCode() {
        return seqCode;
    }

    public void setSeqCode(String seqCode) {
        this.seqCode = seqCode;
    }

    public BigInteger getLastSeq() {
        return lastSeq;
    }

    public void setLastSeq(BigInteger lastSeq) {
        this.lastSeq = lastSeq;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
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
        if (!(object instanceof ESeq)) {
            return false;
        }
        ESeq other = (ESeq) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.paycraftsystems.digitalbank.auth.entities.ESeq[ tid=" + tid + " ]";
    }
    
}
