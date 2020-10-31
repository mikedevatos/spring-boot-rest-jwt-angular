INSERT INTO room (price) VALUES (698),(545),(292),(26),(737),(113),(221),(161),(847),(846);
INSERT INTO room (price) VALUES (336),(126),(70),(424),(405),(307),(45),(587),(80),(365);
INSERT INTO room (price) VALUES (560),(146),(824),(21),(444),(449),(573),(262),(440),(587);


INSERT INTO customer (first_name,last_name,email,bill) VALUES ('Stuart','Malachi','Torres',8),('Abbot','Gary','Gaines',4),('Hunter','Channing','Snider',2),('Erasmus','Sean','Solomon',8),('Cruz','Tyrone','David',7),('Hasad','Timothy','Blackburn',9),('Otto','Neil','Sawyer',7),('Hamish','Griffin','Potter',5),('Ross','Chaney','Jimenez',6),('Bruno','Kane','Hobbs',6);
INSERT INTO customer (first_name,last_name,email,bill) VALUES ('Lester','Tucker','Williams',2),('Brennan','Declan','Macdonald',5),('Warren','Cain','Watson',5),('Emmanuel','Kenyon','Nielsen',2),('Andrew','Walker','Contreras',10),('Dean','Patrick','Watts',5),('Blaze','Harper','Marshall',6),('Leonard','Alvin','Mcbride',6),('Jason','Levi','Cotton',7),('Marsden','Merrill','Simpson',1);
-- more customer data
/*INSERT INTO customer (first_name,last_name,email,bill) VALUES ('Benedict','Stuart','Vega',1),('Joshua','Lance','Ball',8),('Isaiah','Brent','Williams',10),('Alexander','Keane','Holloway',6),('Mannix','Quentin','Stanton',7),('Edan','Slade','Mullins',4),('Edan','Shad','Duran',3),('Rooney','Damon','Nichols',10),('Flynn','Simon','Baker',2),('Harding','Kareem','Morales',4);
INSERT INTO customer (first_name,last_name,email,bill) VALUES ('Stuart','Malachi','Torres',8),('Abbot','Gary','Gaines',4),('Hunter','Channing','Snider',2),('Erasmus','Sean','Solomon',8),('Cruz','Tyrone','David',7),('Hasad','Timothy','Blackburn',9),('Otto','Neil','Sawyer',7),('Hamish','Griffin','Potter',5),('Ross','Chaney','Jimenez',6),('Bruno','Kane','Hobbs',6);
INSERT INTO customer (first_name,last_name,email,bill) VALUES ('Lester','Tucker','Williams',2),('Brennan','Declan','Macdonald',5),('Warren','Cain','Watson',5),('Emmanuel','Kenyon','Nielsen',2),('Andrew','Walker','Contreras',10),('Dean','Patrick','Watts',5),('Blaze','Harper','Marshall',6),('Leonard','Alvin','Mcbride',6),('Jason','Levi','Cotton',7),('Marsden','Merrill','Simpson',1);
INSERT INTO customer (first_name,last_name,email,bill) VALUES ('Benedict','Stuart','Vega',1),('Joshua','Lance','Ball',8),('Isaiah','Brent','Williams',10),('Alexander','Keane','Holloway',6),('Mannix','Quentin','Stanton',7),('Edan','Slade','Mullins',4),('Edan','Shad','Duran',3),('Rooney','Damon','Nichols',10),('Flynn','Simon','Baker',2),('Harding','Kareem','Morales',4);
INSERT INTO customer (first_name,last_name,email,bill) VALUES ('Hiram','Keaton','Massey',8),('Carl','Ezra','West',4),('Eagan','Price','Vaughan',3),('Wyatt','Samson','Curtis',9),('Macaulay','Hamilton','Simmons',6),('Quinn','Zane','George',3),('Odysseus','Flynn','Dunlap',5),('Hiram','Noah','Reyes',2),('Richard','Graham','Leon',7),('Devin','Hyatt','Morris',7);
INSERT INTO customer (first_name,last_name,email,bill) VALUES ('Jasper','Brandon','Lancaster',1),('Elijah','Kennan','Merritt',6),('Michael','Gabriel','Ramirez',4),('Dominic','Boris','Quinn',7),('Peter','Wyatt','Hale',10),('Macon','Driscoll','Maldonado',7),('Lamar','Bevis','Schmidt',9),('Jermaine','Davis','Irwin',6),('Brent','Caldwell','Cote',7),('Samuel','Ferris','Fry',5);*/



