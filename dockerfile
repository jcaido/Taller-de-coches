FROM openjdk:18-jdk-alpine
COPY target/Taller-de-coches-0.0.1-SNAPSHOT.jar taller.jar

ENTRYPOINT [ "java", "-jar" , "taller.jar"]
