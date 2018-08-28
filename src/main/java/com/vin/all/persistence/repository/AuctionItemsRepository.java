package com.vin.all.persistence.repository;

import com.vin.all.persistence.entity.AuctionItems;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by vineetsinha on 8/18/18.
 */
public interface AuctionItemsRepository  extends JpaRepository<AuctionItems, Integer>{
    AuctionItems findByAuctionItemId (String auctionItemId);
}
