# Imagen base con Java 17
FROM openjdk:17-jdk-slim

# Crea una carpeta para la app
WORKDIR /app

# Copia el pom.xml y descarga dependencias
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline

# Copia el resto del código
COPY . .

# Empaqueta la app (salta los tests para que sea más rápido)
RUN ./mvnw clean package -DskipTests

# Expone el puerto (Render usará PORT como env var)
EXPOSE 8080

# Comando para arrancar
CMD ["java", "-jar", "target/*.jar"]
