package com.vin.all.persistence.repository;

import com.vin.all.persistence.entity.AuctionItems;
import com.vin.all.persistence.entity.Bids;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by vineetsinha on 8/18/18.
 */
public interface BidsRepository extends JpaRepository<Bids, Integer> {
    List<Bids> findByAuctionItemAucId(Integer aucId);
}