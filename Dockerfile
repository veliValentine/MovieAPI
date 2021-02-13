FROM openjdk:15
ADD target/api-0.0.1-SNAPSHOT.jar movieapi.jar
ENTRYPOINT ["java", "-jar", "movieapi.jar"]