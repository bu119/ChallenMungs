FROM openjdk:11-jdk
EXPOSE 9999
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"] 
