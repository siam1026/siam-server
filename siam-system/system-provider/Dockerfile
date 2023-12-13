FROM openjdk:8-jdk-slim
LABEL maintainer=暹罗

COPY target/*.jar /app.jar
# ADD jmx_prometheus_javaagent-0.15.0.jar /opt/jmx.jar
# ADD jmx_exporter.yml /opt/jmx.yml

# 设置JVM运行参数监控端口可以自定义
ENV JAVA_JMX_PROMETHEUS="-javaagent:/opt/jmx.jar=0.0.0.0:9041:/opt/jmx.yml"
ENV JAVA_SW_AGENT="-javaagent:/opt/apache-skywalking-apm-bin/agent/skywalking-agent.jar -Dskywalking.agent.service_name=siam-system -Dskywalking.collector.backend_service=127.0.0.1:11800"
ENV JAVA_PROFILES_ACTIVE=""
ENV JAVA_OPTS=""

# 应用端口
EXPOSE 9041

# ENTRYPOINT ["sh", "-c", "java $JAVA_JMX_PROMETHEUS $JAVA_SW_AGENT -jar /app.jar"]
# ENTRYPOINT ["sh", "-c", "java -Xms128m -Xmx256m $JAVA_SW_AGENT -jar /app.jar"]
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS $JAVA_PROFILES_ACTIVE -jar /app.jar"]
