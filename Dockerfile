FROM openjdk:17-slim AS build
WORKDIR /app

RUN apt-get update && apt-get install -y curl

RUN curl -o mvnw https://raw.githubusercontent.com/takari/maven-wrapper/master/mvnw \
    && chmod +x mvnw \
    && mkdir -p .mvn/wrapper \
    && curl -o .mvn/wrapper/maven-wrapper.jar https://repo1.maven.org/maven2/io/takari/maven-wrapper/0.5.6/maven-wrapper-0.5.6.jar

RUN chmod +x mvnw

RUN echo 'distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.6.3/apache-maven-3.6.3-bin.zip' > .mvn/wrapper/maven-wrapper.properties

COPY pom.xml ./

RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-slim AS deploy
WORKDIR /app
COPY --from=build /app/target/api.jar /app/api.jar
COPY src/main/resources/database-sql /app/resources/database-sql
ENTRYPOINT ["java", "-jar", "/app/api.jar"]