FROM openjdk:21-jdk-slim
COPY target/mspatient-0.0.1-SNAPSHOT.jar microservice-patient.jar
ENTRYPOINT ["java", "-jar", "microservice-patient.jar"]

ENV security.in.memory.username=Admin
ENV security.in.memory.password=@AdminM03

