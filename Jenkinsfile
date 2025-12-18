pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sq1') {
                    sh '''
                      mvn sonar:sonar \
                      -Dsonar.projectKey=student-management \
                      -Dsonar.projectName=student-management
                    '''
                }
            }
        }
    }
}
