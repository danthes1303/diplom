FROM openjdk:17-slim

WORKDIR /app

COPY mvnw .
COPY .mvn ./.mvn
RUN chmod +x ./mvnw

COPY pom.xml .

COPY src ./src

RUN ./mvnw install -DskipTests

COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
