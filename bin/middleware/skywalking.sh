# 启动oap服务
docker run --name oap --restart always -d \
--net elk \
--restart=always \
-e TZ=Asia/Shanghai \
-p 12800:12800 \
-p 11800:11800 \
-v /home/dockerdata/oap/config/:/skywalking/config \
apache/skywalking-oap-server:6.6.0-es7

# 启动ui服务
docker run -d --name skywalking-ui \
--net elk \
--restart=always \
-e TZ=Asia/Shanghai \
-p 8080:8080 \
--link oap:oap \
-e SW_OAP_ADDRESS=oap:12800 \
apache/skywalking-ui:6.6.0
