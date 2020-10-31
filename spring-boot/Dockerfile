FROM openjdk:11
ADD target/docker-spring-hotels.jar docker-spring-hotels.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "docker-spring-hotels.jar"]