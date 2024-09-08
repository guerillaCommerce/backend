def EC2_HOST = ''
def ENV_FILE_NAME = ''
def DOCKER_FILE_NAME = ''
def MODULE_NAME = ''
def MODULE_PORT = ''

pipeline {
    agent any

    stages {
        stage('Set EC2 Host') {
            steps {
                script {
                    echo "Current branch : ${env.BRANCH_NAME}"

                    // 설정된 브랜치에 따라 변수를 설정
                    if (env.BRANCH_NAME == 'main-product') {
                        echo "Setting up for main-product branch"
                        EC2_HOST = string(credentialsId: 'product-module-host')
                        ENV_FILE_NAME = 'product-properties'
                        DOCKER_FILE_NAME = 'Dockerfile_Product'
                        MODULE_NAME = 'product_module'
                        MODULE_PORT = '8081'
                    } else if (env.BRANCH_NAME == 'main-user') {
                        echo "Setting up for main-user branch"
                        EC2_HOST = string(credentialsId: 'user-module-host')
                        ENV_FILE_NAME = 'user-properties'
                        DOCKER_FILE_NAME = 'Dockerfile_User'
                        MODULE_NAME = 'user_module'
                        MODULE_PORT = '8082'
                    } else if (env.BRANCH_NAME == 'main-api') {
                        echo "Setting up for main-api branch"
                        EC2_HOST = string(credentialsId: 'api-module-host')  // 수정된 부분
                        ENV_FILE_NAME = 'api-properties'
                        DOCKER_FILE_NAME = 'Dockerfile_Api'
                        MODULE_NAME = 'api_module'
                        MODULE_PORT = '8090'
                    } else {
                        error "Branch ${env.BRANCH_NAME} is not configured to run this pipeline."
                    }

                    // 로그로 변수 상태 확인
                    echo "EC2_HOST: ${EC2_HOST}"
                    echo "ENV_FILE_NAME: ${ENV_FILE_NAME}"
                    echo "DOCKER_FILE_NAME: ${DOCKER_FILE_NAME}"
                    echo "MODULE_NAME: ${MODULE_NAME}"
                    echo "MODULE_PORT: ${MODULE_PORT}"
                }
            }
        }

        stage('Retrieve Application Properties from Jenkins') {
            steps {
                script {
                    withCredentials([file(credentialsId: "${ENV_FILE_NAME}", variable: 'APP_PROPS')]) {
                        echo "Using application.properties from Jenkins credentials."

                        sh """
                            sudo mkdir -p "${MODULE_NAME}/src/main/resources"
                            sudo chown -R jenkins:jenkins "${MODULE_NAME}/src/main/resources"
                            sudo cp "$APP_PROPS" "${MODULE_NAME}/src/main/resources/application.properties"
                        """
                    }
                }
            }
        }

        stage('Build and Push Docker Image') {
            steps {
                script {
                    echo "Building Docker Image using ${DOCKER_FILE_NAME}..."
                    withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh """
                        echo '${DOCKER_PASSWORD}' | docker login -u '${DOCKER_USER}' --password-stdin
                        docker build -t '${DOCKER_USER}/${MODULE_NAME}' -f '${DOCKER_FILE_NAME}' .
                        docker push '${DOCKER_USER}/${MODULE_NAME}'
                        """
                    }
                }
            }
        }

        stage('Deploy to AWS EC2') {
            steps {
                script {
                    echo "Deploying Docker Image to AWS EC2..."
                    withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sshagent(['ec2-ssh-key']) {
//                             sh """
//                                 ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} << 'EOF'
//                                     sudo docker stop ${MODULE_NAME} || true
//                                     sudo docker rm ${MODULE_NAME} || true
//                                     sudo docker rmi ${DOCKER_USER}/${MODULE_NAME} || true
//                                     echo '${DOCKER_PASSWORD}' | docker login -u '${DOCKER_USER}' --password-stdin
//                                     sudo docker pull ${DOCKER_USER}/${MODULE_NAME}
//                                     sudo docker run -d --name ${MODULE_NAME} -p ${MODULE_PORT}:${MODULE_PORT} ${DOCKER_USER}/${MODULE_NAME}
//                                     sudo docker image prune -f
//                                 EOF
//                             """
                            // Stopping the Docker container
                            echo "Stopping Docker container..."
                            sh "ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} sudo docker stop ${MODULE_NAME} || true"

                            // Removing the Docker container
                            echo "Removing Docker container..."
                            sh "ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} sudo docker rm ${MODULE_NAME} || true"

                            // Removing the Docker image
                            echo "Removing Docker image..."
                            sh "ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} sudo docker rmi ${DOCKER_USER}/${MODULE_NAME} || true"

                            // Logging into Docker registry
                            echo "Logging into Docker registry..."
                            sh "ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} echo '${DOCKER_PASSWORD}' | docker login -u '${DOCKER_USER}' --password-stdin"

                            // Pulling the latest Docker image
                            echo "Pulling latest Docker image..."
                            sh "ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} sudo docker pull ${DOCKER_USER}/${MODULE_NAME}"

                            // Running the new Docker container
                            echo "Running new Docker container..."
                            sh "ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} sudo docker run -d --name ${MODULE_NAME} -p ${MODULE_PORT}:${MODULE_PORT} ${DOCKER_USER}/${MODULE_NAME}"

                            // Pruning unused Docker images
                            echo "Pruning unused Docker images..."
                            sh "ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} sudo docker image prune -f"
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Cleaning up...'
            // 각 모듈의 application.properties 파일 정리
            sh """
                sudo rm -f ${MODULE_NAME}/src/main/resources/application.properties
            """
        }
        success {
            echo 'Deployment succeeded!'
        }
        failure {
            echo 'Deployment failed!'
        }
    }
}
