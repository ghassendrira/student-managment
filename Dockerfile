# Utiliser une image OpenJDK officielle stable
FROM eclipse-temurin:17-jdk-focal

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le jar généré par Maven dans le conteneur
ARG JAR_FILE=target/student-management-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Exposer le port par défaut de Spring Boot
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
