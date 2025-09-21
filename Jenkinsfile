pipeline {
    agent any

    environment {
        IMAGE_NAME = "springboot-app"
        CONTAINER_NAME = "springboot-container"
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main',url: 'https://github.com/souravs6/SimpleApi.git'
            }
        }

        stage('Build App') {
            steps {
                bat './mvnw clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t %IMAGE_NAME% .'
            }
        }

        stage('Stop Existing Container') {
            steps {
                bat '''
                    docker stop %CONTAINER_NAME% || exit 0
                    docker rm %CONTAINER_NAME% || exit 0
                '''
            }
        }

        stage('Run Docker Container') {
            steps {
                bat '''
                    docker run -d --name $CONTAINER_NAME -p 8080:8080 %IMAGE_NAME
                '''
            }
        }
    }
}
