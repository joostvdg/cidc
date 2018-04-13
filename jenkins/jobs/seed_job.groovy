pipeline {
    agent {
        label 'docker'
    }
    options {
        buildDiscarder logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '5', numToKeepStr: '5')
        timeout(5)
        timestamps()
        disableConcurrentBuilds()
        disableResume()
        durabilityHint 'PERFORMANCE_OPTIMIZED'
    }
    stages {
        stage('Pre-Clean') {
            steps {
                addInfoBadge id: 'test', text: 'Test'
                cleanWs()
            }
        }
        stage('Get Pipeline Library') {
            steps {
                library 'jenkins-pipeline-library'
            }
        }
        stage('Checkout') {
            steps {
                checkout scm
                sh 'ls -lath'
            }
        }
        stage('Execute JobDSL') {
            steps {
                jobDsl targets: 'jenkins/jobs/config_seed_jobs.groovy'
            }
        }
        stage('Post-Clean') {
            steps {
                cleanWs()
            }
        }
    }
}