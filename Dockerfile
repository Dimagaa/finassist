FROM openjdk:21-jdk-slim
COPY build/libs/*.jar /app/application.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","/app/application.jar"]
