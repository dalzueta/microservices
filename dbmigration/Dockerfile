FROM maven:3.8.1-openjdk-11 AS build

WORKDIR /app

COPY src/main/resources/db/changelog .

RUN mvn clean package

FROM openjdk:11-jre-slim

WORKDIR /app

COPY --from=build /app/target/dbmigration-1.0-SNAPSHOT.jar /app/db-migration.jar

# Copia el archivo liquibase.properties
COPY liquibase.properties /app/liquibase.properties

# Comando para ejecutar las migraciones de Liquibase
ENTRYPOINT ["java", "-jar", "/app/db-migration.jar", "update"]
