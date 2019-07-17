FROM maven:3.6.1-ibmjava-8-alpine as builder

WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn package -DskipTests

FROM openjdk:8-jre-alpine
COPY --from=builder /usr/src/app/target/webservice-0.0.1-SNAPSHOT.jar /usr/share/bengyun/

ENTRYPOINT ["java", "-jar", "/usr/share/bengyun/webservice-0.0.1-SNAPSHOT.jar"]