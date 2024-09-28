FROM openjdk:17-alpine AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-alpine AS deploy
WORKDIR /app
COPY --from=build /app/target/api.jar /app/api.jar 
COPY src/main/resources/database-sql /app/resources/database-sql
ENTRYPOINT ["java", "-jar", "/app/api.jar"]
