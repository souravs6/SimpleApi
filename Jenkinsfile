pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    environment {
        APP_SERVER = "ec2-user@<172.31.22.25>"
        DEPLOY_DIR = "/home/ec2-user/app"
        SSH_CRED = "app-server-ssh"
    }

    stages {

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

        stage('Copy JAR to App Server') {
            steps {
                sshagent(credentials: [SSH_CRED]) {
                    sh """
                    scp target/*.jar $APP_SERVER:$DEPLOY_DIR/app.jar
                    """
                }
            }
        }

        stage('Restart Application') {
            steps {
                sshagent(credentials: [SSH_CRED]) {
                    sh """
                    ssh $APP_SERVER << EOF
                    pkill -f app.jar || true
                    nohup java -jar $DEPLOY_DIR/app.jar > app.log 2>&1 &
                    EOF
                    """
                }
            }
        }
    }

    post {
        success {
            echo "✅ Deployment successful"
        }
        failure {
            echo "❌ Deployment failed"
        }
    }
}
