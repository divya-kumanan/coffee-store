# Use a base image with Java installed
FROM openjdk:11

# Set the working directory in the container
WORKDIR /app

# Copy the application jar file into the container
COPY target/coffee-store-0.0.1-SNAPSHOT.jar /app

# Expose the port that the application listens on
EXPOSE 8080

# Run the application when the container starts
CMD ["java", "-jar", "coffee-store-0.0.1-SNAPSHOT.jar"]