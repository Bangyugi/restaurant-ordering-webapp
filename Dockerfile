FROM eclipse-temurin:17
LABEL maintainer="bangtranvan08@gmail.com"
WORKDIR /app
COPY target/restaurant-ordering-webapp-0.0.1-SNAPSHOT.jar /app/restaurant-ordering-webapp.jar
ENTRYPOINT ["java", "-jar", "restaurant-ordering-webapp.jar"]
