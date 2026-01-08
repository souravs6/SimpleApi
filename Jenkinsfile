pipeline {
    agent any
    
    environment {
        APP_NAME = "demo"
        APP_PORT = "8080"
        DEPLOY_USER = "ec2-user"
        DEPLOY_HOST = "172.31.22.25"
        DEPLOY_DIR = "/home/ec2-user/app"
        JAR_NAME = "demo-0.0.1-SNAPSHOT.jar"
    }
    
    

    stages {

        stage('Verify Tools') {
            steps {
                sh 'java -version'
                sh 'mvn -v'
            }
        }

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/souravs6/SimpleApi.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Copy JAR to Server') {
            steps {
                sh """
                ssh ${DEPLOY_USER}@${DEPLOY_HOST} 'mkdir -p ${DEPLOY_DIR}'
                scp target/${JAR_NAME} ${DEPLOY_USER}@${DEPLOY_HOST}:${DEPLOY_DIR}
                """
            }
        }

        stage('Deploy Application') {
            steps {
                sh """
                ssh ${DEPLOY_USER}@${DEPLOY_HOST} '
                pkill -f ${JAR_NAME} || true
                nohup java -jar ${DEPLOY_DIR}/${JAR_NAME} > app.log 2>&1 &
                '
                """
            }
        }
    }

    post {
        success {
            echo "Deployment Successful 🚀"
        }
        failure {
            echo "Deployment Failed ❌"
        }
    }
}
