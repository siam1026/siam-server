###构建docker镜像
cd /root/project/siam-server/siam-system/system-provider

docker build -t siam-server:v1.0 .

###先杀死原程序进程
docker stop siam-server

docker rm siam-server

###运行镜像
# docker run -d -p 9602:9602 -p 9612:9041 --name siam-server -v /home/dockerdata/oap/volume/siam-system/apache-skywalking-apm-bin/:/opt/apache-skywalking-apm-bin/ siam-server:v1.0
docker run -d -p 9602:9602 --name siam-server siam-server:v1.0

###推送至阿里云容器镜像
spawn docker login --username=siam registry-vpc.cn-hangzhou.aliyuncs.com
expect "password:"
send "123456"

docker tag siam-server:v1.0 registry-vpc.cn-hangzhou.aliyuncs.com/siam-server/siam-server:v1.0

docker push registry-vpc.cn-hangzhou.aliyuncs.com/siam-server/siam-server:v1.0

if [ "$1" = "logs" ];then
  ###打印运行日志
  docker logs -f siam-server
  exit 1
fi