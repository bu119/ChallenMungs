FROM openjdk:11-jdk
EXPOSE 9999
COPY build/libs/*.jar
ENTRYPOINT ["java","-jar","/app.jar"] 
