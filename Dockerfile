FROM openjdk:11-jdk
COPY --from=build /home/gradle/project/build/libs/*.jar /app.jar
EXPOSE 9999
ENTRYPOINT ["java", "-jar", "/app.jar"]
