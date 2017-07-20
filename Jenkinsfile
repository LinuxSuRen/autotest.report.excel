pipeline {
  agent any
  triggers {
    pollSCM('H/5 * * * *')
  }
  
  stages {
    stage('Example') {
      steps {
        echo 'hello jenkins'
      }
    }
  }
}

node {
  def mvnHome
  stage('Preparation') {
    git 'https://github.com/LinuxSuRen/autotest.report.excel.git'
    mvnHome = tool 'M3'
  }
  
  stage('Clean') {
    if(isUnix()){
      sh "'${mvnHome}/bin/mvn' clean"
    }else{
      bat(/"${mvnHome}\bin\mvn" clean/)
    }
  }
  
  stage('JavaDoc') {
    if(isUnix()){
      sh "'${mvnHome}/bin/mvn' javadoc:jar -DdocSkip=false"
    }else{
      bat(/"${mvnHome}\bin\mvn" javadoc:jar -DdocSkip=false/)
    }
  }
  
  stage('Site') {
    if(isUnix()){
      sh "'${mvnHome}/bin/mvn' site"
    }else{
      bat(/"${mvnHome}\bin\mvn" site/)
    }
  }
  
  stage('Package') {
    if(isUnix()){
      sh "'${mvnHome}/bin/mvn' package"
    }else{
      bat(/"${mvnHome}\bin\mvn" package/)
    }
  }
  
  stage('Archive ApiDocs') {
    sh "tar -czvf target/apidocs.tar.gz -C target apidocs"
    archiveArtifacts 'target/apidocs.tar.gz'
  }
  
  stage('Archive Site') {
    sh "tar -czvf target/site.tar.gz -C target site"
    archiveArtifacts 'target/site.tar.gz'
  }
}
