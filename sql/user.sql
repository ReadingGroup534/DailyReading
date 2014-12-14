/************ user infos **************/
drop table if exists user;
create table user(
    user_id            int unsigned    not null    auto_increment,
    account_no        varchar(11)    default ''  not null,
    password            varchar(20)    default '' not null,
    active            enum('y', 'n') default 'y' not null,
    dt_created        datetime         default '0000-00-00 00:00:00' not null,
    dt_updated        timestamp,
    primary key (user_id)
)engine = innodb;

/*********** device infos *************/

/*********** token infos **************/
drop table if exists token;
create table token (
    token_id        int unsigned    not null    auto_increment,
    user_id        int unsigned     default 0    not null,
    app_key        char(64)            default '' not null,
    app_name        varchar(255)    default '' not null,
    app_pack        varchar(255)    default '' not null,
    dt_created        datetime         default '0000-00-00 00:00:00' not null,
    dt_updated        timestamp,
    primary key (token_id),
    unique index app_pack(app_pack)
)engine = innodb;