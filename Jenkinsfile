pipeline {
    agent any

    environment {
        // AWS 자격증명
        AWS_DEFAULT_REGION = 'ap-northeast-2'
        AWS_ACCESS_KEY = credentials('AWS_ACCESS_KEY')
        AWS_SECRET_KEY = credentials('AWS_SECRET_KEY')

        // EC2 관련 설정
        EC2_HOST = ''
        SSH_CREDENTIALS = ''

        // AWS 파라미터 스토어
        ENV_FILE_NAME = ''

        // 도커 관련
        DOCKER_FILE_NAME = ''
        DOCKER_USER = credentials('docker-user')
        DOCKER_PASSWORD = credentials('docker-password')
        MODULE_NAME = ''
        MODULE_PORT = ''
    }
    stages {
        stage('Set EC2 Host') {
            steps {
                script {
                    echo "Current branch is: ${env.BRANCH_NAME}"

                    if (env.BRANCH_NAME == 'main-product') {
                        env.EC2_HOST = credentials('product-module-host')
                        env.ENV_FILE_NAME = '/product-env'
                        env.DOCKER_FILE_NAME = 'Dockerfile_Product'
                        env.MODULE_NAME = 'product_module'
                        env.MODULE_PORT = 8081
                    } else if (env.BRANCH_NAME == 'main-user') {
                        env.EC2_HOST = credentials('user-module-host')
                        env.ENV_FILE_NAME = '/user-env'
                        env.DOCKER_FILE_NAME = 'Dockerfile_User'
                        env.MODULE_NAME = 'user_module'
                        env.MODULE_PORT = 8082
                    } else if (env.BRANCH_NAME == 'main-api') {
                        env.EC2_HOST = credentials('api-module-host')
                        env.ENV_FILE_NAME = '/api-env'
                        env.DOCKER_FILE_NAME = 'Dockerfile_Api'
                        env.MODULE_NAME = 'api_module'
                        env.MODULE_PORT = 8090
                    }
                }
            }
        }
        stage('Retrieve AWS Parameters') {
            steps {
                script {
                    echo "Fetching parameters from AWS Parameter Store..."
                    // AWS Parameter Store에서 환경변수 가져오기
                    sh """
                    aws ssm get-parameters \
                        --names ${ENV_FILE_NAME} \
                        --with-decryption \
                        --query "Parameters[*].[Name,Value]" \
                        --output text > aws_params.txt
                    """
                }
            }
        }

        stage('Create application.properties') {
            steps {
                script {
                    echo "Creating application.properties from AWS Parameter Store values..."
                    // AWS Parameter Store에서 가져온 값을 application.properties에 저장
                    sh """
                    cat aws_params.txt | while read line; do
                        key=\$(echo \$line | awk '{print \$1}' | awk -F '/' '{print \$NF}')
                        value=\$(echo \$line | awk '{print \$2}')
                        echo "\$key=\$value" >> application.properties
                    done
                    """

                    // 생성된 application.properties를 product_module/src/resources/로 이동
                    sh """
                    mkdir -p ${MODULE_NAME}/src/resources
                    mv application.properties ${MODULE_NAME}/src/resources/
                    """
                }
            }
        }

        stage('Build and Push Docker Image') {
            steps {
                script {
                    echo "Building Docker Image using ${DOCKER_FILE_NAME}..."
                    // Docker 이미지 빌드 (Dockerfile_Product 사용)
                    sh """
                    docker login -u ${DOCKER_USER} -p ${DOCKER_PASSWORD}
                    docker build -t ${DOCKER_USER}/${MODULE_NAME} -f ${DOCKER_FILE_NAME} .
                    docker push ${DOCKER_USER}/${MODULE_NAME}
                    """
                }
            }
        }

        stage('Deploy to AWS EC2') {
            steps {
                script {
                    echo "Deploying Docker Image to AWS EC2..."

                    // EC2 인스턴스에 SSH로 접속하여 기존 컨테이너 중지 및 새 컨테이너 실행
                    sshagent(['ec2-ssh-key']) {
                        sh """
                        ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} << EOF
                        docker stop ${MODULE_NAME} || true
                        docker rm ${MODULE_NAME} || true
                        docker rmi ${DOCKER_USER}/${MODULE_NAME} || true
                        docker pull ${DOCKER_USER}/${MODULE_NAME}
                        docker run -d --name ${MODULE_NAME} -p ${MODULE_PORT}:${MODULE_PORT} ${DOCKER_USER}/${MODULE_NAME}
                        docker image prune -f
                        EOF
                        """
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Cleaning up...'
            // 필요하다면 임시 파일 정리
            sh 'rm -f aws_params.txt application.properties'
        }
        success {
            echo 'Deployment succeeded!'
        }
        failure {
            echo 'Deployment failed!'
        }
    }
}
