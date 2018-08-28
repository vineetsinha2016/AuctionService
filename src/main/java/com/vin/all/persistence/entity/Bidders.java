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
@Table(name="bidder_info")
@ToString(exclude="bids")
public class Bidders implements Serializable{

    @Id
    @Column(name = "BIDDER_ID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer bidderId;

    @Column(name = "BIDDER_NAME")
    private String bidderName;

    @Column(name = "BIDDER_EMAIL")
    private String bidderEmail;

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
    @OneToMany(mappedBy="bidder" ,cascade= CascadeType.ALL)
    private List<Bids> bids=new ArrayList<>();

    public void addBid(Bids bid) {
        this.getBids().add(bid);
        bid.setBidder(this);
    }

    public void removeBid(Bids bid) {
        this.getBids().remove(bid);
        bid.setBidder(null);
    }
}
