FROM eclipse-temurin:17-jre-jammy
EXPOSE 8761
ADD /target/serviceDesk.jar serviceDesk.jar
ENTRYPOINT ["java", "-jar", "serviceDesk.jar"]