package com.vin.all.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * Created by vineetsinha on 8/18/18.
 */
@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuctionVO implements Serializable{
    private String auctionItemId;
    private Long currentBid;
    private Long reservePrice;
    private Item item;


    public AuctionVO(String auctionItemId) {
        this.auctionItemId = auctionItemId;
    }
}
