# ---------- Build stage ----------
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos lo mínimo para cachear dependencias
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x mvnw \
 && ./mvnw -B -DskipTests dependency:go-offline

# Copiamos el código y empaquetamos
COPY src src
RUN ./mvnw -B -DskipTests package

# ---------- Run stage (runtime ligero) ----------
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copiamos el JAR construido
COPY --from=build /app/target/*.jar /app/app.jar

# Puerto interno (Render usará la env PORT igualmente)
EXPOSE 8080

# Perfil por defecto en contenedor
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS=""

# Ejecuta respetando la PORT de Render
CMD ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar /app/app.jar"]
