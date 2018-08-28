package com.vin.all.persistence.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by vineetsinha on 8/18/18.
 */
@Data
@Entity
@Table(name="bid_audit_log")
@ToString(exclude="bid")
public class BidAuditLog implements Serializable {

    @Id
    @Column(name = "BID_AUD_ID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer bidAuditId;

    @Column(name = "REC_SRC_CDE")
    private String recSource;

    @Column(name = "ADD_BY_USER")
    private String addByUser;

    @Column(name = "REC_ADD_DTTM")
    private Timestamp recordAddTime;

    @Column(name = "UPD_BY_USER")
    private String updatedByUser;

    @Column(name = "REC_UPD_DTTM")
    private Timestamp recordUpdatedTime;

    //bi-directional many-to-one association to Bids
    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="BID_ID" )
    private Bids bid;

}
