
package com.ssafy.domain.auctionDetail;

import com.ssafy.api.dto.AuctionDetailDto;
import com.ssafy.api.dto.AuctionRoomDto;
import com.ssafy.api.request.AuctionDetailReq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuctionDetailRepository extends JpaRepository<AuctionDetail, Long> {
    @Query(value = "select r.id id, " +
            "r.auction_room_title auctionRoomTitle," +
            "r.auction_room_description auctionRoomDescription, " +
            "r.auction_room_thumbnail auctionRoomThumbnail," +
            "r.owner_id ownerId " +
            "from auction_room r INNER JOIN Auction_Detail d ON d.AUCTION_ROOM_ID = r.ID where d.product_title like CONCAT('%', :product, '%')", nativeQuery = true)
    List<AuctionRoomDto> findAllByProduct(@Param("product") String product);


    /*@Query(value = "select b.auctionedPrice as auctionedPrice," +
            "b.bidIncrement as bidIncrement, " +
            "b.grade as grade, " +
            "b.productTitle as productTitle, " +
            "b.quantity as quantity, " +
            "b.startingPrice as startingPrice " +
            "from AuctionDetail b where AuctionRoom.id = :id ")
    List<AuctionDetailDto> findAllByAuctionRoomId(@Param("id") Long id);*/

    @Query(value = "select * from auction_detail d where auction_room_id = :id", nativeQuery = true)
    List<AuctionDetail> findAllByAuctionRoomId(@Param("id") Long id);




}

