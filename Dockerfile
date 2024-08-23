FROM openjdk:18
 
WORKDIR /app
 
COPY ./target/CCTS-0.0.1-SNAPSHOT.jar /app
 
EXPOSE 7070
 
CMD ["java", "-jar", "CCTS-0.0.1-SNAPSHOT.jar"]
 
