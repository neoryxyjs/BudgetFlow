# Multi-stage build para Spring Boot con Maven
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar archivos de Maven primero (para cache de dependencias)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar la aplicación
RUN mvn clean package -DskipTests

# Imagen final más liviana
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiar el JAR desde la etapa de build
COPY --from=build /app/target/budgetflow-backend-1.0.0.jar app.jar

# Exponer el puerto (Railway asignará el puerto automáticamente)
EXPOSE 8080

# Variable de entorno para el puerto
ENV PORT=8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java -jar -Dserver.port=${PORT} app.jar"]

