create database dbbrowser;

CREATE USER 'dbbrowser'@'localhost' IDENTIFIED BY 'dbbrowser';
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP ON dbbrowser.* TO 'dbbrowser'@'localhost';
CREATE USER 'dbbrowser'@'%' IDENTIFIED BY 'dbbrowser';
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP ON dbbrowser.* TO 'dbbrowser'@'%';

use dbbrowser;

create table connection (
	id int NOT NULL AUTO_INCREMENT,
	name varchar(255),       
	hostname varchar(255),   
 	port smallint,      
	database_name varchar(255),
	username varchar(255),   
	password varchar(255),
	primary key (id)   
);
