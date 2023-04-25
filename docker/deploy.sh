#!/bin/sh

# 使用说明，用来提示输入参数
usage() {
	echo "Usage: sh 执行脚本.sh [port|tool|base|data|busi|stop|rm] 
	port：开启防火墙端口
	db：启动mysql，redis
	tool：启动nacos，seata
	base：启动nginx，gateway，auth，monitor，file，gen，job
	data：启动admin，device，user，vision
	busi：启动app，crm，channel，openapi，baas-vision
	stop：停止以上所有服务
	rm：移除以上所有服务"
	exit 1
}

# 开启所需端口
port(){
	firewall-cmd --add-port=80/tcp --permanent
	firewall-cmd --add-port=8080/tcp --permanent
	firewall-cmd --add-port=8848/tcp --permanent
	firewall-cmd --add-port=9848/tcp --permanent
	firewall-cmd --add-port=9849/tcp --permanent
	firewall-cmd --add-port=6379/tcp --permanent
	firewall-cmd --add-port=3306/tcp --permanent
	firewall-cmd --add-port=7091/tcp --permanent
	firewall-cmd --add-port=8091/tcp --permanent
	firewall-cmd --add-port=8701/tcp --permanent
	firewall-cmd --add-port=8702/tcp --permanent
	firewall-cmd --add-port=8703/tcp --permanent
	firewall-cmd --add-port=8704/tcp --permanent
	firewall-cmd --add-port=8601/tcp --permanent
	firewall-cmd --add-port=8602/tcp --permanent
	firewall-cmd --add-port=8603/tcp --permanent
	firewall-cmd --add-port=8604/tcp --permanent
	firewall-cmd --add-port=8605/tcp --permanent
	firewall-cmd --add-port=8606/tcp --permanent
	firewall-cmd --add-port=8607/tcp --permanent
	firewall-cmd --add-port=8608/tcp --permanent
	firewall-cmd --add-port=8609/tcp --permanent
	firewall-cmd --add-port=8610/tcp --permanent
	firewall-cmd --add-port=8611/tcp --permanent
	service firewalld restart
}
# 启动工具环境（必须）
db(){
	docker-compose up -d metanet-mysql metanet-redis
}

# 启动工具环境（必须）
tool(){
	docker-compose up -d metanet-nacos metanet-seata
}

# 启动基础服务（必须）
base(){
	docker-compose up -d metanet-nginx metanet-baas-gateway metanet-baas-auth metanet-baas-monitor metanet-baas-file metanet-baas-gen metanet-baas-job
}

# 启动数据层服务（必须）
data(){
	docker-compose up -d metanet-daas-admin metanet-daas-device metanet-daas-user metanet-daas-vision
}

# 启动业务层服务（必须）
busi(){
	docker-compose up -d metanet-baas-app metanet-baas-channel metanet-baas-crm metanet-baas-openapi metanet-baas-vision
}

# 关闭所有环境/模块
stop(){
	docker-compose stop
}

# 删除所有环境/模块
rm(){
	docker-compose rm
}

# 根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
"port")
	port
;;
"db")
	db
;;
"tool")
	tool
;;
"base")
	base
;;
"data")
	data
;;
"busi")
	busi
;;
"stop")
	stop
;;
"rm")
	rm
;;
*)
	usage
;;
esac
