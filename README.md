# MediLabo-Solution - Deployment Guide

MediLabo-Solution is designed to assist healthcare professionals in identifying patients at risk of Type 2 diabetes. 
This application utilizes a microservices architecture and Docker for container orchestration.

![Capture d'écran 2024-07-24 185800](https://github.com/user-attachments/assets/e4912b8c-fa6c-4acd-a8d7-266754c426ab)


## Prerequisites

Ensure Docker and Docker Compose are installed on your machine. If not, you can download and install them from the following links:

- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Cloning the Project

To clone the project to your local machine, run:

git clone https://github.com/walid938/Medilabo-Solution.git

## Port Configuration

The MediLabo-Solution Docker services use the following ports. Ensure these ports are free on your machine:

- **9091**: User Interface (`microservice-clientui`)
- **8181**: API Gateway (`microservice-gateway`)
- **8084**: Patient Management Service (`microservice-patient`)
- **8081**: Notes Management Service (`microservice-notes`)
- **8082**: Diabetes Risk Assessment Service (`microservice-diabetes-risk`)
- **3307**: MySQL Database (`mysql-db`)
- **27018**: MongoDB Database (`mongodb`)

## Starting the Application

## Navigate to the project directory:

cd MediLabo-Solution

## Start the application in detached mode with:

 docker-compose up --build

This command will launch all services defined in the docker-compose.yml file in the background.

## Accessing the Application

## Once the Docker containers are up and running, access the application via the user interface at:

http://localhost:9091/patient/list

## Default Login Credentials

## Use the following credentials to log in:

Username: username
Password: password

## Troubleshooting

## If you encounter issues with starting services or errors, view the container logs with:

docker-compose logs

## To restart services or make changes, use the following commands:

## Restart Services:

docker-compose restart

## Stop Services:


docker-compose down

## Stop Services and Remove Containers:

docker-compose down --volumes

For additional information, consult the Docker documentation and Docker Compose documentation.

Feel free to adjust any specific details or add any additional instructions based on your project needs!


