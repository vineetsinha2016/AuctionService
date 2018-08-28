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
@ToString(exclude="auctionItems")
@Table(name="items_to_auction")
public class Items implements Serializable{

    @Id
    @Column(name = "ITEM_IDENT")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer itId;

    @Column(name = "ITEM_ID")
    private String itemId;

    @Column(name = "ITEM_DESC")
    private String itemDescription;

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

    //bi-directional one-to-one association to AuctionItems
    @OneToOne
    @JoinColumn(name = "AUC_IDENT")
    private AuctionItems auctionItems;
}
