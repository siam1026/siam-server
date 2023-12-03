# 启动es
docker run -d --name es \
--net elk \
-p 9200:9200 -p 9300:9300 \
-e "discovery.type=single-node" \
--privileged=true \
-v /home/dockerdata/elasticsearch/config/:/usr/share/elasticsearch/config \
-v /home/dockerdata/elasticsearch/data/:/usr/share/elasticsearch/data \
elasticsearch:7.12.1

# 启动kibana
docker run -d --name kibana \
-p 5601:5601 \
-v /home/dockerdata/kibana/config/:/usr/share/kibana/config \
--net elk \
kibana:7.12.1

# 启动logstash
docker run -d --name logstash --net elk \
--privileged=true \
-p 5044:5044 -p 9600:9600 \
-v /home/dockerdata/logstash/data/:/usr/share/logstash/data \
-v /home/dockerdata/logstash/config/:/usr/share/logstash/config \
-v /home/dockerdata/logstash/pipeline/:/usr/share/logstash/pipeline \
logstash:7.12.1
