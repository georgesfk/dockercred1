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
                        git url: 'https://github.com/goreges/dockercred1.git', branch: 'main', credentialsId: 'dockercred1'
                        echo 'Repository checkout successful.'
                    } catch (Exception e) {
                        echo "Error during checkout: ${e.message}"
                        error "Failed to checkout repository."
                    }
                }
            }
        }

        stage('Verify Environment') {
            steps {
                script {
                    echo 'Verifying environment setup...'
                    sh 'whoami'
                    sh 'echo $PATH'
                    sh 'which docker || echo "Docker not found"'
                    sh 'docker --version || echo "Docker version not found"'
                    sh 'docker info || echo "Docker info not available"'
                    sh 'java -version || echo "Java not found"'
                }
            }
        }

        stage('Verify mvnw') {
            steps {
                script {
                    echo 'Checking mvnw script...'
                    sh 'ls -l ./mvnw || echo "mvnw script not found"'
                    sh 'chmod +x ./mvnw || echo "Failed to make mvnw executable"'
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    echo 'Listing files in the workspace...'
                    sh 'ls -l'

                    echo 'Running mvnw clean package...'
                    sh './mvnw clean package || echo "Failed to run mvnw clean package"'

                    echo 'Listing files in the target directory...'
                    sh 'ls -l target || echo "Target directory not found"'

                    echo 'Building Docker image...'
                    sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} . || echo 'Failed to build Docker image'"
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    echo 'Running Docker container to test the built image...'
                    sh "docker run --rm ${DOCKER_IMAGE}:${DOCKER_TAG} || echo 'Failed to run Docker container'"
                }
            }
        }

        stage('Push') {
            steps {
                script {
                    echo 'Pushing Docker image to Docker Hub...'
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials-id', usernameVariable: 'DOCKER_HUB_USERNAME', passwordVariable: 'DOCKER_HUB_PASSWORD')]) {
                        sh "echo \$DOCKER_HUB_PASSWORD | docker login -u \$DOCKER_HUB_USERNAME --password-stdin || echo 'Failed to login to Docker Hub'"
                        sh "docker push ${DOCKER_IMAGE}:${DOCKER_TAG} || echo 'Failed to push Docker image'"
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                echo 'Cleaning up Docker image locally...'
                sh "docker rmi ${DOCKER_IMAGE}:${DOCKER_TAG} || echo 'Failed to remove Docker image'"
            }
        }
    }
}