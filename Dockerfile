# Build stage
FROM maven:3.9.11-amazoncorretto-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage (FIXED)
FROM amazoncorretto:17

WORKDIR /app

COPY --from=build /app/target/FarmOracle-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 10000

ENTRYPOINT ["java", "-jar", "app.jar"]
