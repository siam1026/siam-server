FROM openjdk:8-jdk-slim
LABEL maintainer=暹罗

COPY target/*.jar /app.jar

ENV JAVA_PROFILES_ACTIVE=""
ENV JAVA_OPTS=""

# 应用端口
EXPOSE 9041

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS $JAVA_PROFILES_ACTIVE -jar /app.jar"]
