pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'my-docker-image'
        DOCKER_TAG = 'latest'
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    echo 'Checking out the repository...'
                    try {
                        git(url: 'https://github.com/goreges/dockercred1.git', branch: 'main', credentialsId: 'dockercred1')
                        echo 'Repository checkout successful.'
                    } catch (Exception e) {
                        echo "Error during checkout: ${e.message}"
                        error "Failed to checkout repository."
                    }
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

        stage('Verify mvnw') {
            steps {
                script {
                    echo 'Checking mvnw script...'
                    sh 'ls -l ./mvnw'
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
    stage('Print PATH') {
        steps {
            script {
                echo 'Printing PATH...'
                sh 'echo $PATH'
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