pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'my-docker-image'
        DOCKER_TAG = 'latest' // you can change this to a specific tag
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
                    // Add execution permission to the mvnw script
                    sh 'chmod +x ./mvnw'

                    // Use the mvnw script to clean and package the application
                    sh './mvnw clean package'

                    // Build a Docker image using the Dockerfile in the repository
                    sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Run the Docker container to test the built image
                    sh "docker run --rm ${DOCKER_IMAGE}:${DOCKER_TAG}"
                }
            }
        }

        stage('Push') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials-id', usernameVariable: 'DOCKER_HUB_USERNAME', passwordVariable: 'DOCKER_HUB_PASSWORD')]) {
                        // Log in to Docker Hub using provided credentials
                        sh "echo $DOCKER_HUB_PASSWORD | docker login -u $DOCKER_HUB_USERNAME --password-stdin"

                        // Push the Docker image to the registry
                        sh "docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                // Remove the Docker image locally to clean up disk space
                sh "docker rmi ${DOCKER_IMAGE}:${DOCKER_TAG}"
            }
        }
    }
}