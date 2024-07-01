FROM openjdk:21-jdk-slim
COPY target/msgateway-0.0.1-SNAPSHOT.jar microservice-gateway.jar
ENTRYPOINT ["java", "-jar", "microservice-gateway.jar"]

ENV security.in.memory.username=Admin
ENV security.in.memory.password=@AdminM03
