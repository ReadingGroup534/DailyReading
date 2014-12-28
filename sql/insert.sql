/**
 * browse
**/
delete from browse;

insert into browse 
(browse_id, browse_value, description,dt_created, dt_updated)
values
(1,'逗乐','幽默笑话、经典段子',now(), now());

insert into browse
(browse_id, browse_value, description, dt_created, dt_updated)
values
(2,'散文','优美经典的优秀散文', now(), now());

insert into browse
(browse_id, browse_value, description, dt_created, dt_updated)
values
(3,'励志','激发正能量的文章', now(), now());

insert into browse
(browse_id, browse_value, description, dt_created, dt_updated)
values
(4,'专题','比如情人节专场', now(), now());