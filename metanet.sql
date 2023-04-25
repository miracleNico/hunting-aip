-- MySQL dump 10.13  Distrib 5.7.22, for Win64 (x86_64)
--
-- Host: 192.168.101.26    Database: metanet
-- ------------------------------------------------------
-- Server version	5.7.41

DROP DATABASE IF EXISTS metanet;
create database metanet DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
use metanet;

-- 删除
-- 1、积分
-- 2、奖品
-- 3、内容、内容商
-- 4、收益
-- 5、学习资源、年级、阶段
-- 6、家庭

DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
DROP TABLE IF EXISTS QRTZ_LOCKS;
DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_SIMPROP_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_JOB_DETAILS;
DROP TABLE IF EXISTS QRTZ_CALENDARS;

-- ----------------------------
-- 1、存储每一个已配置的 jobDetail 的详细信息
-- ----------------------------
create table QRTZ_JOB_DETAILS (
    sched_name           varchar(120)    not null            comment '调度名称',
    job_name             varchar(200)    not null            comment '任务名称',
    job_group            varchar(200)    not null            comment '任务组名',
    description          varchar(250)    null                comment '相关介绍',
    job_class_name       varchar(250)    not null            comment '执行任务类名称',
    is_durable           varchar(1)      not null            comment '是否持久化',
    is_nonconcurrent     varchar(1)      not null            comment '是否并发',
    is_update_data       varchar(1)      not null            comment '是否更新数据',
    requests_recovery    varchar(1)      not null            comment '是否接受恢复执行',
    job_data             blob            null                comment '存放持久化job对象',
    primary key (sched_name, job_name, job_group)
) engine=innodb comment = '任务详细信息表';

-- ----------------------------
-- 2、 存储已配置的 Trigger 的信息
-- ----------------------------
create table QRTZ_TRIGGERS (
    sched_name           varchar(120)    not null            comment '调度名称',
    trigger_name         varchar(200)    not null            comment '触发器的名字',
    trigger_group        varchar(200)    not null            comment '触发器所属组的名字',
    job_name             varchar(200)    not null            comment 'qrtz_job_details表job_name的外键',
    job_group            varchar(200)    not null            comment 'qrtz_job_details表job_group的外键',
    description          varchar(250)    null                comment '相关介绍',
    next_fire_time       bigint(13)      null                comment '上一次触发时间（毫秒）',
    prev_fire_time       bigint(13)      null                comment '下一次触发时间（默认为-1表示不触发）',
    priority             integer         null                comment '优先级',
    trigger_state        varchar(16)     not null            comment '触发器状态',
    trigger_type         varchar(8)      not null            comment '触发器的类型',
    start_time           bigint(13)      not null            comment '开始时间',
    end_time             bigint(13)      null                comment '结束时间',
    calendar_name        varchar(200)    null                comment '日程表名称',
    misfire_instr        smallint(2)     null                comment '补偿执行的策略',
    job_data             blob            null                comment '存放持久化job对象',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, job_name, job_group) references QRTZ_JOB_DETAILS(sched_name, job_name, job_group)
) engine=innodb comment = '触发器详细信息表';

-- ----------------------------
-- 3、 存储简单的 Trigger，包括重复次数，间隔，以及已触发的次数
-- ----------------------------
create table QRTZ_SIMPLE_TRIGGERS (
    sched_name           varchar(120)    not null            comment '调度名称',
    trigger_name         varchar(200)    not null            comment 'qrtz_triggers表trigger_name的外键',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggers表trigger_group的外键',
    repeat_count         bigint(7)       not null            comment '重复的次数统计',
    repeat_interval      bigint(12)      not null            comment '重复的间隔时间',
    times_triggered      bigint(10)      not null            comment '已经触发的次数',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references QRTZ_TRIGGERS(sched_name, trigger_name, trigger_group)
) engine=innodb comment = '简单触发器的信息表';

-- ----------------------------
-- 4、 存储 Cron Trigger，包括 Cron 表达式和时区信息
-- ---------------------------- 
create table QRTZ_CRON_TRIGGERS (
    sched_name           varchar(120)    not null            comment '调度名称',
    trigger_name         varchar(200)    not null            comment 'qrtz_triggers表trigger_name的外键',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggers表trigger_group的外键',
    cron_expression      varchar(200)    not null            comment 'cron表达式',
    time_zone_id         varchar(80)                         comment '时区',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references QRTZ_TRIGGERS(sched_name, trigger_name, trigger_group)
) engine=innodb comment = 'Cron类型的触发器表';

