FROM openjdk:21-jdk-slim
COPY target/msnotes-0.0.1-SNAPSHOT.jar microservice-notes.jar
ENTRYPOINT ["java", "-jar", "microservice-notes.jar"]

ENV security.in.memory.username=Admin
ENV security.in.memory.password=@AdminM03

