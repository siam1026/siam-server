#启动prometheus
docker run -d --name prometheus --net=host -p 9090:9090 -v /home/dockerdata/prometheus/:/etc/prometheus/ prom/prometheus

#启动node_exporter
nohup ./node_exporter  --web.listen-address=":9037"&

#启动mysqld-exporter
./mysqld_exporter --web.listen-address=:9038 --config.my-cnf=./my.cnf &

#启动redis_exporter
docker run -d --name redis_exporter -p 9121:9121 oliver006/redis_exporter --redis.addr redis://127.0.0.1:6379 --redis.password '123456'

#启动grafana
docker run -d --name grafana -p 3000:3000 \
-v /home/dockerdata/grafana/conf:/etc/grafana \
-v /home/dockerdata/grafana/data:/var/lib/grafana \
-v /home/dockerdata/grafana/log:/var/log/grafana \
-v /home/dockerdata/grafana/plugins:/var/lib/grafana/plugins \
 grafana/grafana

#启动cadvisor
docker run --name=cadvisor --volume=/:/rootfs:ro --volume=/var/run:/var/run:rw --volume=/sys:/sys:ro --volume=/var/lib/docker/:/var/lib/docker:ro --volume=/dev/disk/:/dev/disk:ro --publish=8080:8080 --detach=true google/cadvisor

#手动启动jar + jmx_exporter
java -javaagent:/root/middleware/JmxExporter/jmx_prometheus_javaagent-0.15.0.jar=9041:/root/middleware/JmxExporter/jmx_exporter.yml -jar siam-system.jar