INSERT INTO customer_room (customer_id,room_id) VALUES (20,19),(19,19),(9,6),(16,16),(2,2),(2,4),(3,1),(2,1),(1,2),(1,9);
/*INSERT INTO customer_room (customer_id,room_id) VALUES (5,30),(33,34),(49,23),(31,9),(19,4,(22,1),(22,5),(47,11),(34,13),(42,37);*/


-- No encoding only for development!!!!!!
--insert INTO users (username,pass,first_name,last_name) values ('user1','pass1','fname1','lname1');
--insert INTO users (username,pass,first_name,last_name) values ('user2','pass2','fname2','lname2');
--insert INTO users (username,pass,first_name,last_name) values ('user3','pass3','fname3','lname3');
--insert INTO users (username,pass,first_name,last_name) values ('user4','pass4','fname4','lname4');
--insert INTO users (username,pass,first_name,last_name) values ('user5','pass5','fname5','lname5');
--insert INTO users (username,pass,first_name,last_name) values ('user6','pass6','fname6','lname6');
--insert INTO users (username,pass,first_name,last_name) values ('user7','pass7','fname7','lname7');
--insert INTO users (username,pass,first_name,last_name) values ('user8','pass8','fname8','lname8');



--  encoding  with Bcrypt also change config in SecurityConfiguration

insert INTO users (username,pass,first_name,last_name) values ('user1','$2a$10$9olFwnsjlvqntyJRnxDJzuCfuG7LVxyrXe8llxAdW6IqXrZgVacv.','fname1','lname1');
insert INTO users (username,pass,first_name,last_name) values ('user2','$2a$10$qUWmEkp8IfIVs2yaqBoAkue56j1S9uBEGxnusHUIqxnjVlp0ERO6S','fname2','lname2');
insert INTO users (username,pass,first_name,last_name) values ('user3','$2a$10$dWSWq6L1xFJTHX5h8fD2qOWcIHG4RWpbKbE6l0bYZcgCR9JSyKbeO','fname3','lname3');
insert INTO users (username,pass,first_name,last_name) values ('user4','$2a$10$8etg/rDIZe0fIij./FWJMO10bn7llgSxXVB2ksoDqFxlHgustJuLO','fname4','lname4');
insert INTO users (username,pass,first_name,last_name) values ('user5','$2a$10$dGJevdRgL/NexKw3c9Zvc.jl2Fk9HPsam8hdOnG.AGMYcWYm2nuSO','fname5','lname5');
insert INTO users (username,pass,first_name,last_name) values ('user6','$2a$10$cClBjC1OMUZcu9tZ4Ij9nu/ANEb1zvpvM4ZkWW.VbE1gGXFDaZMai','fname6','lname6');
insert INTO users (username,pass,first_name,last_name) values ('user7','$2a$10$wIsMxxb2fRolrBGY93gPyOyJ1Z9gE69C/5k8eXKs//fZ4SLxnH7cW','fname7','lname7');
insert INTO users (username,pass,first_name,last_name) values ('user8','$2a$10$068CNDsh2VGiaBNWPgvjSehZlBZZ93R70rSWpG/8/BHAaknCGa3Xy','fname8','lname8');


INSERT INTO roles (type_role) values ('MANAGER');
INSERT INTO roles (type_role) values ('EMPLOYEE');



insert INTO user_roles (user_id,roles_id) values (1,1);
insert INTO user_roles (user_id,roles_id) values (2,1);
insert INTO user_roles (user_id,roles_id) values (3,2);
insert INTO user_roles (user_id,roles_id) values (4,2);
insert INTO user_roles (user_id,roles_id) values (5,2);
insert INTO user_roles (user_id,roles_id) values (6,2);
insert INTO user_roles (user_id,roles_id) values (7,2);
insert INTO user_roles (user_id,roles_id) values (8,2);
