package com.vin.all.persistence.repository;

import com.vin.all.persistence.entity.Bidders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by vineetsinha on 8/19/18.
 */
public interface BidderRepository extends JpaRepository<Bidders, Integer> {
    List<Bidders> findByBidderName(String bidderName);
}
