# Используем базовый образ с Java и Maven для сборки приложения
FROM maven:3.8.4-openjdk-17 AS build

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем файлы с зависимостями и файлы проекта в контейнер
COPY ./pom.xml ./pom.xml
COPY ./src ./src

# Собираем приложение с использованием Maven
RUN mvn clean package

# Создаем отдельный этап сборки для минимизации размера образа
FROM adoptopenjdk:17-jre-hotspot

# Копируем JAR-файл, созданный на предыдущем этапе, в контейнер
COPY --from=build /app/target/aston-0.0.1-SNAPSHOT.jar /app/aston.jar

# Указываем порт, который ваше приложение будет слушать
EXPOSE ${SERVER_PORT}

# Команда для запуска приложения при запуске контейнера
CMD ["java", "-jar", "/app/aston.jar"]
