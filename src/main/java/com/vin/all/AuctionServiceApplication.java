package com.vin.all;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * Created by vineetsinha on 8/18/18.
 */
@SpringBootApplication
public class AuctionServiceApplication {
    public static void main (String[] args){
        SpringApplication.run(AuctionServiceApplication.class);
    }

    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));   // It will set UTC timezone
    }
}
