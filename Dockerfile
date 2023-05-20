FROM openjdk:11-jdk
COPY target/*.jar USAINUA_Admin.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "USAINUA_Admin.jar"]
