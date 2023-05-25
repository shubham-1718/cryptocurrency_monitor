FROM maven:3.8.1-openjdk-11-slim AS build

# Set working directory in container
WORKDIR /app

# Copy project POM file
COPY pom.xml .

# Download project dependencies
RUN mvn dependency:go-offline -B

# Copy project source code
COPY src ./src

# Build project
RUN mvn package -DskipTests

FROM adoptopenjdk:11-jre-hotspot

# Set working directory in container
WORKDIR /app

# Copy built WAR file from previous stage
COPY --from=build /app/target/cryptocurrency_monitor.war .

EXPOSE 8080

# Command to run web application
ENTRYPOINT ["java", "-jar", "cryptocurrency_monitor.war"]