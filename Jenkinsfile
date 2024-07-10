pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'my-docker-image'
        DOCKER_TAG = 'latest' // you can change this to a specific tag
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    echo 'Checking out the repository...'
                    git(url: 'https://github.com/goreges/dockercred1.git', branch: 'main', credentialsId: 'dockercred1')
                }
            }
        }
        stage('Verify Docker') {
            steps {
              script {
            echo 'Verifying Docker installation...'
            sh 'docker --version'
            sh 'docker info'
        }
    }
}
        stage('Build') {
            steps {
                script {
                    echo 'Listing files in the workspace...'
                    sh 'ls -l'

                    echo 'Adding execution permission to the mvnw script...'
                    sh 'chmod +x ./mvnw'

                    echo 'Running mvnw clean package...'
                    sh './mvnw clean package'

                    echo 'Listing files in the target directory...'
                    sh 'ls -l target'

                    echo 'Building Docker image...'
                    sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    echo 'Running Docker container to test the built image...'
                    sh "docker run --rm ${DOCKER_IMAGE}:${DOCKER_TAG}"
                }
            }
        }

        stage('Push') {
            steps {
                script {
                    echo 'Pushing Docker image to Docker Hub...'
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials-id', usernameVariable: 'DOCKER_HUB_USERNAME', passwordVariable: 'DOCKER_HUB_PASSWORD')]) {
                        sh "echo \$DOCKER_HUB_PASSWORD | docker login -u \$DOCKER_HUB_USERNAME --password-stdin"
                        sh "docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                echo 'Cleaning up Docker image locally...'
                sh "docker rmi ${DOCKER_IMAGE}:${DOCKER_TAG}"
            }
        }
    }
}