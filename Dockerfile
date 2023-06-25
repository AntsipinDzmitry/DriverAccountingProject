FROM openjdk:17-jdk
WORKDIR /app
COPY . /app
RUN ./mvnw clean package -DskipTests
CMD ["java", "-jar", "target/DriverAccountingProject-0.0.1-SNAPSHOT.jar"]