/****************** create table category *******************/
drop table if exists category;
create table category (
    category_id    int unsigned    not null auto_increment,
    category_value    varchar(255)    default ''    not null,
    active          enum('y', 'n') default 'y' not null,
    dt_created      datetime default '0000-00-00 00:00:00' not null,
    dt_updated      timestamp,
    primary key (category_id)
)auto_increment = 10000 engine = innodb;