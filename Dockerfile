FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY --from=build /app/lib/opentelemetry-javaagent.jar /lib/opentelemetry-javaagent.jar
COPY --from=build /app/entrypoint.sh entrypoint.sh
RUN chmod +x entrypoint.sh
LABEL authors="abnerandrade"

EXPOSE 8080
ARG JAR_FILE=app.jar
ENV JAVA_OPTS="-XX:MaxRAMPercentage=80 -XX:MinRAMPercentage=20"

ENTRYPOINT ["./entrypoint.sh"]