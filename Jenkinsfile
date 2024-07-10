pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'my-docker-image'
        DOCKER_TAG = 'latest'
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/goreges/dockercred1.git', branch: 'main', credentialsId: 'dockercred1'
            }
        }

        stage('Verify Environment') {
            steps {
                sh 'whoami'
                sh 'echo $PATH'
                sh 'which docker'
                sh 'docker --version'
                sh 'docker info'
                sh 'java -version'
            }
        }

        stage('Verify mvnw') {
            steps {
                sh 'ls -l ./mvnw'
                sh 'chmod +x ./mvnw'
            }
        }

        stage('Build') {
            steps {
                sh 'ls -l'
                sh './mvnw clean package'
                sh 'ls -l target'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
            }
        }

        stage('Test') {
            steps {
                sh "docker run --rm ${DOCKER_IMAGE}:${DOCKER_TAG}"
            }
        }

        stage('Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials-id', usernameVariable: 'DOCKER_HUB_USERNAME', passwordVariable: 'DOCKER_HUB_PASSWORD')]) {
                    sh "echo ${DOCKER_HUB_PASSWORD} | docker login -u ${DOCKER_HUB_USERNAME} --password-stdin"
                    sh "docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
                }
            }
        }
    }

    post {
        always {
            sh "docker rmi ${DOCKER_IMAGE}:${DOCKER_TAG} || echo 'Failed to remove Docker image'"
        }
    }
}