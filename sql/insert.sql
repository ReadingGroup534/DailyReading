/**
 * browse
**/
delete from browse;

insert into browse 
(browse_id, browse_value, description,dt_created, dt_updated)
values
(1,'情感治愈','关于情感类的阅读',now(), now());

insert into browse
(browse_id, browse_value, description, dt_created, dt_updated)
values
(2,'经典散文','优美经典的优秀散文', now(), now());

insert into browse
(browse_id, browse_value, description, dt_created, dt_updated)
values
(3,'心灵鸡汤','激发正能量的文章', now(), now());

insert into browse
(browse_id, browse_value, description, dt_created, dt_updated)
values
(4,'幽默笑话','经典的笑话收录', now(), now());