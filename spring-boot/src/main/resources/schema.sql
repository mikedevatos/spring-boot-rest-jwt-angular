
CREATE TABLE IF NOT EXISTS customer (
   id INTEGER NOT NULL AUTO_INCREMENT,
   first_name VARCHAR(255) NULL,
   last_name VARCHAR(255) NULL,
   email VARCHAR(255) unique,
   bill FLOAT NULL DEFAULT NULL,
   PRIMARY KEY (id));

 -- -----------------------------------------------------
 -- Table hotels.room
 -- -----------------------------------------------------
 CREATE TABLE IF NOT EXISTS room (
   id INTEGER NOT NULL AUTO_INCREMENT,
   price FLOAT  NULL DEFAULT NULL,
   PRIMARY KEY (id));


 -- -----------------------------------------------------
 -- Table hotels.customer_room
 -- -----------------------------------------------------
 CREATE TABLE IF NOT EXISTS customer_room(
   customer_id INTEGER NOT NULL,
   room_id INTEGER NOT NULL,
   PRIMARY KEY (customer_id, room_id),
     FOREIGN KEY (customer_id)
     REFERENCES customer (id),
     FOREIGN KEY (room_id)
     REFERENCES room (id));

 -- -----------------------------------------------------
 -- Table hotels.roles
 -- -----------------------------------------------------
 CREATE TABLE IF NOT EXISTS roles (
   id INTEGER NOT NULL AUTO_INCREMENT,
   type_role VARCHAR(45) NULL,
   PRIMARY KEY (id));

 -- -----------------------------------------------------
 -- Table hotels.users
 -- -----------------------------------------------------
 CREATE TABLE IF NOT EXISTS users (
   id INTEGER NOT NULL AUTO_INCREMENT,
   username VARCHAR(50) UNIQUE NOT NULL,
   pass VARCHAR(64) NULL,
   first_name VARCHAR(45) NULL,
   last_name VARCHAR(45) NULL,
   PRIMARY KEY (id));


 -- -----------------------------------------------------
 -- Table hotels.user_roles
 -- -----------------------------------------------------
 CREATE TABLE IF NOT EXISTS user_roles (
   user_id INTEGER NOT NULL,
   roles_id INTEGER NOT NULL,
   PRIMARY KEY (user_id, roles_id),
     FOREIGN KEY (roles_id)
     REFERENCES roles (id),
     FOREIGN KEY (user_id)
     REFERENCES users (id));

