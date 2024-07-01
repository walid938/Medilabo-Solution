FROM openjdk:21-jdk-slim
COPY target/msdiabeterisk-0.0.1-SNAPSHOT.jar microservice-diabetes-risk.jar
ENTRYPOINT ["java", "-jar", "microservice-diabetes-risk.jar"]


ENV security.in.memory.username=Admin
ENV security.in.memory.password=@AdminM03
