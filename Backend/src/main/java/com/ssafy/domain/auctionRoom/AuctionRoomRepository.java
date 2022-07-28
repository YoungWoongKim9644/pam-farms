
package com.ssafy.domain.auctionRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuctionRoomRepository extends JpaRepository<AuctionRoom, Long> {

    List<AuctionRoom> findAllByAuctionRoomTitle(String key);
/*

    @Query("select r from AuctionRoom r join r.auctionDetails")
    List<AuctionRoom> findAllByProduct(String key);
*/

}

