FROM openjdk:8-jdk-slim
LABEL maintainer=暹罗

COPY target/*.jar /app.jar

# 应用端口
EXPOSE 9041

ENTRYPOINT ["sh", "-c", "java -Xms256m -Xmx256m -jar /app.jar"]
