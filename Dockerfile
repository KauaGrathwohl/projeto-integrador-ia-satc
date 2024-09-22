FROM openjdk:17-alpine AS build
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean package -DskipTests

RUN ls -l

FROM openjdk:17-alpine
WORKDIR /app

COPY --from=build /app/target/api.jar /app/api.jar 

ENTRYPOINT ["java", "-jar", "/app/api.jar"]
