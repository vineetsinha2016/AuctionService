package com.vin.all.service;

import com.vin.all.model.AuctionVO;
import com.vin.all.model.Item;
import com.vin.all.persistence.entity.AuctionItems;
import com.vin.all.persistence.entity.Items;
import com.vin.all.persistence.repository.AuctionItemsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by vineetsinha on 8/18/18.
 */
@Service
public class AuctionService {
    private static final Logger log = LoggerFactory.getLogger(AuctionService.class);

    private final AuctionItemsRepository auctionItemsRepository;

    public AuctionService(AuctionItemsRepository auctionItemsRepository) {
        this.auctionItemsRepository = auctionItemsRepository;
    }

    public AuctionVO saveAuctionItems (final AuctionVO auctionRequest,
                                       final HttpHeaders headers) throws Exception{
       if (auctionRequest == null || auctionRequest.getReservePrice()==null
               || auctionRequest.getItem()==null
               || auctionRequest.getItem().getItemId()==null
               || auctionRequest.getItem().getDescription()==null) {
           log.error("request Object is missing.. throw exception");
           return null;
       }
        AuctionItems auctionItems = new AuctionItems();
        Items items = new Items();
        items.setItemId(auctionRequest.getItem().getItemId());
        items.setItemDescription(auctionRequest.getItem().getDescription());
        items.setRecSource(headers.getFirst("rec-source"));
        items.setRecordAddTime(new Timestamp(new Date().getTime()));
        items.setAddByUser(headers.getFirst("userId"));
        items.setUpdatedByUser(headers.getFirst("userId"));
        items.setRecordUpdatedTime(new Timestamp(new Date().getTime()));
        auctionItems.setItems(items);
        auctionItems.setReservePrice(auctionRequest.getReservePrice());
        auctionItems.setCurrentBid(new Long(0));
        auctionItems.setRpMetIndicator("N");
        auctionItems.setAuctionItemId(generateAuctionItemId());
        auctionItems.setRecSource(headers.getFirst("rec-source"));
        auctionItems.setRecordAddTime(new Timestamp(new Date().getTime()));
        auctionItems.setAddByUser(headers.getFirst("userId"));
        auctionItems.setUpdatedByUser(headers.getFirst("userId"));
        auctionItems.setRecordUpdatedTime(new Timestamp(new Date().getTime()));
        items.setAuctionItems(auctionItems);
        auctionItemsRepository.save(auctionItems);
        return new AuctionVO(auctionItems.getAuctionItemId());
    }

    public List<AuctionVO> getAllAuctionItems (HttpHeaders headers) {
        return getVOFromEntities(auctionItemsRepository.findAll());
    }

    public AuctionVO getAuctionItem (String auctionItemId, HttpHeaders headers) {
         return extractVOFromEntity(auctionItemsRepository
                 .findByAuctionItemId(auctionItemId));
    }

    private List<AuctionVO> getVOFromEntities (List<AuctionItems> entities) {
        List<AuctionVO> auctionVOList = new ArrayList<>();
        if (entities!=null && entities.size()>0) {
            for(AuctionItems ai : entities)
            auctionVOList.add(extractVOFromEntity(ai));
        }
        return auctionVOList;
    }
    private AuctionVO extractVOFromEntity(AuctionItems entity) {
        if(entity == null) {
            return new AuctionVO();
        }
        AuctionVO auctionVO = new AuctionVO(entity.getAuctionItemId());
        auctionVO.setAuctionItemId(entity.getAuctionItemId());
        auctionVO.setCurrentBid(entity.getCurrentBid());
        auctionVO.setReservePrice(entity.getReservePrice());
        auctionVO.setItem(new Item(entity.getItems().getItemId(),
                entity.getItems().getItemDescription()));
        return auctionVO;
    }
    private String generateAuctionItemId() {
        UUID idOne = UUID.randomUUID();
        return String.valueOf(idOne);
    }

}
