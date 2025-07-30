
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app


COPY . .


RUN ./mvnw clean package -DskipTests


FROM eclipse-temurin:21-jre


WORKDIR /app


COPY --from=build /app/target/*.jar app.jar

# Expose port (change if needed)EXPOSE 8080


ENTRYPOINT ["java", "-jar", "app.jar"]
