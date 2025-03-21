FROM openjdk:21

RUN apk add --no-cache bash

WORKDIR /app

COPY target/api-0.0.1-SNAPSHOT.jar /app/api.jar
COPY wait-for-it.sh /wait-for-it.sh

RUN chmod +x /wait-for-it.sh

EXPOSE 8080

CMD ["java", "-jar", "api.jar"]