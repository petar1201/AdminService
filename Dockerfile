FROM openjdk:17
EXPOSE 8080
ADD target/AdminService-0.0.1-SNAPSHOT.jar admin-service.jar
ADD ./src/main/resources/samples src/main/resources/samples
ENTRYPOINT ["java","-jar","/admin-service.jar"]