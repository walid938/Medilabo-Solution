FROM openjdk:21-jdk-slim
COPY target/msclientui-0.0.1-SNAPSHOT.jar microservice-clientui.jar
ENTRYPOINT ["java", "-jar", "microservice-clientui.jar"]


ENV security.in.memory.username=Admin
ENV security.in.memory.password=@AdminM03
