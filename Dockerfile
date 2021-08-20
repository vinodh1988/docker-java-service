FROM adoptopenjdk/openjdk15
ENV DATABASESERVICE_PORT=9494
ENV DATABASE_PATH=/h2db/mydb
COPY target/rest-microservice-1.0.0.jar app.jar
EXPOSE $DATABASESERVICE_PORT
ENTRYPOINT ["java","-jar","/app.jar"]
