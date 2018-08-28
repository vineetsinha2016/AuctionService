package com.vin.all.persistence.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vineetsinha on 8/18/18.
 */
@Data
@Entity
@Table(name="bids")
public class Bids implements Serializable {

    @Id
    @Column(name = "BID_ID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer bidId;

    @Column(name = "MAX_AUTO_BID_AMT")
    private Long maxAutoBidAmount;

    @Column(name = "BIDDER_OUTBID_IND")
    private String outbidInd;

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

    //bi-directional many-to-one association to Bidders
    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="BIDDER_ID" )
    private Bidders bidder;

    //bi-directional many-to-one association to AuctionItems
    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="AUC_IDENT" )
    private AuctionItems auctionItem;

    //bi-directional many-to-one association to AuditLogs
    @OneToMany(mappedBy="bid" ,cascade= CascadeType.ALL)
    private List<BidAuditLog> auditLogs=new ArrayList<>();

    public void addAuditLog(BidAuditLog auditLog) {
        this.getAuditLogs().add(auditLog);
        auditLog.setBid(this);
    }

    public void removeAuditLog (BidAuditLog auditLog) {
        this.getAuditLogs().remove(auditLog);
        auditLog.setBid(null);
    }
}
