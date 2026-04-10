pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    stages {

        stage('Build') {
            steps {
                bat '"%MAVEN_HOME%\\bin\\mvn" clean install'
            }
        }

        stage('Test') {
            steps {
                bat '"%MAVEN_HOME%\\bin\\mvn" test'
            }
        }
    }
}
