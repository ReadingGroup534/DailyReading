/*************************
 * create database for the reading
 * and add app user
**************************/
create database if not exists reading default charset = utf8 default collate = utf8_general_ci;
/******************************
* grant
*******************************/
grant all privileges on reading.* to app@localhost identified by 'app_pass';
grant all privileges on reading.* to app@'%' identified by 'app_pass';