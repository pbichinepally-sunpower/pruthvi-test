#!groovy
@Library('jenkins-pipeline-shared@master') _

def slack_channel = 'pruthvi-test'
def BUILD_NUMBER 
def COMMIT


pipeline {
    agent { label 'nightvision-deploy' }

    options {
        buildDiscarder(logRotator(numToKeepStr: '25'))
        timestamps()
    }

    stages {

        stage ('Checkout') {
            steps {
                script {
                  def repo = checkout([$class: 'GitSCM',
                      branches: [[ name: 'main' ]],
                      doGenerateSubmoduleConfigurations: false,
                      extensions: [],
                      submoduleCfg: [],
                      userRemoteConfigs: scm.userRemoteConfigs
                    ])
                  commit = repo.GIT_COMMIT.take(7)
                  branch = repo.GIT_BRANCH.split('/')[1]
                  sendNotifications 'STARTED', "Job: '${env.JOB_NAME}', Branch-Commit: '${branch}-${commit}', BuildNumber: '${env.BUILD_NUMBER}'", slack_channel
                }                   
                    
                }
            }                      
    }    

    post {
        success {
            script {
                sshagent(['DIGITAL_GH']) {
                    sh("git config user.name 'NightvisionJenkins'")
                    sh("git config user.email 'digital_release@mysunpower.com'")
                    sh("git tag deploy-to-qa  ${GIT_COMMIT} ")
                    sh("git push origin deploy-to-qa")
                }
            }                                    
        }
              
    }    
          
}




