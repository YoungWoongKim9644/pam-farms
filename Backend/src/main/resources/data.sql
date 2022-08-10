INSERT INTO IMAGE (content_type, file_path, file_size, origin_file_name, server_file_name)
VALUES
('image/jpeg', '/root/pictures/202208101037266079035057.jpg', 88821, 'karina.jpg', '202208101037266079035057.jpg'),
('image/png', '/root/pictures/202208101037502688086397.png', 21058, 'dog.png', '202208101037502688086397.png'),
('string', 'string3', 'string', 'string', 100),
('string', 'string4', 'string', 'string', 100),
('string', 'string5', 'string', 'string', 100),
('string', 'string6', 'string', 'string', 100),
('string', 'string7', 'string', 'string', 100),
('string', 'string8', 'string', 'string', 100),
('string', 'string9', 'string', 'string', 100),
('string', 'string10', 'string', 'string', 100);

INSERT INTO user (account, address, name, password, phone, data_create, IMAGE_ID)
VALUES ('110-342-143345', 'SSAFY', '홍길동', '1','01000000001', now(), 1);


INSERT INTO AUCTION_ROOM (created_at, auction_room_description, thumbnail_id, auction_room_title, auctioned, owner_id)
VALUES
(now(), '감자감자방', 1, '1번 감자방', false, 1),
(now(), 'string2', 2, '2번 열정 농부방', false, 1),
(now(), 'string3', 3, '3번 멋쟁이 포도방', false, 1),
(now(), 'string4', 4, 'string', false, 1),
(now(), 'string5', 5, 'string', false, 1),
(now(), 'string6', 6, 'string', false, 1),
(now(), 'string7', 7, 'string', false, 1),
(now(), 'string8', 8, 'string', false, 1),
(now(), 'string9', 9, 'string', false, 1),
(now(), 'string10', 10, 'string', false, 1);