
SET FOREIGN_KEY_CHECKS=0;

INSERT INTO roles (type_role) values ('MANAGER');
INSERT INTO roles (type_role) values ('EMPLOYEE');

--  encoding  with Bcrypt also change config in SecurityConfiguration
insert INTO users (username,pass,first_name,last_name,roles_id) values ('user1','$2a$10$9olFwnsjlvqntyJRnxDJzuCfuG7LVxyrXe8llxAdW6IqXrZgVacv.','fname1','lname1',1);
insert INTO users (username,pass,first_name,last_name,roles_id) values ('user2','$2a$10$qUWmEkp8IfIVs2yaqBoAkue56j1S9uBEGxnusHUIqxnjVlp0ERO6S','fname2','lname2',1);
insert INTO users (username,pass,first_name,last_name,roles_id) values ('user3','$2a$10$dWSWq6L1xFJTHX5h8fD2qOWcIHG4RWpbKbE6l0bYZcgCR9JSyKbeO','fname3','lname3',2);
insert INTO users (username,pass,first_name,last_name,roles_id) values ('user4','$2a$10$8etg/rDIZe0fIij./FWJMO10bn7llgSxXVB2ksoDqFxlHgustJuLO','fname4','lname4',2);
insert INTO users (username,pass,first_name,last_name,roles_id) values ('user5','$2a$10$dGJevdRgL/NexKw3c9Zvc.jl2Fk9HPsam8hdOnG.AGMYcWYm2nuSO','fname5','lname5',2);
insert INTO users (username,pass,first_name,last_name,roles_id) values ('user6','$2a$10$cClBjC1OMUZcu9tZ4Ij9nu/ANEb1zvpvM4ZkWW.VbE1gGXFDaZMai','fname6','lname6',2);
insert INTO users (username,pass,first_name,last_name,roles_id) values ('user7','$2a$10$wIsMxxb2fRolrBGY93gPyOyJ1Z9gE69C/5k8eXKs//fZ4SLxnH7cW','fname7','lname7',2);
insert INTO users (username,pass,first_name,last_name,roles_id) values ('user8','$2a$10$068CNDsh2VGiaBNWPgvjSehZlBZZ93R70rSWpG/8/BHAaknCGa3Xy','fname8','lname8',2);


INSERT INTO room (id,price,room_capacity) VALUES
(1,698,2),
(2,545,4),
(3,292,5),
(4,333,4),
(5,444,4),
(6,333,4),
(7,777,4);


INSERT INTO booking(id,start_booking,end_booking) values (1,'2021-09-09','2021-09-11');
INSERT INTO booking(id,start_booking,end_booking) values (2,'2021-09-09','2021-09-12');
INSERT INTO booking(id,start_booking,end_booking) values (3,'2021-09-09','2021-09-15');
INSERT INTO booking(id,start_booking,end_booking) values (4,'2021-09-09','2021-09-20');
INSERT INTO booking(id,start_booking,end_booking) values (5,'2021-09-09','2021-09-11');

INSERT INTO booking(id,start_booking,end_booking) values (6,'2021-09-21','2021-09-30');
INSERT INTO booking(id,start_booking,end_booking) values (7,'2021-09-21','2021-09-25');
INSERT INTO booking(id,start_booking,end_booking) values (8,'2021-09-21','2021-09-28');
INSERT INTO booking(id,start_booking,end_booking) values (9,'2021-09-21','2021-09-23');
INSERT INTO booking(id,start_booking,end_booking) values (10,'2021-09-21','2021-09-24');

INSERT INTO booking(id,start_booking,end_booking) values (11,'2021-10-15','2021-10-20');    
INSERT INTO booking(id,start_booking,end_booking) values (12,'2021-10-15','2021-10-20');
INSERT INTO booking(id,start_booking,end_booking) values (13,'2021-10-15','2021-10-20');
INSERT INTO booking(id,start_booking,end_booking) values (14,'2021-10-15','2021-10-20');
INSERT INTO booking(id,start_booking,end_booking) values (15,'2021-10-15','2021-10-20');

INSERT INTO booking(id,start_booking,end_booking) values (16,'2021-09-14','2021-09-15');
INSERT INTO booking(id,start_booking,end_booking) values (17,'2021-10-25','2021-10-31');
INSERT INTO booking(id,start_booking,end_booking) values (18,'2021-11-25','2021-11-28');


INSERT INTO customer (id,room_id,booking_id,account_type,bill) VALUES

(1,1,1, 'vacation',2094),
(2,2,2, 'vacation',2180),
(3,3,3, 'bussines',2044),
(4,4,4, 'bussines',3996),
(5,5,5, 'bussines',1332),

