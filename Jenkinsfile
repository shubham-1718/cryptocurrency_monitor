pipeline {
    agent any
    tools {
        maven "Maven3"
    }

    stages {
        stage('Build') {
            steps {
                git 'https://github.com/shubham-1718/cryptocurrency_monitor.git'
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }

            post {
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }

        stage('Build docker image') {
            steps {
                script {
                    bat 'docker build -t gshub777/cryptocurrency_monitor .'
                }
            }
        }

        stage('Push image to Hub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                        bat 'docker login -u gshub777 -p %dockerhubpwd%'
                    }
                    bat 'docker push gshub777/cryptocurrency_monitor'
                }
            }
        }
    }
}
