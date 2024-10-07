FROM openjdk:17-alpine

WORKDIR /app

COPY target/money-exchange-0.0.1-SNAPSHOT.jar /app/money-exchange.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/money-exchange.jar"]