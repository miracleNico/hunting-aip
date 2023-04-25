#!/bin/sh

# 复制项目的文件到对应docker路径，便于一键生成镜像。
usage() {
	echo "Usage: sh copy.sh"
	exit 1
}


# copy sql
echo "begin copy sql "
cp ../metanet.sql ./mysql/db
cp ../test.sql ./mysql/db

# copy html
echo "begin copy html "
cp -r ../metanet-ui-auth/dist/** ./nginx/html/dist


# copy jar
echo "begin copy metanet-baas-gateway "
cp ../metanet-baas-gateway/target/metanet-baas-gateway.jar ./metanet/baas/gateway/jar

echo "begin copy metanet-baas-auth "
cp ../metanet-baas-auth/target/metanet-baas-auth.jar ./metanet/baas/auth/jar

echo "begin copy metanet-baas-monitor "
cp ../metanet-baas-monitor/target/metanet-baas-monitor.jar  ./metanet/baas/monitor/jar

echo "begin copy metanet-baas-app "
cp ../metanet-baas-app/target/metanet-baas-app.jar ./metanet/baas/app/jar

echo "begin copy metanet-baas-channel "
cp ../metanet-baas-channel/target/metanet-baas-channel.jar ./metanet/baas/channel/jar

echo "begin copy metanet-baas-crm "
cp ../metanet-baas-crm/target/metanet-baas-crm.jar ./metanet/baas/crm/jar

echo "begin copy metanet-baas-openapi "
cp ../metanet-baas-openapi/target/metanet-baas-openapi.jar ./metanet/baas/openapi/jar

echo "begin copy metanet-baas-vision "
cp ../metanet-baas-vision/target/metanet-baas-vision.jar ./metanet/baas/vision/jar

echo "begin copy metanet-daas-admin "
cp ../metanet-daas-admin/target/metanet-daas-admin.jar ./metanet/daas/admin/jar

echo "begin copy metanet-daas-device "
cp ../metanet-daas-device/target/metanet-daas-device.jar ./metanet/daas/device/jar

echo "begin copy metanet-daas-user "
cp ../metanet-daas-user/target/metanet-daas-user.jar ./metanet/daas/user/jar

echo "begin copy metanet-daas-vision "
cp ../metanet-daas-vision/target/metanet-daas-vision.jar ./metanet/daas/vision/jar

echo "begin copy metanet-baas-file "
cp ../metanet-baas-file/target/metanet-baas-file.jar ./metanet/baas/file/jar

echo "begin copy metanet-baas-gen "
cp ../metanet-baas-gen/target/metanet-baas-gen.jar ./metanet/baas/gen/jar

echo "begin copy metanet-baas-job "
cp ../metanet-baas-job/target/metanet-baas-job.jar ./metanet/baas/job/jar

# copy config
echo "begin copy metanet-baas-gateway config"
cp ../metanet-baas-gateway/src/main/resources/*.yml ./metanet/baas/gateway/config

echo "begin copy metanet-baas-auth "
cp ../metanet-baas-auth/src/main/resources/*.yml ./metanet/baas/auth/config

echo "begin copy metanet-baas-monitor config"
cp ../metanet-baas-monitor/src/main/resources/*.yml  ./metanet/baas/monitor/config

echo "begin copy metanet-baas-app config"
cp ../metanet-baas-app/src/main/resources/*.properties ./metanet/baas/app/config

echo "begin copy metanet-baas-channel config"
cp ../metanet-baas-channel/src/main/resources/*.properties ./metanet/baas/channel/config

echo "begin copy metanet-baas-crm config"
cp ../metanet-baas-crm/src/main/resources/*.properties ./metanet/baas/crm/config

echo "begin copy metanet-baas-openapi config"
cp ../metanet-baas-openapi/src/main/resources/*.properties ./metanet/baas/openapi/config

echo "begin copy metanet-baas-vision config"
cp ../metanet-baas-vision/src/main/resources/*.yml ./metanet/baas/vision/config

echo "begin copy metanet-daas-admin config"
cp ../metanet-daas-admin/src/main/resources/*.properties ./metanet/daas/admin/config

echo "begin copy metanet-daas-device config"
cp ../metanet-daas-device/src/main/resources/*.properties ./metanet/daas/device/config

echo "begin copy metanet-daas-user config"
cp ../metanet-daas-user/src/main/resources/*.properties ./metanet/daas/user/config

echo "begin copy metanet-daas-vision config"
cp ../metanet-daas-vision/src/main/resources/*.yml ./metanet/daas/vision/config

echo "begin copy metanet-baas-file config"
cp ../metanet-baas-file/src/main/resources/*.yml ./metanet/baas/file/config

echo "begin copy metanet-baas-gen config"
cp ../metanet-baas-gen/src/main/resources/*.yml ./metanet/baas/gen/config

echo "begin copy metanet-baas-job config"
cp ../metanet-baas-job/src/main/resources/*.yml ./metanet/baas/job/config