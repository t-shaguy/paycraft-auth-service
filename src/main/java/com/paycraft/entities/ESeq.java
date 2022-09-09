/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraft.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
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
public class ESeq extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TID")
    public Long tid;
    @Column(name = "SEQ_CODE")
    public String seqCode;
    @Column(name = "LAST_SEQ")
    public BigInteger lastSeq;
    @Column(name = "LENGTH")
    public int length;

    public ESeq() {
    }

    public ESeq(Long tid) {
        this.tid = tid;
    }

    
    public static ESeq findBySeqCode(String code)
    {
        return find("seqCode", code).firstResult();
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
