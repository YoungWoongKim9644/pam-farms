import ModalPortal from "./ModalPortal";
import styled from "styled-components"
import Text from "../../atoms/Text";
import Button from "../../atoms/Button";
import theme from "../../../common/theme"
import CloseIcon from '@mui/icons-material/Close';
import ReadItemCard from "../ReadItemCard";
import { useNavigate } from "react-router-dom";

// 위로 올라오듯이 css 효과 넣기
const BackGround = styled.div`
  position: fixed;
  background-color: rgba(0, 0, 0, 0.4);
  width: 100%;
  height: 100vh;
  top: 0;
  left: 0;
`

const Content = styled.div`
  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  margin: auto;
  background-color: white;
  width: 100%;
  height: 65vh;
  border-radius: 1rem 1rem 0 0;
  box-shadow: 0 0 1rem 0.2rem rgba(0, 0, 0, 0.3);
`

const Header = styled.div`
  background-color: ${theme.colors.green5};
  width: 100%;
  height: 9%;
  border-radius: 1rem 1rem 0 0;
  display: flex;
  justify-content: space-between; 
  align-items: center;
`

// 왜 알파벳은 개행 안되냐거~~
const RoomDetailSection = styled.div`
  width: 100%;
  height: 82%;
  overflow-x: hidden;
  overflow-y: auto;
  padding: 0 1rem 2rem 1rem;
`

// 개행이 됐었는데 안됐습니다
const TitleSection = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: auto;
  padding-top: 0.5rem;
  white-space: pre-wrap;
  border-bottom: 2px solid ${theme.colors.gray1};
`
 
const DescriptionSection = styled.div`
  display: flex;
  flex-direction: column;
  white-space: pre-wrap;
  padding-top: 0.5rem;
  width: 100%;
  height: auto;
  border-bottom: 2px solid ${theme.colors.gray1};
`

const ItemsSection = styled.div` 
  display: flex;
  flex-direction: column;
  width: 100%;
  height: auto;
`

const ItemCardSection = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: auto;
`

const ButtonSection = styled.div`
  position: fixed;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 9%;
  background-color: white;
`

const Div = styled.div`
  margin-top: ${(props) => props.mt + 'rem'};
  margin-bottom: ${(props) => props.mb + 'rem'};
  margin-left: ${(props) => props.ml + 'rem'};
  margin-right: ${(props) => props.mr + 'rem'};
  padding-top: ${(props) => props.pt + 'rem'};
  padding-bottom: ${(props) => props.pb + 'rem'};
  padding-left: ${(props) => props.pl + 'rem'};
  padding-right: ${(props) => props.pr + 'rem'};
`

const RoomDetailModal = ({title, description, items, closeModal, roomId}) => {

  const navigate = useNavigate();

  const enterRoomHandler = (roomId) => {
    navigate("/room")
  }

  return (
    <ModalPortal>
      <BackGround onClick={(e) => {
        closeModal();
        e.stopPropagation();
      }}/>
      <Content>
        <Header>
          <Div pl={1}>
            <Text color="white" size="xxl" weight="bold">방 상세 정보</Text>
          </Div>
          <Div pr={1}>
            <CloseIcon 
              onClick={(e) => {
                closeModal();
                e.stopPropagation();
              }} 
              style={{cursor: 'pointer', color: 'white'}}
            />
          </Div>
        </Header>
        <RoomDetailSection>
          <Div mt={0.5}>
            <TitleSection>
              <Text color="gray2" size="xl" weight="bold">방 제목</Text>
              <Div mt={0.5} mb={0.5}>
                <Text size="lg">{title}</Text>
              </Div>
            </TitleSection>
          </Div>
          <Div mt={0.5}>
            <DescriptionSection>
            <Text color="gray2" size="xl" weight="bold">내용</Text>
            <Div mt={0.5} mb={0.5}>
              <Text>{description}</Text>
            </Div>
            </DescriptionSection>
          </Div>
          <Div mt={0.5}>
          <ItemsSection>
            <Text color="gray2" size="xl" weight="bold">항목</Text>
            <ItemCardSection>
              {items ? 
                items.map((item) => <ReadItemCard {...item} key={item.id}/>) :
                <div>isLoading</div>
              }                        
            </ItemCardSection>
          </ItemsSection>
          </Div>
        </RoomDetailSection>
        <ButtonSection>
          <Button width="90%" onClick={enterRoomHandler}>입장하기</Button>
        </ButtonSection>
      </Content>
    </ModalPortal>
  );
}

const EXAMPLE_ITEMS = [
  {
    id: '1',
    productTitle: "배추",
    quantity: "1000kg",
    grade: "최상",
    startingPrice: "100,000원"
  },
  {
    id: '2',
    productTitle: "감자",
    quantity: "2000kg",
    grade: "최상",
    startingPrice: "200,000원"
  },
  {
    id: '3',
    productTitle: "당근",
    quantity: "500kg",
    grade: "중상",
    startingPrice: "80,000원"
  },
]

RoomDetailModal.defaultProps = {
  items: EXAMPLE_ITEMS,
}

export default RoomDetailModal;