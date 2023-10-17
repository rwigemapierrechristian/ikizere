FROM openjdk:19
ADD target/ussd-0.0.1-SNAPSHOT.war springboot-Docker.war
ENTRYPOINT [ "java","-war","springboot-Docker.war" ]