package com.ssafy.api.dto;


public interface AuctionRoomDto {
    Long getId();
    String getAuctionRoomTitle();
    String getAuctionRoomDescription();
    String getThumbnailId();
    Long getOwnerId();
}
