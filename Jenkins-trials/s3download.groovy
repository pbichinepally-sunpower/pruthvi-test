#!groovy
@Library('jenkins-pipeline-shared@master') _

def services = 'unbound-site-inverters-sink ' 
def slack_channel = 'pruthvi-test'

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

        // stage ('SonarQube Analysis') {
        //     steps {
        //         script {
        //             sh """
        //             export JAVA_HOME=/usr/lib/jvm/java-11-openjdk
        //             ./gradlew dependencies
        //             """
        //         }

        //         withCredentials([string(credentialsId: 'SonarQube', variable: 'SONAR_TOKEN')]) {
        //             script {
        //                 withSonarQubeEnv("SonarQube") {
        //                     sh "${tool("SonarQubeScanner")}/bin/sonar-scanner \
        //                     -Dsonar.host.url=https://sonarqube.mysunpower.com/ \
        //                     -Dsonar.login=${SONAR_TOKEN} \
        //                     -Dsonar.projectKey=nightvision \
        //                     -Dsonar.projectName=nightvision \
        //                     -Dsonar.java.binaries=./services \
        //                     -Dsonar.java.libraries=./services \
        //                     -Dsonar.sources=."
        //                 }
        //             }
        //         }
        //     }
        // }

        // stage('Image build based on commit') {
        //     steps {
        //         withCredentials([
        //             file(credentialsId: 'GCP-CD-CREDS', variable: 'FILE'),
        //             string(credentialsId: 'NightvisionMdsDevUser', variable: 'MDS_DATABASE_USER'),
        //             string(credentialsId: 'NightvisionMdsDevPassword', variable: 'MDS_DATABASE_PASSWORD'),
        //             string(credentialsId: 'NightvisionUnboundSiteInvertersCodeGenerationDatabaseUser',
        //                     variable: 'UNBOUND_SITE_INVERTERS_DATABASE_USER'),
        //             string(credentialsId: 'NightvisionUnboundSiteInvertersCodeGenerationDatabasePassword',
        //                     variable: 'UNBOUND_SITE_INVERTERS_DATABASE_PASSWORD'),
        //             string(credentialsId: 'NightvisionSitesWithTimeZoneCodeGenerationDatabaseUser',
        //                     variable: 'SITES_WITH_TIME_ZONE_DATABASE_USER'),
        //             string(credentialsId: 'NightvisionSitesWithTimeZoneCodeGenerationDatabasePassword',
        //                     variable: 'SITES_WITH_TIME_ZONE_DATABASE_PASSWORD')
        //         ]) {
        //             script{
        //                 sh """
        //                 gcloud auth activate-service-account --key-file "${FILE}"
        //                 gcloud auth list
        //                 gcloud --quiet config set project spwr-cd-90fd
        //                 gcloud auth configure-docker --quiet
                        
        //                 export JAVA_HOME=/usr/lib/jvm/java-11-openjdk

        //                 export MDS_DATABASE_HOSTNAME=dm1hufwpszvwage.cnnwe8pmnp5z.us-west-2.rds.amazonaws.com
        //                 export MDS_DATABASE_NAME=edp_mds

        //                 export UNBOUND_SITE_INVERTERS_POSTGRES_HOST=du8q5ams3cfj2t.cnnwe8pmnp5z.us-west-2.rds.amazonaws.com
        //                 export UNBOUND_SITE_INVERTERS_POSTGRES_PORT=5432
        //                 export UNBOUND_SITE_INVERTERS_CODE_GENERATION_DATABASE_NAME=Unbound_Site_Dev_Generation

        //                 export SITES_WITH_TIME_ZONE_POSTGRES_HOST=duqzmtobtiuxly.cnnwe8pmnp5z.us-west-2.rds.amazonaws.com
        //                 export SITES_WITH_TIME_ZONE_POSTGRES_PORT=5432
        //                 export SITES_WITH_TIME_ZONE_CODE_GENERATION_DATABASE_NAME=sites_with_time_zone_code_generation

        //                 export KAFKA_ENVIRONMENT_PREFIX=test-

       
        //                 for element in $services;
        //                 do
        //                     cd ./services/\$element/ > /dev/null
        //                     skaffold build -p dev --default-repo=us.gcr.io/spwr-cd-90fd/nightvision --filename=skaffold.yml --file-output=\$element-${commit}.json
        //                     cd ../../ > /dev/null
        //                 done

        //                 #cd ./night-cli/ > /dev/null && skaffold build -p dev --default-repo=us.gcr.io/spwr-cd-90fd/nightvision --filename=skaffold.yml --file-output=night-cli-jumpbox-${GIT_COMMIT}.json && cd .. > /dev/null

        //                 """
        //             }
        //         }
        //     }
        // }


    }

    post {

        success {
            script {
                    withAWS(region:'us-west-2',credentials:'EDP_DEV_JENKINS'){
                        
                    def identity = awsIdentity()    
                        //s3Upload(bucket:"nightvision-artifacts", file: '*.json');
                            
                            sh("pwd")
                            s3Download(bucket:'nightvision-artifacts',
                                file:'/home/jenkins/workspace/nightvision-trial-job',
                                toPath:'**/*-${commit}.json',
                                force: true)
                                
                            sh ("ls -ltr")
                            archiveArtifacts artifacts: '**/*.json', fingerprint: true, onlyIfSuccessful: true
                                
                    }

            }
        }
    }
    
}