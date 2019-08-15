BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `tbl_view_request` (
	`request_id`	int ( 11 ) NOT NULL,
	`requested_by`	int ( 11 ) NOT NULL,
	`requested_property`	int ( 11 ) NOT NULL,
	`requested_date`	date DEFAULT NULL,
	`date_of_view`	date NOT NULL,
	`status`	tinyint ( 1 ) DEFAULT '0'
);
CREATE TABLE tbl_users (user_id INTEGER PRIMARY KEY ASC AUTOINCREMENT UNIQUE NOT NULL, username VARCHAR (30) NOT NULL UNIQUE, password VARCHAR (30) NOT NULL, user_type INTEGER NOT NULL ON CONFLICT ROLLBACK, status BOOLEAN DEFAULT (0) NOT NULL ON CONFLICT ROLLBACK);
INSERT INTO `tbl_users` VALUES (1,'fazrin','abcd',1,1),
 (2,'nirzaf','1234',1,1),
 (3,'arfath','123456',1,1),
 (4,'xamra','123456',1,1),
 (5,'zamhar','123456',1,1),
 (6,'anjuthan','123456',2,1),
 (7,'dharshan','123456',2,1),
 (8,'rizmy','123456',1,1),
 (9,'abcd@123','123456',1,1),
 (10,'abcde','123456',2,1);
CREATE TABLE IF NOT EXISTS `tbl_student` (
	`student_id`	int ( 11 ) NOT NULL,
	`first_name`	varchar ( 100 ) NOT NULL,
	`last_name`	varchar ( 100 ) NOT NULL,
	`address`	text NOT NULL,
	`email`	varchar ( 50 ) NOT NULL,
	`telephone`	varchar ( 25 ) NOT NULL,
	`isDeleted`	tinyint ( 1 ) NOT NULL DEFAULT '0'
);
INSERT INTO `tbl_student` VALUES (1,'fazrin','farook','412, Bulugohotenne, Batugoda, Kandy','mfmfazrin1986@gmail.com','0772049123',1),
 (2,'jerom','sanjeewan','hekita, wattala','jerom@gmail.com','0772940123',1),
 (3,'Zamra','Banu','kolonnawa, Colombo','zamra@gmail.com','0776543212',1),
 (4,'Fazrin','Nuh','123, Kandy Road, Matale','abcd@gmail.com','0774321212',1),
 (5,'Arfath','Mohamed','123, Bambalapitiya','abcd@gmail.com','0773434343',1);
CREATE TABLE tbl_property_types (type_id INTEGER PRIMARY KEY ASC AUTOINCREMENT NOT NULL ON CONFLICT ROLLBACK, type_name int (11) NOT NULL, isDeleted BOOLEAN DEFAULT (1));
INSERT INTO `tbl_property_types` VALUES (1,'Single Room',1),
 (2,'Double Room',1),
 (3,'Annex',1),
 (4,'Flat
',1);
CREATE TABLE IF NOT EXISTS `tbl_property` (
	`property_id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`property_type`	int ( 11 ) NOT NULL,
	`address`	TEXT,
	`suitable_for`	smallint ( 6 ) NOT NULL,
	`is_available`	tinyint ( 1 ) NOT NULL,
	`owner`	int ( 11 ) NOT NULL,
	`rented_by`	int ( 11 ) DEFAULT 0,
	`charge`	float NOT NULL,
	`isDeleted`	BOOLEAN DEFAULT (1)
);
INSERT INTO `tbl_property` VALUES (1,1,'123, jaffna',4,0,2,3,500.0,1),
 (2,2,'321, Colombo',5,0,2,4,1000.0,1),
 (3,2,'88, Matale ',20,1,2,0,5000.0,1);
CREATE TABLE IF NOT EXISTS `tbl_payment` (
	`payment_id`	int ( 11 ) NOT NULL,
	`property_id`	int ( 11 ) NOT NULL,
	`charge`	float NOT NULL,
	`payment_month`	date NOT NULL,
	`paid_on`	datetime,
	`status`	tinyint ( 1 ) NOT NULL DEFAULT '0'
);
CREATE TABLE IF NOT EXISTS `tbl_owner` (
	`owner_id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`first_name`	VARCHAR ( 50 ),
	`last_name`	VARCHAR ( 50 ),
	`address`	VARCHAR ( 250 ),
	`email`	VARCHAR ( 50 ),
	`telephone`	VARCHAR ( 20 ),
	`isDeleted`	BOOLEAN ( 0 ) NOT NULL DEFAULT (0)
);
INSERT INTO `tbl_owner` VALUES (1,'Farook','Fazrin','412 bulugohotenne, Batugoda','mfmfazrin1986@gmail.com','+94770000000',1),
 (2,'Farook','gggggg','412, Bulugohotenne','heavenlankatours55@gmail.com','07720444444',1),
 (3,'Mohamed Farook','Fazrin','412 bulugohotenne, Batugoda','mfmfazrin1986@gmail.com','+94772049123',1),
 (4,'First Name','Last Name','123, Colombo Road, Kandy','mfmfazrin1986@gmail.com','+94772049123',1),
 (5,'First Name','Last Name','123, Colombo Road, Kandy','mfmfazrin1986@gmail.com','+94772049123',0);
COMMIT;
