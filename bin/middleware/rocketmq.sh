# 启动nameserver服务
nohup sh mqnamesrv &

# 启动broker服务
nohup sh mqbroker -n localhost:9876 autoCreateTopicEnable=true -c ../conf/broker.conf &

# 启动控制台项目
nohup java -jar rocketmq-dashboard-1.0.1-SNAPSHOT.jar --server.port=12581 --rocketmq.config.namesrvAddr=127.0.0.1:9876 --rocketmq.config.isVIPChannel=false &

# 发送消息
sh bin/tools.sh org.apache.rocketmq.example.quickstart.Producer

# 接收消息
sh bin/tools.sh org.apache.rocketmq.example.quickstart.Consumer

#关闭nameserver服务
sh mqshutdown namesrv

#关闭broker服务
sh mqshutdown broker
