pipeline {
    agent {
        label 'node_10_0_1_24'
    }
    tools {
        // The name here must match the 'Name' configured in Jenkins Global Tool Configuration
        maven 'mvn-3.9.12'
    }

    environment {
      scannerHome = tool 'sonar-scanner-8'
    }
    stages {
        stage('Clone SRC') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/DOP2025-01/logic.git',
                    credentialsId: 'de7ba840-30a0-4931-b1d2-e4983f127634'
            }
        }
        stage('Setup Dependencies') {
            steps {
                script {
                    // check and install docker cli
                    def dockerInstalled = sh(
                        script: 'which docker || echo "not_installed"',
                        returnStdout: true
                    ).trim()
                    sh "sudo apt-get install -y unzip curl"
                    if (dockerInstalled == 'not_installed') {
                        sh '''
                            sudo apt-get update -y --no-install-recommends
                            sudo apt-get install -y ca-certificates gnupg lsb-release
                    
                            sudo install -m 0755 -d /etc/apt/keyrings
                            curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
                            sudo chmod a+r /etc/apt/keyrings/docker.gpg
                            
                            echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] \
                            https://download.docker.com/linux/ubuntu $(. /etc/os-release && echo $VERSION_CODENAME) stable" \
                            | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
                    
                            sudo apt-get update -y
                            sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
                            sudo systemctl enable docker
                            sudo systemctl start docker
                        '''
                    } else {
                        sh '''
                            echo "Docker is already installed at: ${dockerInstalled}"
                            sudo chmod 777 /var/run/docker.sock
                        '''
                    }
                    sh '''
                      sudo chmod 777 /var/run/docker.sock
                      docker --version
                    '''

                    // check and install aws cli
                    def awsInstalled = sh(
                        script: 'which aws || echo "not_installed"',
                        returnStdout: true
                    ).trim()


                    if (awsInstalled == 'not_installed') {
                        sh '''
                            curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
                            unzip awscliv2.zip
                            sudo ./aws/install --bin-dir /usr/local/bin --install-dir /usr/local/aws-cli --update
                            rm -rf awscliv2.zip aws/
                        '''
                    } else {
                        sh "echo AWS CLI is already installed at: ${awsInstalled}"
                    }
                }

            }
        }
        stage('Setup AWS Config/Credential') {
            steps {
                script{
                    sh '''
                        mkdir -p ~/.aws
                        echo "[default]" > ~/.aws/credentials
                        echo "aws_access_key_id=${aws_access_key_id}" >> ~/.aws/credentials
                        echo "aws_secret_access_key=${aws_secret_access_key}" >> ~/.aws/credentials
                        echo "[default]" > ~/.aws/config
                        echo "region=${region}" >> ~/.aws/config
                        echo "output=json" >> ~/.aws/config
                        chmod 600 ~/.aws/credentials ~/.aws/config


                        aws s3 ls                  
                    '''
                }
            }
        }

        stage('Build Application') {
            steps {
                echo 'Building.........'
                sh 'mvn -B package'
            }
        }
        // stage('Build Docker Image') {
        //     steps {
        //         echo 'Building....'
        //         sh "docker build -t quickbite/logic:${params.new_version_tag} ."
        //     }
        // }
        stage('Test Application') {
            steps {
                echo 'Testing.........'
            }
        }
        stage('Analysis Application') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh """
                    ${scannerHome}/bin/sonar-scanner \
                      -Dsonar.projectKey=quickbite-logic \
                      -Dsonar.projectName=quickbite-logic \
                      -Dsonar.sources=. \
                      -Dsonar.sources=src/main/java/ \
                      -Dsonar.language=java \
                      -Dsonar.java.binaries=target/classes \
                      -Dsonar.host.url=$SONAR_HOST_URL
                    """
                }
            }
        }
        stage('Push Docker Image/ Artifact') {
            steps {
                // sh "docker login -u ${params.DOCKERHUB_USERNAME} -p ${params.dockerhub_password}"
                // sh "docker push huynguyen2025/logic:${params.new_version_tag}"
                
                sh "aws ecr get-login-password --region ca-central-1 | docker login --username AWS --password-stdin 846040891095.dkr.ecr.ca-central-1.amazonaws.com"
                sh "docker tag quickbite/logic:${params.new_version_tag} 846040891095.dkr.ecr.ca-central-1.amazonaws.com/quickbite/logic:${params.new_version_tag}"
                sh "docker push 846040891095.dkr.ecr.ca-central-1.amazonaws.com/quickbite/logic:${params.new_version_tag}"
            }
        }
        stage('Deploy Application') {
            steps {
                echo 'Deploying.....'
            }
        }
    }
}
