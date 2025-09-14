pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-21-openjdk-amd64'
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Haciendo checkout del repositorio..."
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                echo "Construyendo y ejecutando tests con Maven..."
                sh 'mvn clean verify'
            }
        }

        stage('Verify Jacoco Exec') {
            steps {
                echo "Verificando archivos .exec generados por Jacoco..."
                sh 'ls -l target'
            }
        }
    }

    post {
        success {
            script {
                jacoco (
                    execPattern: '**/target/*.exec',
                    classPattern: '**/target/classes',
                    sourcePattern: '**/src/main/java',
                    changeBuildStatus: true,
                    minimumLineCoverage: '50'
                )
            }
            echo 'Build y cobertura completados con éxito'
        }

        failure {
            echo 'Build o tests fallaron'
        }
    }
}
