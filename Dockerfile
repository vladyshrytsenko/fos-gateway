
FROM openjdk:21-jdk
WORKDIR /app
COPY target/fos-gateway-0.0.1.jar fos-gateway.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "fos-gateway.jar"]
