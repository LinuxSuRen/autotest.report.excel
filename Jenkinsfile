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
  
  stage('Package') {
    if(isUnix()){
      sh "'${mvnHome}/bin/mvn' package"
    }else{
      bat(/"${mvnHome}\bin\mvn" package/)
    }
  }
  
  stage('Archive ApiDocs') {
    archiveArtifacts 'target/apidocs/*'
  }
}
