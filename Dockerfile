FROM openjdk:11-jdk
VOLUME /tmp
EXPOSE 9999
ADD ./build/libs/ChallenMungs-0.0.1-SNAPSHOT.jar app.jar
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

