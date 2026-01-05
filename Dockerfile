# Use an official Maven image to build the application
FROM maven:3.9.11-amazoncorretto-17 AS build

WORKDIR /app

# Copy the pom.xml and download the dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Runtime image (FIXED)
FROM amazoncorretto:17-alpine

WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/FarmOracle-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