-- ----------------------------
-- 5、 Trigger 作为 Blob 类型存储(用于 Quartz 用户用 JDBC 创建他们自己定制的 Trigger 类型，JobStore 并不知道如何存储实例的时候)
-- ---------------------------- 
create table QRTZ_BLOB_TRIGGERS (
    sched_name           varchar(120)    not null            comment '调度名称',
    trigger_name         varchar(200)    not null            comment 'qrtz_triggers表trigger_name的外键',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggers表trigger_group的外键',
    blob_data            blob            null                comment '存放持久化Trigger对象',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references QRTZ_TRIGGERS(sched_name, trigger_name, trigger_group)
) engine=innodb comment = 'Blob类型的触发器表';

-- ----------------------------
-- 6、 以 Blob 类型存储存放日历信息， quartz可配置一个日历来指定一个时间范围
-- ---------------------------- 
create table QRTZ_CALENDARS (
    sched_name           varchar(120)    not null            comment '调度名称',
    calendar_name        varchar(200)    not null            comment '日历名称',
    calendar             blob            not null            comment '存放持久化calendar对象',
    primary key (sched_name, calendar_name)
) engine=innodb comment = '日历信息表';

-- ----------------------------
-- 7、 存储已暂停的 Trigger 组的信息
-- ---------------------------- 
create table QRTZ_PAUSED_TRIGGER_GRPS (
    sched_name           varchar(120)    not null            comment '调度名称',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggers表trigger_group的外键',
    primary key (sched_name, trigger_group)
) engine=innodb comment = '暂停的触发器表';

-- ----------------------------
-- 8、 存储与已触发的 Trigger 相关的状态信息，以及相联 Job 的执行信息
-- ---------------------------- 
create table QRTZ_FIRED_TRIGGERS (
    sched_name           varchar(120)    not null            comment '调度名称',
    entry_id             varchar(95)     not null            comment '调度器实例id',
    trigger_name         varchar(200)    not null            comment 'qrtz_triggers表trigger_name的外键',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggers表trigger_group的外键',
    instance_name        varchar(200)    not null            comment '调度器实例名',
    fired_time           bigint(13)      not null            comment '触发的时间',
    sched_time           bigint(13)      not null            comment '定时器制定的时间',
    priority             integer         not null            comment '优先级',
    state                varchar(16)     not null            comment '状态',
    job_name             varchar(200)    null                comment '任务名称',
    job_group            varchar(200)    null                comment '任务组名',
    is_nonconcurrent     varchar(1)      null                comment '是否并发',
    requests_recovery    varchar(1)      null                comment '是否接受恢复执行',
    primary key (sched_name, entry_id)
) engine=innodb comment = '已触发的触发器表';

-- ----------------------------
-- 9、 存储少量的有关 Scheduler 的状态信息，假如是用于集群中，可以看到其他的 Scheduler 实例
-- ---------------------------- 
create table QRTZ_SCHEDULER_STATE (
    sched_name           varchar(120)    not null            comment '调度名称',
    instance_name        varchar(200)    not null            comment '实例名称',
    last_checkin_time    bigint(13)      not null            comment '上次检查时间',
    checkin_interval     bigint(13)      not null            comment '检查间隔时间',
    primary key (sched_name, instance_name)
) engine=innodb comment = '调度器状态表';

-- ----------------------------
-- 10、 存储程序的悲观锁的信息(假如使用了悲观锁)
-- ---------------------------- 
create table QRTZ_LOCKS (
    sched_name           varchar(120)    not null            comment '调度名称',
    lock_name            varchar(40)     not null            comment '悲观锁名称',
    primary key (sched_name, lock_name)
) engine=innodb comment = '存储的悲观锁信息表';

-- ----------------------------
-- 11、 Quartz集群实现同步机制的行锁表
-- ---------------------------- 
create table QRTZ_SIMPROP_TRIGGERS (
    sched_name           varchar(120)    not null            comment '调度名称',
    trigger_name         varchar(200)    not null            comment 'qrtz_triggers表trigger_name的外键',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggers表trigger_group的外键',
    str_prop_1           varchar(512)    null                comment 'String类型的trigger的第一个参数',
    str_prop_2           varchar(512)    null                comment 'String类型的trigger的第二个参数',
    str_prop_3           varchar(512)    null                comment 'String类型的trigger的第三个参数',
    int_prop_1           int             null                comment 'int类型的trigger的第一个参数',
    int_prop_2           int             null                comment 'int类型的trigger的第二个参数',
    long_prop_1          bigint          null                comment 'long类型的trigger的第一个参数',
    long_prop_2          bigint          null                comment 'long类型的trigger的第二个参数',
    dec_prop_1           numeric(13,4)   null                comment 'decimal类型的trigger的第一个参数',
    dec_prop_2           numeric(13,4)   null                comment 'decimal类型的trigger的第二个参数',
    bool_prop_1          varchar(1)      null                comment 'Boolean类型的trigger的第一个参数',
    bool_prop_2          varchar(1)      null                comment 'Boolean类型的trigger的第二个参数',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references QRTZ_TRIGGERS(sched_name, trigger_name, trigger_group)
) engine=innodb comment = '同步机制的行锁表';

