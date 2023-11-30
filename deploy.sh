#!/bin/bash

# Specify a static container name
CONTAINER_NAME="playAreaService"

# Replace these variables with your actual values
AWS_REGION="us-east-2"
ECR_REPO_URI="public.ecr.aws/r2n9m4w8/playpalrepo"

# Stop the existing Docker container if it's running
if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
    echo "Stopping existing container: $CONTAINER_NAME"
    docker stop $CONTAINER_NAME
fi

# Remove the existing Docker container
if [ "$(docker ps -a -q -f name=$CONTAINER_NAME)" ]; then
    echo "Removing existing container: $CONTAINER_NAME"
    docker rm $CONTAINER_NAME
fi


# Pull the Docker image
docker pull $ECR_REPO_URI:latest

# Run the new Docker container with the static name
docker run -d --name $CONTAINER_NAME -p 8080:8080 $ECR_REPO_URI:latest