pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                docker {
                    image 'my-docker-image'
                    // ... rest of the pipeline ...
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/goreges/dockercred1.git', branch: 'main', credentialsId: 'dockercred1'
            }
        }

        stage('Build') {
            steps {
                script {
                    sh 'chmod +x mvnw'
                    sh './mvnw clean package'
                    sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    sh "docker run --rm ${DOCKER_IMAGE}:${DOCKER_TAG} "
                }
            }
        }

        stage('Push') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials-id', usernameVariable: 'DOCKER_HUB_USERNAME', passwordVariable: 'DOCKER_HUB_PASSWORD')]) {
                        sh "echo $DOCKER_HUB_PASSWORD | docker login -u $DOCKER_HUB_USERNAME --password-stdin"
                        sh "docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                sh "docker rmi ${DOCKER_IMAGE}:${DOCKER_TAG}"
            }
        }
    }
}