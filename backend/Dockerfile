FROM openjdk:17-jdk-slim

#ARG DB_PW
#ENV DATABASE_USER_PASSWORD=${DB_PW}
#
#ARG DB_URL
#ENV DATABASE_URL=${DB_URL}
#
#ARG DB_USER
#ENV DATABASE_USER=${DB_USER}

ARG SPRING_ACTIVE_PROFILE=dev

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-XX:+UseSerialGC","-Xss1M","-Xms220m","-Xmx220m","-jar","/app.jar","--spring.profiles.active=","${SPRING_ACTIVE_PROFILE}"]
