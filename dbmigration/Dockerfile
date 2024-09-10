FROM liquibase/liquibase:latest

WORKDIR /liquibase

COPY src/main/resources/db/changelog /liquibase/changelog
COPY src/main/resources/liquibase.properties /liquibase/liquibase.properties


WORKDIR /liquibase
USER root
RUN chmod -R 644 /liquibase/changelog/changes

ENTRYPOINT ["liquibase", "--changeLogFile=changelog/changelog-master.xml", "update"]



