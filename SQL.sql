--用户表

create table user(
id bigint  auto_increment COMMENT '自动增长编号',
userId varchar(32) not null COMMENT '用户编号',
userName varchar(150) not null COMMENT '用户昵称',
userHeadImageUrl varchar(150) not null COMMENT '用户头像地址',
primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

ALTER table  user add password varchar(32) not null COMMENT '用户密码';

ALTER table  user add token varchar(128)  COMMENT '用户登录令牌';



--视频表

CREATE TABLE `video` (
  `id` varchar(100) NOT NULL COMMENT '主键',
  `name` varchar(150) NOT NULL COMMENT '文件名称',
  `type` varchar(36) NOT NULL COMMENT '视频类型 1 电影 2 电视剧',
  `tag` varchar(36) NOT NULL COMMENT '视频分类 1 热血 2 犯罪',
  `title` varchar(150) NOT NULL COMMENT '标题',
  `year` varchar(8) NOT NULL DEFAULT '未知' COMMENT '年份',
  `coverImageUrl` varchar(36) NOT NULL DEFAULT '' COMMENT '上传文件封面图片',
  `duration` varchar(36) NOT NULL DEFAULT '0' COMMENT '时长',
  `playUrl` varchar(36) NOT NULL DEFAULT '' COMMENT '播放地址',
  `suffix` varchar(12) NOT NULL DEFAULT '' COMMENT '格式后缀',
  `watchCount` bigint(20) NOT NULL DEFAULT '0' COMMENT '观看次数',
  `commentCount` bigint(20) NOT NULL DEFAULT '0' COMMENT '评论次数',
  `startNumber` int(11) NOT NULL DEFAULT '0' COMMENT '星星个数',
  `likeCount` bigint(20) NOT NULL DEFAULT '0' COMMENT '点赞次数',
  `notLikeCount` bigint(20) NOT NULL DEFAULT '0' COMMENT '点非赞次数',
  `introduction` text COMMENT '简介',
  `associatedCommentsId` varchar(36) DEFAULT NULL COMMENT '评论关联编号',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间,文件上传时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视频信息表'

