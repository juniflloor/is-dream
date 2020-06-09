--用户表

create table user(
id bigint  auto_increment COMMENT '自动增长编号',
userId varchar(32) not null COMMENT '用户编号',
userName varchar(150) not null COMMENT '用户昵称',
userHeadImageUrl varchar(150) not null COMMENT '用户头像地址',
primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

