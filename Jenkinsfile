pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/sylly237/ci_apitesting_demo.git'
            }
        }

stage('Test') {
    steps {
        sh '''
            docker run --rm -d --name selenium-chrome \
              --network jenkins-grid \
              selenium/standalone-chrome:latest

            sleep 10

            docker ps

            curl http://localhost:4444/status

            cd api-test-framework

            mvn test -Dselenium.remote=http://localhost:4444/wd/hub
        '''
    }

    post {
        always {
            sh 'docker stop selenium-chrome || true'
        }
    }
}

        stage('Results') {
            steps {
                junit 'api-test-framework/target/surefire-reports/*.xml'
            }
        }
    }

    post {
        always {
            emailext to: 'jade03@my.yorku.ca',
                     subject: "Nightly test results: ${currentBuild.result}",
                     body: "Build ${currentBuild.number} finished with status: ${currentBuild.result}"
        }
    }
}