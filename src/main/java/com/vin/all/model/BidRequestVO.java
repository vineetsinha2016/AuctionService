package com.vin.all.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by vineetsinha on 8/19/18.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BidRequestVO {
    private String auctionItemId;
    private Long maxAutoBidAmount;
    private String bidderName;
    private String result;
}
