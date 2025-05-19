FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/crud-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_crud_base_de_datos.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app_crud_base_de_datos.jar"]