commit;

--
-- Table structure for table `sql_log`
--

DROP TABLE IF EXISTS `sql_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sql_log` (
  `name` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `content` text COLLATE utf8_bin,
  `code` int(10) DEFAULT NULL,
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_admin`
--

DROP TABLE IF EXISTS `t_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_admin` (
  `admin_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '管理员id',
  `username` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '账号',
  `password` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `nick_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `province_id` varchar(6) COLLATE utf8_bin DEFAULT NULL COMMENT '省',
  `city_id` varchar(6) COLLATE utf8_bin DEFAULT NULL COMMENT '城市',
  `county_id` varchar(6) COLLATE utf8_bin DEFAULT 'CHN' COMMENT '县',
  `zip_code` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '邮编',
  `email` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `gender` enum('0','1','2') COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '性别-0女/1男/2保密',
  `phone_code` varchar(4) COLLATE utf8_bin DEFAULT NULL COMMENT '手机国家区域号',
  `phone` varchar(11) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号码',
  `age` double(3,0) unsigned DEFAULT NULL COMMENT '年龄',
  `hobby` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '爱好-多个用/分割',
  `head_url` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '头像路径',
  `last_login_time` timestamp(6) NULL DEFAULT NULL COMMENT '最后登录时间',
  `enable_status` bit(1) NOT NULL DEFAULT b'1' COMMENT '启用状态-0禁用/1启用',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='管理员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_agent`
--

DROP TABLE IF EXISTS `t_agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_agent` (
  `agent_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '代理id',
  `agent_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '代理人',
  `agent_code` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '推广码',
  `username` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '账号',
  `password` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '密码',
  `tel` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `agent_level` enum('BOARD','HDMASTER','TEACHER') COLLATE utf8_bin NOT NULL COMMENT '代理级别',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '上级代理',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`agent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='代理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_agent_number`
--

DROP TABLE IF EXISTS `t_agent_number`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_agent_number` (
  `agent_number_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '代理台数id',
  `agent_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '代理id',
  `current_number` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '当前台数',
  `total_number` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '总台数',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  PRIMARY KEY (`agent_number_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='代理台数';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_agent_number_detail`
--

DROP TABLE IF EXISTS `t_agent_number_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_agent_number_detail` (
  `agent_number_detail_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '代理台数明细id',
  `agent_number_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '代理台数id',
  `change_value` int(10) NOT NULL COMMENT '交易值',
  `after_value` int(10) NOT NULL COMMENT '交易后的值',
  `change_type` enum('JOINSALE','PAY','SALE') COLLATE utf8_bin NOT NULL COMMENT '交易类型',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  PRIMARY KEY (`agent_number_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='代理台数明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_app`
--

DROP TABLE IF EXISTS `t_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_app` (
  `app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '产品id',
  `channel_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '渠道id',
  `app_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '产品名称',
  `package_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '包名',
  `auth_type` enum('MAC','SN','MACSN') COLLATE utf8_bin NOT NULL DEFAULT 'MACSN' COMMENT '认证方式',
  `app_type` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '软件类型-0APP/1固件',
  `instruction` text COLLATE utf8_bin NOT NULL COMMENT '产品说明',
  `enable_sn` bit(1) NOT NULL DEFAULT b'1' COMMENT '启动激活码激活',
  `app_key` varchar(32) COLLATE utf8_bin NOT NULL,
  `app_secret` varchar(32) COLLATE utf8_bin NOT NULL,
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`app_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='产品';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `t_app_version`
--

DROP TABLE IF EXISTS `t_app_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_app_version` (
  `version_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '版本id',
  `version_code` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '版本号',
  `version_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '版本名称',
  `app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '产品id',
  `instruction` text COLLATE utf8_bin NOT NULL COMMENT '更新说明',
  `file_size` decimal(19,2) DEFAULT NULL COMMENT '文件大小-单位b',
  `md5` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'md5值',
  `version_type` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '版本类型-0全量包/1拆分包',
  `url` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '文件地址',
  `url_type` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '文件地址类型-0内部/1外部',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`version_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='产品版本';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_app_version_module`
--

DROP TABLE IF EXISTS `t_app_version_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_app_version_module` (
  `version_module_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '版本模块id',
  `version_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '版本id',
  `module_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '模块名称',
  `call_type` enum('SDK','APK','MP4') COLLATE utf8_bin NOT NULL DEFAULT 'APK' COMMENT '调用类型',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '父级id',
  `module_icon` text COLLATE utf8_bin COMMENT '图标',
  `package_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '调用包名',
  `class_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '调用类名',
  `parameter` json DEFAULT NULL COMMENT '参数',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '序号',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`version_module_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='版本模块';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_article_exercise`
--

DROP TABLE IF EXISTS `t_article_exercise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_article_exercise` (
  `article_exercise_id` varchar(64) NOT NULL COMMENT '文章和锻炼绑定ID',
  `eyes_article_id` varchar(64) NOT NULL COMMENT '文章ID',
  `exercise_id` varchar(64) NOT NULL COMMENT '锻炼ID',
  `status` bit(1) NOT NULL COMMENT '状态',
  PRIMARY KEY (`article_exercise_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章和锻炼关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_auth_admin_role`
--

DROP TABLE IF EXISTS `t_auth_admin_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_auth_admin_role` (
  `admin_role_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户角色id',
  `admin_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '管理员id或渠道id',
  `role_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '角色id',
  PRIMARY KEY (`admin_role_id`) USING BTREE,
  UNIQUE KEY `admin_id` (`admin_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='用户角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_auth_permission`
--

DROP TABLE IF EXISTS `t_auth_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_auth_permission` (
  `permission_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '权限id',
  `permission_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '权限名称',
  `permission_type` enum('MODULE','MENU','BTN','FN') COLLATE utf8_bin NOT NULL COMMENT '权限类型',
  `permission_tag` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '权限标识',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '父级id',
  `apis` text COLLATE utf8_bin NOT NULL COMMENT '多个用逗号分隔',
  `sort` int(11) NOT NULL DEFAULT '1' COMMENT '序号',
  `icon` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'icon',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='权限';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_auth_role`
--

DROP TABLE IF EXISTS `t_auth_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_auth_role` (
  `role_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '角色id',
  `role_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `role_type` enum('ADMIN','CHANNEL') COLLATE utf8_bin NOT NULL DEFAULT 'ADMIN' COMMENT '角色类型-ADMIN:平台/CHANNEL:渠道',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_auth_role_permission`
--

DROP TABLE IF EXISTS `t_auth_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_auth_role_permission` (
  `role_permission_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '角色权限id',
  `role_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '角色id',
  `permission_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '权限id',
  PRIMARY KEY (`role_permission_id`),
  UNIQUE KEY `role_id` (`role_id`,`permission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色权限';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `t_brand`
--

DROP TABLE IF EXISTS `t_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_brand` (
  `brand_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '品牌id-BD1000',
  `brand_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '品牌名称-唯一',
  `source` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '来源-0导入/1记录',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`brand_id`) USING BTREE,
  UNIQUE KEY `brand_name` (`brand_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='品牌';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `t_channel`
--

DROP TABLE IF EXISTS `t_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_channel` (
  `channel_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '渠道id-CH1000',
  `channel_name` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '渠道名称-唯一',
  `username` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '账号',
  `password` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '密码',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`channel_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='渠道';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_device`
--

DROP TABLE IF EXISTS `t_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_device` (
  `device_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '设备id',
  `device_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '设备名称',
  `wired_mac` varchar(17) CHARACTER SET utf8 DEFAULT NULL COMMENT '有线mac',
  `wireless_mac` varchar(17) CHARACTER SET utf8 DEFAULT NULL COMMENT '无线mac',
  `imei` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT 'IMEI号',
  `imei2` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT 'IMEI2号',
  `bluetooth` varchar(17) COLLATE utf8_bin DEFAULT NULL COMMENT '蓝牙',
  `serial_number` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '终端序列号',
  `uuid` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '终端uuid',
  `brand_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '品牌id',
  `model_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '型号id',
  `firmware_info` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '固件信息',
  `source` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '来源-0导入/1记录',
  `enable_status` bit(1) NOT NULL DEFAULT b'1' COMMENT '启用状态-0禁用/1启用',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  `tag` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '0虚拟/1真实',
  PRIMARY KEY (`device_id`) USING BTREE,
  KEY `brand_id` (`brand_id`) USING BTREE,
  KEY `model_id` (`model_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='设备';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_device_app`
--

DROP TABLE IF EXISTS `t_device_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_device_app` (
  `device_app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `device_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '设备id',
  `app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '产品id',
  `sn_code` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'SN码',
  `activate_time` timestamp(6) NULL DEFAULT NULL COMMENT '激活时间',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `tag` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '0虚拟/1真实',
  PRIMARY KEY (`device_app_id`) USING BTREE,
  KEY `device_id` (`device_id`) USING BTREE,
  KEY `app_id` (`app_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='设备产品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_eye`
--

DROP TABLE IF EXISTS `t_eye`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_eye` (
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户序列号',
  `type` int(11) DEFAULT '1' COMMENT '类型（0左眼,1右眼）',
  `height` decimal(8,2) DEFAULT NULL COMMENT '眼睛高度（毫米）',
  `width` decimal(8,2) DEFAULT NULL COMMENT '眼睛宽度（毫米）',
  `pupi_distance` decimal(8,2) DEFAULT NULL COMMENT '瞳距（毫米）',
  `length_of_optic_axis` decimal(8,2) DEFAULT NULL COMMENT '眼轴（毫米）',
  `corneal_diameter` decimal(8,2) DEFAULT NULL COMMENT '角膜直径（毫米）',
  `corneal_thickness` decimal(8,2) DEFAULT NULL COMMENT '角膜厚度（微米）',
  `pupil_diameter` decimal(8,2) DEFAULT NULL COMMENT '瞳孔直径（毫米）',
  `intraocular_pressure` decimal(8,2) DEFAULT NULL COMMENT '眼压IOP（mmHg）',
  `radius_curvature` decimal(8,2) DEFAULT NULL COMMENT '曲率半径（毫米）',
  `refractive_index_for_eyeball` decimal(8,2) DEFAULT NULL COMMENT '介质折射率',
  `radius_curvature_for_retina` decimal(8,2) DEFAULT NULL COMMENT '视网膜曲率半径（毫米）',
  `aperture_for_high_light` decimal(8,2) DEFAULT NULL COMMENT '强光光圈',
  `aperture_for_low_light` decimal(8,2) DEFAULT NULL COMMENT '低光光圈',
  `focus_distance_for_object_space` decimal(8,2) DEFAULT NULL COMMENT '物方焦距（毫米）',
  `focus_distance_for_image_space` decimal(8,2) DEFAULT NULL COMMENT '像方焦距（毫米）',
  `focal_power_for_image_space` decimal(8,2) DEFAULT NULL COMMENT '像方光焦度',
  `owner_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '眼睛拥有者ID，用户ID，来自第三方系统',
  `owner_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '眼睛拥有者姓名，用户姓名，来自第三方系统',
  `owner_tel` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '眼睛拥有者电话，用户电话，来自第三方系统',
  `owner_age` int(11) DEFAULT NULL COMMENT '眼睛拥有者年龄，用户年龄，来自第三方系统',
  `owner_height` decimal(8,2) DEFAULT NULL COMMENT '眼睛拥有者身高，用户身高，单位厘米，来自第三方系统',
  `eye_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '序列号',
  `remark` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`eye_id`),
  KEY `FK_user_eye` (`user_id`),
  CONSTRAINT `FK_user_eye` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='眼睛';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_eyes_article`
--

DROP TABLE IF EXISTS `t_eyes_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_eyes_article` (
  `eyes_article_id` varchar(50) NOT NULL COMMENT '爱眼知识ID',
  `eyes_article_title` varchar(200) NOT NULL COMMENT '爱眼知识标题',
  `eyes_article_exercise_type` int(255) NOT NULL COMMENT '内容锻炼类型 0眼保健操、1眼肌锻炼、2眼球放松、3新型眼操',
  `eyes_article_type` varchar(20) NOT NULL COMMENT '爱眼知识类型-ARTICLE:文章/VIDEO：视频',
  `eyes_article_content` longtext COMMENT '爱眼知识文章',
  `eyes_article_img` varchar(200) NOT NULL COMMENT '爱眼知识图片',
  `eyes_article_url` varchar(200) NOT NULL COMMENT '爱眼知识地址',
  `release_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '发布时间',
  `release_number` int(11) NOT NULL DEFAULT '0' COMMENT '浏览次数',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`eyes_article_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='爱眼软文';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_eyestrain`
--

DROP TABLE IF EXISTS `t_eyestrain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_eyestrain` (
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户序列号，关联用户信息',
  `eye_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '眼睛序列号，关联用户眼睛信息，备用',
  `date_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '用眼日期和时间',
  `use_type` int(11) NOT NULL COMMENT '用眼类型（-1所有类型、0看书、1看黑板、2看手机、3看电脑、4看平板、5看电视、6其他），默认为0',
  `time_duration` int(11) NOT NULL COMMENT '持续时长（单位秒）',
  `use_mode` int(11) DEFAULT NULL COMMENT '用眼模式（-1所有模式，0自我监督模式，1家长协助模式，2医生协助模式），默认为0',
  `eyestrain_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用眼活动序列号',
  `remark` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`eyestrain_id`),
  KEY `FK_user_eyestrain` (`user_id`),
  CONSTRAINT `FK_user_eyestrain` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='眼疲劳记录（用眼活动）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_feedback`
--

DROP TABLE IF EXISTS `t_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_feedback` (
  `feedback_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '反馈id',
  `package_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '包名',
  `version_code` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '版本',
  `content` text COLLATE utf8_bin NOT NULL COMMENT '内容',
  `qq` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'qq',
  `vx` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'vx',
  `tel` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'tel',
  `feedback_option_content1` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '选项1',
  `feedback_option_content2` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '选项2',
  `uuid` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '终端序列号UUID',
  `mac` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'MAC地址',
  `brand_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌',
  `model_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '型号',
  `process_status` enum('NO','YES') COLLATE utf8_bin NOT NULL DEFAULT 'NO' COMMENT '处理状态',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`feedback_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='反馈';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_feedback_option`
--

DROP TABLE IF EXISTS `t_feedback_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_feedback_option` (
  `feedback_option_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '反馈选项id',
  `feedback_option_content` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '反馈选项内容',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '父级内容',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  PRIMARY KEY (`feedback_option_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='反馈选项';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_model`
--

DROP TABLE IF EXISTS `t_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_model` (
  `model_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '型号id-MD1000',
  `brand_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '品牌id',
  `model_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '型号名称-某个品牌下型号名称是唯一的',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `source` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '来源-0导入/1记录',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`model_id`) USING BTREE,
  UNIQUE KEY `model_name-brand_id` (`model_name`,`brand_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='品牌型号';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_open_app`
--

DROP TABLE IF EXISTS `t_open_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_open_app` (
  `app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '应用id',
  `app_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '应用名称',
  `app_key` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '应用秘钥(AES)',
  `channel_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '渠道id',
  `sign_device_number` int(10) unsigned NOT NULL DEFAULT '200' COMMENT '签约设备数',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='开放平台应用';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_open_device`
--

DROP TABLE IF EXISTS `t_open_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_open_device` (
  `open_device_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'id',
  `device_id` varchar(500) COLLATE utf8_bin NOT NULL COMMENT '设备唯一标识',
  `app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '应用id',
  `brand` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌',
  `model` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '型号',
  `mac` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'mac',
  `sn` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '序列号',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '启用状态',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`open_device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='开放平台设备';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_region`
--

DROP TABLE IF EXISTS `t_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_region` (
  `region_id` varchar(6) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `pid` varchar(6) COLLATE utf8_bin NOT NULL COMMENT '父级编号',
  `name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `region_code` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '行政区划码',
  `province_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '省级',
  `city_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '市级',
  `county_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '县级',
  `area_code` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '区号',
  `zip_code` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '邮编',
  `short_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '简称',
  `pinyin` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '拼音',
  `short_pinyin` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '简拼',
  `first_letter` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '首字母',
  `english` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '英文',
  `gd_jd` decimal(20,17) DEFAULT NULL COMMENT '经度',
  `gd_wd` decimal(20,17) DEFAULT NULL COMMENT '维度',
  `province_short_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '省级简称',
  `city_short_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '地级简称',
  `county_short_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '县级简称',
  `province_pinyin` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '省级拼音',
  `city_pinyin` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '地级拼音',
  `county_pinyin` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '县级拼音',
  `pids` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '父级路径',
  `level` tinyint(1) DEFAULT NULL COMMENT '深度',
  `full_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '全路径',
  PRIMARY KEY (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='地区';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_sequence`
--

DROP TABLE IF EXISTS `t_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sequence` (
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `prefix` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `current_value` int(11) NOT NULL,
  `increment` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='序列';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_serial_number`
--

DROP TABLE IF EXISTS `t_serial_number`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_serial_number` (
  `sn_code` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'sn码',
  `use_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '使用状态-0未使用/1已使用',
  `max_use_number` decimal(5,0) unsigned NOT NULL DEFAULT '1' COMMENT '最大使用次数',
  `use_number` decimal(5,0) unsigned NOT NULL DEFAULT '0' COMMENT '使用次数',
  `app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '产品id(仅适用于此产品)',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`sn_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='SN码';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_statistical_app_active`
--

DROP TABLE IF EXISTS `t_statistical_app_active`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_statistical_app_active` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `app_id` varchar(64) COLLATE utf8_bin NOT NULL,
  `number` int(11) unsigned NOT NULL DEFAULT '0',
  `tag` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '0虚拟/1真实',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `date` (`date`,`app_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=27352 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='统计产品日活用户数';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_store_app`
--

DROP TABLE IF EXISTS `t_store_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_store_app` (
  `store_app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '应用商店id',
  `channel_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '渠道id',
  `app_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '应用名称',
  `package_name` varchar(500) COLLATE utf8_bin NOT NULL COMMENT '包名',
  `call_class` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '调用类名',
  `file_size` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '文件大小(b)字节',
  `version_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '版本名',
  `version_code` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '版本号',
  `url` varchar(1000) COLLATE utf8_bin NOT NULL COMMENT '文件地址',
  `md5` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'md5值',
  `icon` varchar(1000) COLLATE utf8_bin NOT NULL COMMENT '图标',
  `instruction` text COLLATE utf8_bin COMMENT '应用说明',
  `app_scope` enum('0','1') COLLATE utf8_bin DEFAULT NULL COMMENT '应用范围-0所有/1指定',
  `download_number` int(11) DEFAULT NULL COMMENT '下载次数',
  `release_time` date DEFAULT NULL COMMENT '发布时间',
  `developer` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '开发商',
  `images` text COLLATE utf8_bin COMMENT '应用截图',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`store_app_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='应用商店';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_store_app_scope`
--

DROP TABLE IF EXISTS `t_store_app_scope`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_store_app_scope` (
  `store_app_scope_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `store_app_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '应用商店id',
  `app_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '产品id',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  PRIMARY KEY (`store_app_scope_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='应用商店产品范围';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_upgrade_plan`
--

DROP TABLE IF EXISTS `t_upgrade_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_upgrade_plan` (
  `upgrade_plan_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '升级计划id',
  `upgrade_plan_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '升级名称',
  `upgrade_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '升级时间-默认当前时间',
  `app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '产品id',
  `start_version_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '产品版本范围start',
  `end_version_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '产品版本范围end',
  `to_version_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '升级至产品版本',
  `device_scope` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '设备范围-0所有/1指定',
  `enforce` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否强制升级-0否/1是',
  `send_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '发布状态-0未发布/1已发布',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`upgrade_plan_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='设备升级计划';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_upgrade_plan_scope`
--

DROP TABLE IF EXISTS `t_upgrade_plan_scope`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_upgrade_plan_scope` (
  `upgrade_plan_scope_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `upgrade_plan_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '升级计划id',
  `brand_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '品牌id',
  `model_scope` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '型号范围-0所有/1指定',
  `models` text COLLATE utf8_bin COMMENT '型号id-多个以,号分隔',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  PRIMARY KEY (`upgrade_plan_scope_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='设备升级计划范围';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user` (
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `app_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '产品id',
  `username` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '账号',
  `password` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `province_id` varchar(6) COLLATE utf8_bin DEFAULT NULL COMMENT '省id',
  `city_id` varchar(6) COLLATE utf8_bin DEFAULT NULL COMMENT '市id',
  `county_id` varchar(6) COLLATE utf8_bin DEFAULT NULL COMMENT '县id',
  `zip_code` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '邮编',
  `email` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `gender` enum('0','1','2') COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '性别-0女/1男/2保密',
  `phone` varchar(11) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号码',
  `age` double(3,0) unsigned DEFAULT NULL COMMENT '年龄',
  `hobby` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '爱好-多个用/分割',
  `head_url` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '头像路径',
  `enable_status` bit(1) NOT NULL DEFAULT b'1' COMMENT '启用状态-0禁用/1启用',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  `tag` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '0虚拟/1真实',
  PRIMARY KEY (`user_id`) USING BTREE,
  KEY `app_id` (`app_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_address`
--

DROP TABLE IF EXISTS `t_user_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user_address` (
  `user_address_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'id',
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `consignee` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '收货人',
  `phone` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '电话号码',
  `province_id` varchar(6) COLLATE utf8_bin NOT NULL COMMENT '省id',
  `city_id` varchar(6) COLLATE utf8_bin NOT NULL COMMENT '市id',
  `county_id` varchar(6) COLLATE utf8_bin NOT NULL COMMENT '县id',
  `address` varchar(500) COLLATE utf8_bin NOT NULL COMMENT '收货地址',
  `zip_code` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '邮编',
  `default_status` bit(1) NOT NULL DEFAULT b'1' COMMENT '默认状态',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  PRIMARY KEY (`user_address_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='用户收货地址';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_login`
--

DROP TABLE IF EXISTS `t_user_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user_login` (
  `user_login_id` varchar(64) COLLATE utf8_bin NOT NULL,
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL,
  `device_id` varchar(64) COLLATE utf8_bin NOT NULL,
  `version_id` varchar(64) COLLATE utf8_bin NOT NULL,
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `tag` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '0虚拟/1真实',
  PRIMARY KEY (`user_login_id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `version_id` (`version_id`) USING BTREE,
  KEY `device_id` (`device_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户登录记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_vision_exercise`
--

DROP TABLE IF EXISTS `t_vision_exercise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_vision_exercise` (
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户序列号，关联用户信息',
  `date_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '锻炼日期和时间',
  `exercise_type` int(11) NOT NULL COMMENT '锻炼类型（-1所有类型、0眼保健操、1眼肌锻炼、2眼球放松、3新型眼操、4其他），默认为0',
  `time_duration` int(11) NOT NULL COMMENT '持续时长（单位秒）',
  `exercise_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '锻炼序列号',
  `content_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '锻炼内容序列号',
  `remark` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`exercise_id`),
  KEY `FK_user_exer` (`user_id`),
  CONSTRAINT `FK_user_exer` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='视力锻炼';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_vision_plan`
--

DROP TABLE IF EXISTS `t_vision_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_vision_plan` (
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户序列号，关联用户信息',
  `plan_name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户计划名称',
  `start_date` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '计划开始时间，执行计划所规定的活动的开始时间，也是首次提醒的时间点',
  `end_date` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '计划结束时间，执行计划所规定的活动的结束开始时间，最后一次提醒的时间点不能超过计划结束时间',
  `plan_type` int(11) NOT NULL COMMENT '提醒类型（0年提醒,1月提醒,2周提醒,3天提醒,4小时提醒,5一次性提醒）',
  `action_mode` int(11) NOT NULL COMMENT '执行模式（0状态栏提醒,1弹出框提醒,2立即强制切换,3倒计时切换可取消）',
  `plan_action` int(11) DEFAULT NULL COMMENT '计划操作（0视力测试,1眼保健操,2眼肌锻炼,3眼球放松,4新型眼操,5其他）',
  `plan_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '计划序列号',
  `remark` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`plan_id`),
  KEY `FK_user_plan` (`user_id`),
  CONSTRAINT `FK_user_plan` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='视力计划';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_vision_report`
--

DROP TABLE IF EXISTS `t_vision_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_vision_report` (
  `test_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '测试序列号',
  `judgement_result` int(11) DEFAULT NULL COMMENT '判定结果（null未判定 0正常 1警戒 2危险 3高危 4其他异常）',
  `decimal_record` decimal(8,2) DEFAULT NULL COMMENT '小数记录',
  `logarithm_record` decimal(8,2) DEFAULT NULL COMMENT '对数记录，5分制',
  `diopter_for_sph` decimal(8,2) DEFAULT NULL COMMENT '球镜屈光度S(单位D)',
  `diopter_for_cyl` decimal(8,2) DEFAULT NULL COMMENT '柱镜屈光度C(单位D)',
  `corneal_diopter` decimal(8,2) DEFAULT NULL COMMENT '角膜屈光度(单位D)',
  `crystal_diopter` decimal(8,2) DEFAULT NULL COMMENT '晶体度(单位D)',
  `axis_degrees_for_astigmatism` decimal(8,2) DEFAULT NULL COMMENT '散光轴度A',
  `proposal` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT '建议或诊断',
  `report_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '报告序列号',
  `remark` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`report_id`),
  KEY `FK_vision_test` (`test_id`),
  CONSTRAINT `FK_vision_test` FOREIGN KEY (`test_id`) REFERENCES `t_vision_test` (`test_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='视力报告';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_vision_table`
--

DROP TABLE IF EXISTS `t_vision_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_vision_table` (
  `id` int(11) NOT NULL COMMENT '序号',
  `table_type` int(11) NOT NULL COMMENT '类型 0近视力表 1远视力表',
  `line_sn` int(11) NOT NULL COMMENT '行序号，从上数到下，0开始',
  `distance` double NOT NULL COMMENT '标准距离，单位米',
  `decimal_record` double NOT NULL COMMENT '小数计数',
  `logarithm_record` double NOT NULL COMMENT '对数计数',
  `sighting_mark_length` double NOT NULL COMMENT '视标边长，单位豪米',
  `remark` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='视力表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_vision_test`
--

DROP TABLE IF EXISTS `t_vision_test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_vision_test` (
  `eye_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '眼睛序列号',
  `date_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '测试日期和时间',
  `device_type` int(11) NOT NULL COMMENT '设备类型（0视力表灯箱、1手机、2平板、3书桌、4电视、5台灯、6其他）',
  `distance` decimal(8,2) NOT NULL DEFAULT '5.00' COMMENT '测试距离（单位米）',
  `env_light_intensity` decimal(8,2) DEFAULT NULL COMMENT '环境光照强度',
  `time_duration` int(11) DEFAULT NULL COMMENT '测试持续时长（单位秒）',
  `test_mode` int(11) NOT NULL DEFAULT '1' COMMENT '测试模式（0自测模式，1家长协助模式，2医生协助模式）',
  `test_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '测试序列号',
  `remark` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`test_id`),
  KEY `FK_eye_vision` (`eye_id`),
  CONSTRAINT `FK_eye_vision` FOREIGN KEY (`eye_id`) REFERENCES `t_eye` (`eye_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='视力测试活动';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `undo_log`
--

DROP TABLE IF EXISTS `undo_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `undo_log` (
  `branch_id` bigint(20) NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int(11) NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='AT transaction mode undo table';
/*!40101 SET character_set_client = @saved_cs_client */;

commit;
