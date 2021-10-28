SET FOREIGN_KEY_CHECKS=0;

CREATE TABLE IF NOT EXISTS roles (
   id INTEGER NOT NULL AUTO_INCREMENT,
   type_role VARCHAR(45) NULL,
   PRIMARY KEY (id)
   );

 CREATE TABLE IF NOT EXISTS users (
   id INTEGER NOT NULL AUTO_INCREMENT,
   username VARCHAR(50) UNIQUE NOT NULL,
   pass VARCHAR(64) NULL,
   first_name VARCHAR(45) NULL,
   last_name VARCHAR(45) NULL,
   roles_id int,
   FOREIGN KEY (roles_id) REFERENCES roles(id),

   PRIMARY KEY (id)
   );



CREATE TABLE IF NOT EXISTS room (
   id INT NOT NULL AUTO_INCREMENT,
   price FLOAT  NULL DEFAULT NULL,
   room_capacity int NOT NULL,
   PRIMARY KEY (id)
  );


create table  IF NOT EXISTS booking(
  id int NOT NULL AUTO_INCREMENT,
  start_booking DATE NOT NULL,
  end_booking  DATE NOT NULL,
  PRIMARY KEY (id)
);




CREATE TABLE IF NOT EXISTS customer (
   id INT NOT NULL AUTO_INCREMENT,
   bill FLOAT DEFAULT 0.0,
   account_type  VARCHAR(50) NOT NULL,
   room_id INT  NOT NULL,
   booking_id INT NOT NULL UNIQUE,
   FOREIGN KEY (room_id) REFERENCES room(id),
   FOREIGN KEY (booking_id)  REFERENCES booking(id),
   PRIMARY KEY (id)
   );


  CREATE TABLE IF NOT EXISTS person (
   id int NOT NULL AUTO_INCREMENT,
   first_name VARCHAR(255) NOT NULL,
   last_name VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL UNIQUE,
   customer_id int,
   FOREIGN KEY (customer_id) REFERENCES customer(id),
   PRIMARY KEY (id) );


SET FOREIGN_KEY_CHECKS=1;




