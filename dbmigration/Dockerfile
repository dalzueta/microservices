FROM liquibase/liquibase:latest

WORKDIR /liquibase

COPY src/main/resources/db/changelog /liquibase/changelog
COPY src/main/resources/liquibase.properties /liquibase/liquibase.properties

#RUN ls
WORKDIR /liquibase
#RUN ls
#ENTRYPOINT ["liquibase", "--changeLogFile=changelog/changelog-master.xml", "update"]



# Crear el script que ejecutará ambos comandos
RUN echo '#!/bin/bash' > /liquibase/entrypoint.sh \
    && echo 'ls /liquibase' >> /liquibase/entrypoint.sh \
    && echo 'liquibase --changeLogFile=changelog/changelog-master.xml update' >> /liquibase/entrypoint.sh \
    && chmod +x /liquibase/entrypoint.sh

# Usar el script como entrypoint
ENTRYPOINT ["/liquibase/entrypoint.sh"]