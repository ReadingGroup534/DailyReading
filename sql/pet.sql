/*******************user pet ***********/
drop table if exists pet;
create table pet (
    pet_id            int unsigned not null auto_increment,
    pet_name          varchar(255) default '' not null,
    user_id           int unsigned    not null,
    pet_level         int unsigned default 0 not null,
    pet_joyful        int unsigned default 0 not null,
    primary key (pet_id),
    unique index user_id (user_id)
)engine = innodb;
/*******************user pet end *********/