package com.ssafy.api.service;

import com.ssafy.api.dto.ProductAuctionDto;
import com.ssafy.api.request.SessionReq;
import com.ssafy.domain.auctionDetail.AuctionDetailRepository;
import com.ssafy.domain.auctionRoom.AuctionRoom;
import com.ssafy.domain.auctionRoom.AuctionRoomRepository;
import com.ssafy.domain.grade.GradeRepository;
import com.ssafy.domain.product.Product;
import com.ssafy.domain.product.ProductRepository;
import com.ssafy.domain.auctionDetail.AuctionDetail;
import com.ssafy.domain.user.User;
import com.ssafy.domain.user.UserRepository;
import io.openvidu.java.client.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {

    @Autowired
    private AuctionRoomRepository auctionRoomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuctionDetailRepository auctionDetailRepository;
    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private ProductRepository productRepository;

    private OpenVidu openVidu;
    private Map<String, Session> mapSessions = new ConcurrentHashMap<>();
    private Map<String, Map<String, OpenViduRole>> mapSessionNamesTokens = new ConcurrentHashMap<>();

    private String OPENVIDU_URL;
    private String SECRET;


    public SessionService(@Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl) {
        this.SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
    }

    public ResponseEntity<JSONObject> createRoom(String phoneNumber, SessionReq sessionInfo){

        User owner = userRepository.findByPhone(phoneNumber);
        List<ProductAuctionDto> auctionInfoList = sessionInfo.getAuctionInfoList();

        //auction room 저장
        AuctionRoom auctionRoom = new AuctionRoom(owner,
                sessionInfo.getAuctionRoomTitle(),
                sessionInfo.getRoomDescription(),
                sessionInfo.getAuctionRoomThumbnail());

        auctionRoomRepository.save(auctionRoom);

        //연결된 productAuction 저장장
       for(int i = 0 ; i < auctionInfoList.size(); i++){
            AuctionDetail productAuction = new AuctionDetail();

            Product product = productRepository.findByIdAndGrade(auctionInfoList.get(i).getProductId(), auctionInfoList.get(i).getGradeId());

            productAuction.setAuctionRoom(auctionRoom);
            productAuction.setAuctioned(false);

            productAuction.setProduct(product);
            productAuction.setAuctionedPrice(auctionInfoList.get(i).getAuctionedPrice());
            productAuction.setBidIncrement(auctionInfoList.get(i).getBidIncrement());
            productAuction.setQuantity(auctionInfoList.get(i).getQuantity());
            productAuction.setInitPrice(auctionInfoList.get(i).getInitPrice());

            auctionDetailRepository.save(productAuction);
        }


        JSONObject responseJson = new JSONObject();
        responseJson.put(0,"success");
        return new ResponseEntity<>(responseJson,HttpStatus.OK);
    }

    public ResponseEntity<JSONObject> getToken(String phoneNumber, SessionReq sessionInfo){
        String sessionName = String.valueOf(sessionInfo.getAuctionRoomTitle());
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC).data(phoneNumber).role(OpenViduRole.PUBLISHER).build();
        JSONObject responseJson = new JSONObject();

        if (this.mapSessions.get(sessionName) != null) {

            try {
                String token = this.mapSessions.get(sessionName).createConnection(connectionProperties).getToken();
                this.mapSessionNamesTokens.get(sessionName).put(token, OpenViduRole.PUBLISHER);
                responseJson.put(0, token);
                return new ResponseEntity<>(responseJson, HttpStatus.OK);

            } catch (OpenViduJavaClientException e1) {
                return getErrorResponse(e1);
            } catch (OpenViduHttpException e2) {
                if (404 == e2.getStatus()) {
                    this.mapSessions.remove(sessionName);
                    this.mapSessionNamesTokens.remove(sessionName);
                }
            }
        }

        try {

            Session session = this.openVidu.createSession();
            String token = session.createConnection(connectionProperties).getToken();

            this.mapSessions.put(sessionName, session);
            this.mapSessionNamesTokens.put(sessionName, new ConcurrentHashMap<>());
            this.mapSessionNamesTokens.get(sessionName).put(token, OpenViduRole.PUBLISHER);

            responseJson.put(0, token);

            return new ResponseEntity<>(responseJson, HttpStatus.OK);

        } catch (Exception e) {
            return getErrorResponse(e);
        }

    }

    private ResponseEntity<JSONObject> getErrorResponse(Exception e) {
        JSONObject json = new JSONObject();
        json.put("cause", e.getCause());
        json.put("error", e.getMessage());
        json.put("exception", e.getClass());
        return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // 방 제목 검색
    // 품목 검색
    // 통합 검색
    // 농부 이름 검색
    public List<AuctionRoom> search(HashMap<String, String> params) {

        if(params.get("mode").equals("1")){
            return auctionRoomRepository.findAllByAuctionRoomTitle(params.get("key"));
        }else if(params.get("mode").equals("2")){
           /* return auctionDetailRepository.findALlByProduct(params.get("key"));*/
        }else if(params.get("mode").equals("3")){
            /*return auctionDetailRepository.findAllByTitle(params.get("key"));*/
        }

        throw new IllegalArgumentException();
    }
}
