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
                sh 'cd api-test-framework && mvn test'
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