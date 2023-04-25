#!/bin/sh

# 复制项目的文件到对应docker路径，便于一键生成镜像。
usage() {
	echo "Usage: sh remove.sh"
	exit 1
}


# remove file sql
echo "begin remove file sql "
rm -f ./mysql/db/*.sql 

# remove file html
# echo "begin remove file html "
# rm -rf ./nginx/html/dist/*.*

# remove file jar
echo "begin remove file metanet-baas-gateway "
rm -f ./metanet/baas/gateway/jar/*.jar

echo "begin remove file metanet-baas-auth "
rm -f ./metanet/baas/auth/jar/*.jar

echo "begin remove file metanet-baas-monitor "
rm -f   ./metanet/baas/monitor/jar/*.jar

echo "begin remove file metanet-baas-app "
rm -f  ./metanet/baas/app/jar/*.jar

echo "begin remove file metanet-baas-channel "
rm -f  ./metanet/baas/app/jar/*.jar

echo "begin remove file metanet-baas-crm "
rm -f  ./metanet/baas/crm/jar/*.jar

echo "begin remove file metanet-baas-openapi "
rm -f  ./metanet/baas/openapi/jar/*.jar

echo "begin remove file metanet-baas-vision "
rm -f  ./metanet/baas/vision/jar/*.jar

echo "begin remove file metanet-daas-admin "
rm -f  ./metanet/daas/admin/jar/*.jar

echo "begin remove file metanet-daas-device "
rm -f  ./metanet/daas/device/jar/*.jar

echo "begin remove file metanet-daas-user "
rm -f  ./metanet/daas/user/jar/*.jar

echo "begin remove file metanet-daas-vision "
rm -f  ./metanet/daas/vision/jar/*.jar

echo "begin remove file metanet-baas-file "
rm -f  ./metanet/baas/file/jar/*.jar

echo "begin remove file metanet-baas-gen "
rm -f  ./metanet/baas/gen/jar/*.jar

echo "begin remove file metanet-baas-job "
rm -f  ./metanet/baas/job/jar/*.jar