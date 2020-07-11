
--图片前端位置表
CREATE TABLE `imageUi` (
  `id` varchar(100) NOT NULL COMMENT '主键',
  `imageUrl` varchar(36) NOT NULL COMMENT '图片显示的地址',
  `associatedImageUiSettingId` varchar(36) DEFAULT NULL COMMENT '关联图片设置编号',
  `associatedVideoId` varchar(36) DEFAULT NULL COMMENT '关联视频编号',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片前端位置表'


--图片参数设置表
CREATE TABLE `imageUiSetting` (
  `id` varchar(100) NOT NULL COMMENT '主键',
  `imageLocation` varchar(150) NOT NULL COMMENT '图片前端位置',
  `width` varchar(20) NOT NULL COMMENT '图片宽度',
  `hight` varchar(26) NOT NULL COMMENT '图片高度',
  `remark` varchar(26) NOT NULL COMMENT '说明',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `imageLocation` (`imageLocation`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片参数设置表'


--- 图片前端位置表
CREATE TABLE `imageui` (
  `id` varchar(100) NOT NULL COMMENT '主键',
  `imageUrl` varchar(255) NOT NULL COMMENT '图片显示的地址',
  `associatedImageUiSettingId` varchar(36) DEFAULT NULL COMMENT '关联图片设置编号',
  `associatedVideoId` varchar(36) DEFAULT NULL COMMENT '关联视频编号',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片前端位置表'


----图片参数设置表
CREATE TABLE `imageuisetting` (
  `id` varchar(100) NOT NULL COMMENT '主键',
  `imageLocation` varchar(150) NOT NULL COMMENT '图片前端位置',
  `width` int(11) NOT NULL COMMENT '图片宽度',
  `high` int(11) NOT NULL COMMENT '图片高度',
  `weight` int(11) NOT NULL DEFAULT '1' COMMENT '所占比重',
  `listLocation` int(11) NOT NULL DEFAULT '0' COMMENT '返回前端的列表位置',
  `remark` varchar(26) NOT NULL COMMENT '说明',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `imageLocation` (`imageLocation`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片参数设置表'


----用户表
CREATE TABLE `user` (
  `id` varchar(50) NOT NULL COMMENT '用户编号',
  `userName` varchar(50) NOT NULL COMMENT '用户昵称',
  `password` varchar(50) NOT NULL COMMENT '登录密码',
  `email` varchar(50) NOT NULL COMMENT '关联邮箱',
  `userHeadImageUrl` varchar(150) NOT NULL COMMENT '用户头像地址',
  `token` varchar(150) DEFAULT NULL COMMENT '用户登录令牌',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userName` (`userName`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表'

-----视频信息表
CREATE TABLE `video` (
  `id` varchar(100) NOT NULL COMMENT '主键',
  `name` varchar(150) NOT NULL COMMENT '视频名称',
  `fileName` varchar(150) NOT NULL COMMENT '视频文件上传名称',
  `sourceLocation` varchar(100) NOT NULL COMMENT '视频保存位置',
  `type` varchar(36) NOT NULL COMMENT '视频类型 1 电影 2 电视剧',
  `tag` varchar(36) NOT NULL COMMENT '视频分类 1 热血 2 犯罪',
  `title` varchar(150) NOT NULL COMMENT '标题',
  `subtitle` varchar(100) NOT NULL COMMENT '视频子标题',
  `year` varchar(8) NOT NULL DEFAULT '未知' COMMENT '年份',
  `leadRole` varchar(36) NOT NULL COMMENT '视频主演',
  `defaultImageUrl` varchar(255) NOT NULL COMMENT '视频默认的图片',
  `duration` varchar(36) NOT NULL DEFAULT '0' COMMENT '时长',
  `playUrl` varchar(255) NOT NULL COMMENT '播放地址',
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

-----评论表
CREATE TABLE `videocomment` (
  `id` varchar(100) NOT NULL COMMENT '编号',
  `videoId` varchar(50) NOT NULL COMMENT '评论编号',
  `commentSessionId` varchar(50) NOT NULL COMMENT '评论会话编号',
  `parentId` varchar(50) DEFAULT NULL COMMENT '指向父级评论编号',
  `userId` varchar(50) NOT NULL COMMENT '用户编号',
  `content` text NOT NULL COMMENT '评论内容',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论表'


-----视频操作表
CREATE TABLE `videooperation` (
  `id` varchar(100) NOT NULL COMMENT '主键',
  `associatedVideoId` varchar(150) NOT NULL COMMENT '关联的视频编号',
  `associatedUserId` varchar(150) NOT NULL COMMENT '关联的用户编号',
  `like` int(11) NOT NULL COMMENT '0 未点赞 1点赞',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `associateId` (`associatedVideoId`,`associatedUserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视频操作表'


-----用户历史观看记录
CREATE TABLE `userPlayVideoLog` (
  `id` varchar(100) NOT NULL COMMENT '主键',
  `associatedVideoId` varchar(150) NOT NULL COMMENT '关联的视频编号',
  `associatedUserId` varchar(150) NOT NULL COMMENT '关联的用户编号',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户历史观看记录'