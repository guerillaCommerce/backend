def EC2_HOST = ''
def ENV_FILE_NAME = ''
def DOCKER_FILE_NAME = ''
def MODULE_NAME = ''
def MODULE_PORT = ''

pipeline {
    agent any

    environment {
        // AWS 자격증명
        AWS_DEFAULT_REGION = 'ap-northeast-2'
        AWS_ACCESS_KEY = credentials('AWS_ACCESS_KEY')
        AWS_SECRET_KEY = credentials('AWS_SECRET_KEY')

        //도커 관련
        DOCKER_USER = credentials('docker-user')
        DOCKER_PASSWORD = credentials('docker-password')
    }

    stages {
        stage('Set EC2 Host') {
            steps {
                script {
                    echo "Current branch : ${env.BRANCH_NAME}"


                    // 설정된 브랜치에 따라 변수를 설정
                    if (env.BRANCH_NAME == 'main-product') {
                        echo "Setting up for main-product branch"
                        EC2_HOST = credentials('product-module-host')
                        ENV_FILE_NAME = 'product-properties'
                        DOCKER_FILE_NAME = 'Dockerfile_Product'
                        MODULE_NAME = 'product_module'
                        MODULE_PORT = '8081'
                    } else if (env.BRANCH_NAME == 'main-user') {
                        echo "Setting up for main-user branch"
                        EC2_HOST = credentials('user-module-host')
                        ENV_FILE_NAME = 'user-properties'
                        DOCKER_FILE_NAME = 'Dockerfile_User'
                        MODULE_NAME = 'user_module'
                        MODULE_PORT = '8082'
                    } else if (env.BRANCH_NAME == 'main-api') {
                        echo "Setting up for main-api branch"
                        EC2_HOST = credentials('api-module-host')
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
                    // Jenkins 파일 크레덴셜을 임시 디렉토리로 가져옴
                    withCredentials([file(credentialsId: ${ENV_FILE_NAME}, variable: 'APP_PROPS')]) {
                        echo "Using application.properties from Jenkins credentials."

                        // 임시로 다운로드된 파일 경로를 사용할 수 있습니다.
                        sh """
                        cat ${APP_PROPS}
                        """

                        // 만약 이 파일을 모듈 디렉토리로 이동시켜야 한다면
                        sh """
                        mkdir -p "${MODULE_NAME}/src/main/resources"
                        cp ${APP_PROPS} "${MODULE_NAME}/src/main/resources/application.properties"
                        """
                    }
                }
            }
        }
//         stage('Retrieve AWS Parameters') {
//             steps {
//                 script {
//                     if (ENV_FILE_NAME == '') {
//                         error "ENV_FILE_NAME is not set. Exiting the pipeline."
//                     }
//                     echo "Fetching parameters from AWS Parameter Store using ${ENV_FILE_NAME}..."
//                     // AWS Parameter Store에서 환경변수 가져오기
//                     sh """
//                     aws ssm get-parameter \
//                         --name "${ENV_FILE_NAME}" \
//                         --with-decryption \
//                         --query Parameter.Value \
//                         --output text > aws_params.txt
//                     """
//                 }
//             }
//         }
//
//         stage('Create application.properties') {
//             steps {
//                 script {
//                     echo "Creating application.properties from AWS Parameter Store values..."
//                     // AWS Parameter Store에서 가져온 값을 application.properties에 저장
//                     sh """
//                     cat aws_params.txt | while read line; do
//                         key=\$(echo \$line | awk '{print \$1}' | awk -F '/' '{print \$NF}')
//                         value=\$(echo \$line | awk '{print \$2}')
//                         echo "\$key=\$value" >> application.properties
//                     done
//                     """
//
//                     // 생성된 application.properties를 모듈 디렉토리로 이동
//                     sh """
//                     mkdir -p "${env.MODULE_NAME}/src/resources"
//                     mv application.properties "${env.MODULE_NAME}/src/resources/"
//                     """
//                 }
//             }
//         }

        stage('Build and Push Docker Image') {
            steps {
                script {
                    echo "Building Docker Image using ${DOCKER_FILE_NAME}..."
                    // Docker 이미지 빌드
                    sh """
                    docker login -u "${env.DOCKER_USER}" -p "${env.DOCKER_PASSWORD}"
                    docker build -t "${env.DOCKER_USER}/${MODULE_NAME}" -f "${DOCKER_FILE_NAME}" .
                    docker push "${env.DOCKER_USER}/${MODULE_NAME}"
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
                        docker stop "${MODULE_NAME}" || true
                        docker rm "${MODULE_NAME}" || true
                        docker rmi "${env.DOCKER_USER}/${MODULE_NAME}" || true
                        docker pull "${env.DOCKER_USER}/${MODULE_NAME}"
                        docker run -d --name "${MODULE_NAME}" -p ${MODULE_PORT}:${MODULE_PORT} "${env.DOCKER_USER}/${MODULE_NAME}"
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
