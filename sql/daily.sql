/**
*    最终显示给用户的数据
*/
drop table if exists article;

create table article{
    article_id            char(22)                default '' not null,
    active                enum('y','n')           default 'y' not null, --the article wether display   
    show_time             datetime                default '0000-00-00 00:00:00' not null, -- display time
    title                 varchar(255)            default '' not null,
    author                varchar(255)            default '' not null,
    article_type          enum('sanwen','gushi','lizhi','other')    default 'other' not null, -- category
    abstracts             varchar(255)            default '' not null, -- good snapshop 
    recommend_star        smallint unsigned       default 0   not null,
    praise_times	  int unsigned		  default 0   not null, -- nice number	
    share_times		  int unsigned		  default 0   not null, --share
    scan_times            int unsigned            default 0   not null, -- total read times   
    physical_path         varchar(255)            default '' not null, -- storage path
    primary key (article_id)
}engine=innodb;

/**
*    显示之前需要整理的数据
*/
drop table if exists pre_article;

create table preview{
    preview_id            char(22)                default '' not null,
    active                enum('y','n')           default 'y' not null, --the article wether display   
    show_time             datetime                default '0000-00-00 00:00:00' not null, -- display time
    title                 varchar(255)            default '' not null,
    author                varchar(255)            default '' not null,
    article_type          enum('sanwen','gushi','lizhi','other')    default 'other' not null, -- category
    abstracts             varchar(255)            default '' not null, -- good snapshop 
    recommend_star        smallint unsigned       default 0 not null,
    scan_times            int unsigned            default 0 not null, -- total read times   
    physical_path         varchar(255)            default '' not null, -- storage path
    dt_created             datetime        default '0000-00-00 00:00:00' not null,
    dt_updated             datetime        default '0000-00-00 00:00:00' not null,

    primary key (preview_id)
}
