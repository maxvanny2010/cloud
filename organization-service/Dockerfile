FROM openjdk:17.0.2-jdk as build
WORKDIR application
ARG JAR_FILE=target/*.jar
# Добавить файл jar приложения в контейнер(image)
COPY ${JAR_FILE} application.jar
# распаковать файл jar
RUN java -Djarmode=layertools -jar application.jar extract
# шаг 2
FROM openjdk:17.0.2-jdk
WORKDIR application
COPY --from=build application/dependencies/ ./
COPY --from=build application/spring-boot-loader/ ./
COPY --from=build application/snapshot-dependencies/ ./
COPY --from=build application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
