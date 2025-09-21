pipeline {
    agent any

    environment {
        IMAGE_NAME = "springboot-app"
        CONTAINER_NAME = "springboot-container"
    }

    stages {
        stage('Clone Repository') {
            steps {
                git 'https://github.com/souravs6/SimpleApi.git'
            }
        }

        stage('Build App') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('Stop Existing Container') {
            steps {
                sh '''
                    docker stop $CONTAINER_NAME || true
                    docker rm $CONTAINER_NAME || true
                '''
            }
        }

        stage('Run Docker Container') {
            steps {
                sh '''
                    docker run -d --name $CONTAINER_NAME -p 8080:8080 $IMAGE_NAME
                '''
            }
        }
    }
}
