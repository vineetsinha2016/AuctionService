package com.vin.all.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@Table(name="auction_items")
@ToString(exclude="bids")
public class AuctionItems implements Serializable {

    @Id
    @Column(name = "AUC_IDENT")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer aucId;

    @Column(name = "AUCTION_ITEM_ID")
    private String auctionItemId;

    @Column(name = "CURRENT_BID")
    private Long currentBid;

    @Column(name = "RESERVE_PRICE")
    private Long reservePrice;

    @Column(name = "RES_PRICE_MET_IND")
    private String rpMetIndicator;

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

    //bi-directional one-to-one association to Items
    @OneToOne(mappedBy="auctionItems" ,cascade= CascadeType.ALL)
    private Items items;

    //bi-directional many-to-one association to Bids
    @OneToMany(mappedBy="auctionItem" ,cascade= CascadeType.ALL)
    private List<Bids> bids=new ArrayList<>();

    public void addBid(Bids bid) {
        this.getBids().add(bid);
        bid.setAuctionItem(this);
    }

    public void removeBid(Bids bid) {
        this.getBids().remove(bid);
        bid.setAuctionItem(null);
    }
}
