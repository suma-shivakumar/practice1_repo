create database test;

create table test.products 
(product_id int primary key auto_increment,
name varchar(1000), 
price int,
description varchar(2000),
date_of_manufacture date,
stocks int);

create table test.users
(user_id int primary key auto_increment,
first_name varchar(1000),
last_name varchar(100),
password varchar(1000) not null,
email_id varchar(100) unique,
phone_number int(11),
wallet int
);

create table test.product_user_mapping
(
product_user_id int primary key auto_increment,
product_id int,
user_id int,
created_date date
);

create table test.roles
(
role_id int primary key auto_increment, 
role_name varchar(1000)
);

create table test.user_roles
(
user_role_id int primary key auto_increment,
role_id int,
user_id int
);
