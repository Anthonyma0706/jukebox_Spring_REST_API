# Define base docker image
FROM openjdk:11
# change to my name later
LABEL maintainer="Anthonyma0706"
ADD target/springboot-juke-rest-api-0.0.1-SNAPSHOT.jar springboot-juke-rest-api.jar
# copy -0.01-SNAPSHOT.jar INTO springboot-juke-rest-api.jar
# springboot-juke-rest-api.jar is a docker image that tells the docker how the application is going to run

# the command-line runner configuration
ENTRYPOINT ["java","-jar", "springboot-juke-rest-api.jar"]