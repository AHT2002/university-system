FROM openjdk:17
EXPOSE 8080
ADD target/university-system.jar university-system.jar

ENTRYPOINT ["java", "-jar", "/university-system.jar"]