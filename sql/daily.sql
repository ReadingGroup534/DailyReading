/**
*    最终显示给用户的数据
*/
drop table if exists article;

create table article(
    article_id            char(22)                default '' not null,
    active                enum('y','n')           default 'y' not null,   
    show_time             datetime                default '0000-00-00 00:00:00' not null,
    title                 varchar(255)            default '' not null,
    author                varchar(255)            default '' not null,
    article_type          enum('sanwen','gushi','lizhi','other')    default 'other' not null,
    abstracts             varchar(255)            default '' not null, 
    recommend_star        smallint unsigned       default 0   not null,
    praise_times	  int unsigned		  default 0   not null,	
    share_times		  int unsigned		  default 0   not null,
    scan_times            int unsigned            default 0   not null,   
    physical_path         varchar(255)            default '' not null,
    primary key (article_id)
)engine=innodb;

/**
*    显示之前需要整理的数据
*/
drop table if exists preview;

create table preview(
    preview_id            char(22)                default '' not null,
    active                enum('y','n')           default 'y' not null,   
    show_time             datetime                default '0000-00-00 00:00:00' not null,
    title                 varchar(255)            default '' not null,
    author                varchar(255)            default '' not null,
    article_type          enum('sanwen','gushi','lizhi','other')    default 'other' not null,
    abstracts             varchar(255)            default '' not null,
    recommend_star        smallint unsigned       default 0 not null,
    scan_times            int unsigned            default 0 not null,   
    physical_path         varchar(255)            default '' not null,
    dt_created             datetime        default '0000-00-00 00:00:00' not null,
    dt_updated             datetime        default '0000-00-00 00:00:00' not null,

    primary key (preview_id)
);

/**
 * 文章分类
 */
drop table if exists browse;
create table browse(
	browse_id		int     default 0  not null,
	browse_value	varchar(255) 	default '' not null,
        description     varchar(255)    default '',
        dt_created      datetime        default '0000-00-00 00:00:00' not null,
        dt_updated      timestamp,
	active		enum('y','n')	default 'y' not null,
	primary key (browse_id)
);

/**
* 爬虫抓取源
*/
drop table if exists crawler_source;
create table crawler_source(
	id	int(10)		auto_increment not null,
	url	varchar(255)	default '' not null,
	active	enum('y','n')	default 'y' not null,
	primary key (id),
	unique natural_key (url)
);