(6,1,6, 'bussines',6980),
(7,2,7, 'bussines',2725),
(8,3,8, 'bussines',2336),
(9,4,9, 'bussines',999),
(10,5,10,'vacation',1776),

(11,1,11, 'vacation',25128),
(12,2,12, 'vacation',3270),
(13,3,13, 'bussines',1752),
(14,4,14, 'bussines',1998),
(15,5,15, 'bussines',2664),

(16,6,16, 'bussines',5),
(17,6,17, 'bussines',6),
(18,6,18, 'bussines',7) ;



INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firtsname_1'  , 'lastname_1'  , 'email@_1' , 1);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firtsname_2'  , 'lastname_2'  , 'email@_2' , 1);

INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firtsname_3'  , 'lastname_3'  , 'email@_3'  , 2);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firtsname_4'  , 'lastname_4'  , 'email@_4'  , 2);

INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_5'  , 'lastname_5'  , 'email@_5'  , 2);

INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_6'  , 'lastname_6'  , 'email@_6'   , 3);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_7'  , 'lastname_7'  , 'email@_7'   , 3);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_8'  , 'lastname_8'  , 'email@_8'   , 3);

INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_9'  , 'lastname_9'  , 'email@_9'      , 4);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_10' , 'lastname_10' , 'email@_10'     , 4);

INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_11' , 'lastname_11' , 'email@_11'    , 5);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_12' , 'lastname_12' , 'email@_12'    , 5);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_13' , 'lastname_13' , 'email@_13'    , 5);

INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_14' , 'lastname_14' , 'email@_14'    , 6);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_15' , 'lastname_15' , 'email@_15'    , 6);

INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_16' , 'lastname_16' , 'email@_16'    , 7);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_17' , 'lastname_17' , 'email@_17'    , 7);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_18' , 'lastname_18' , 'email@_18'    , 7);

INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_19' , 'lastname_19' , 'email@_19'    , 8);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_20' , 'lastname_20' , 'email@_20'    , 8);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_21' , 'lastname_21' , 'email@_21'    , 8);

INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_22' , 'lastname_22' , 'email@_22'     , 9);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_23' , 'lastname_23' , 'email@_23'     , 9);

INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_24' , 'lastname_24' , 'email@_24'     , 10);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_25' , 'lastname_25' , 'email@_25'     , 10);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_26' , 'lastname_26' , 'email@_26'     , 10);

INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_27' , 'lastname_27' , 'email@_27'     , 11);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_28' , 'lastname_28' , 'email@_28'     , 11);

INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_29' , 'lastname_29' , 'email@_29'     , 12);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_30' , 'lastname_30' , 'email@_30'     , 12);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_31' , 'lastname_31' , 'email@_31' , 2);

INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_32' , 'lastname_32' , 'email@_32'    , 13);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_33' , 'lastname_33' , 'email@_33'    , 13);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_34' , 'lastname_34' , 'email@_34'    , 13);

INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_35' , 'lastname_35' , 'email@_35'     , 14);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_36' , 'lastname_36' , 'email@_36'     , 14);

INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_37' , 'lastname_37' , 'email@_37'      , 15);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_38' , 'lastname_38' , 'email@_38'      , 15);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_39' , 'lastname_39' , 'email@_39'      , 15);

INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_37' , 'lastname_37' , 'email@_40'      , 16);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_38' , 'lastname_38' , 'email@_41'      , 17);
INSERT INTO person (first_name , last_name , email , customer_id) VALUES ('firstname_39' , 'lastname_39' , 'email@_42'      , 18);

SET FOREIGN_KEY_CHECKS=1;

-- No encoding only for development!!!!!!
--insert INTO users (username,pass,first_name,last_name) values ('user1','pass1','fname1','lname1');
--insert INTO users (username,pass,first_name,last_name) values ('user2','pass2','fname2','lname2');
--insert INTO users (username,pass,first_name,last_name) values ('user3','pass3','fname3','lname3');
--insert INTO users (username,pass,first_name,last_name) values ('user4','pass4','fname4','lname4');
--insert INTO users (username,pass,first_name,last_name) values ('user5','pass5','fname5','lname5');
--insert INTO users (username,pass,first_name,last_name) values ('user6','pass6','fname6','lname6');
--insert INTO users (username,pass,first_name,last_name) values ('user7','pass7','fname7','lname7');
--insert INTO users (username,pass,first_name,last_name) values ('user8','pass8','fname8','lname8');


