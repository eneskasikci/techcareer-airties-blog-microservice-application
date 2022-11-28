FROM openjdk:19-alpine
ADD target/BlogApp-0.0.1-SNAPSHOT.jar blogapp.jar
EXPOSE 7777
ENTRYPOINT ["java","-jar","/blogapp.jar","-web -webAllowOthers -tcp -tcpAllowOthers -browser"]