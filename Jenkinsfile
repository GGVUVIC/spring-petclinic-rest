pipeline {
    agent any

    tools {
        jdk 'JDK17'       // Nom del JDK configurat a Jenkins
        maven 'Maven'     // Nom del Maven configurat a Jenkins
    }

    environment {
        SONAR_SCANNER_HOME = tool 'SonarScanner' // Nom del SonarScanner configurat
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Tests') {
            steps {
                sh 'mvn clean verify'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Coverage') {
            steps {
                sh 'mvn jacoco:report'
                archiveArtifacts artifacts: 'target/site/jacoco/**', fingerprint: true
            }
        }

        stage('SonarQube Scan') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh '''
                    ${SONAR_SCANNER_HOME}/bin/sonar-scanner \
                    -Dsonar.projectKey=backend-petclinic \
                    -Dsonar.sources=src/main/java \
                    -Dsonar.tests=src/test/java \
                    -Dsonar.java.binaries=target/classes \
                    -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}
