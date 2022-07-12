# шаг 1 create image:goal create and unpack file .jar
# Базовый образ, содержащий среду Java времени выполнения
# FROM openjdk:17.0.2-jdk as build
# Добавить информацию о владельце
# LABEL maintainer="Illary Huaylupo <illaryhs@gmail.com>"
# Файл jar приложения invoke from pom.xml
#ARG JAR_FILE
# Добавить файл jar приложения в контейнер(image)
# COPY ${JAR_FILE} app.jar
# распаковать файл jar
# RUN mkdir -p target/dependency && (cd target/dependency; jar -xf /app.jar)
# шаг 2
# Та же среда Java времени выполнения
# FROM openjdk:17.0.2-jdk
# Добавить том, ссылающийся на каталог /tmp
# VOLUME /tmp
# Скопировать распакованное приложение в новый контейнер
# ARG DEPENDENCY=/target/dependency
# COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
# COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
# COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
# запустить приложение
# ENTRYPOINT ["java","-cp","app:app/lib/*","com.cloud.license.CloudApplication"]

# шаг 1 create image:goal create and unpack file .jar
# Базовый образ, содержащий среду Java времени выполнения
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
