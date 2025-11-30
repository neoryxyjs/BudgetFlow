# Dockerfile para Railway - BudgetFlow Backend
# Multi-stage build para optimizar el tamaño de la imagen

# Etapa 1: Build con Maven
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar solo el pom.xml primero (para cache de dependencias)
COPY backend/pom.xml ./backend/
RUN cd backend && mvn dependency:go-offline -B

# Copiar el resto del código fuente
COPY backend/ ./backend/

# Compilar la aplicación
WORKDIR /app/backend
RUN mvn clean package -DskipTests

# Etapa 2: Runtime (imagen más liviana)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiar el JAR desde la etapa de build
COPY --from=build /app/backend/target/budgetflow-backend-1.0.0.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Variable de entorno para el puerto (Railway la proporciona)
ENV PORT=8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:${PORT}/api/users || exit 1

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java -jar -Dserver.port=${PORT} app.jar"]

