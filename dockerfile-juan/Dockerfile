# Stage 1: Build with Maven
# FROM maven:3.9-amazoncorretto-25-alpine AS builder   # Imágen no oficial (ligero)
# FROM maven:eclipse-temurin AS builder                # Imágen oficial (pesado)
FROM juan321/maven:3.9-amazoncorretto-25-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run with Java
# FROM eclipse-temurin:25-jre-alpine  # Imágen no oficial (ligero)
# FROM openjdk:12-ea-25-jdk-oracle    # Imágen oficial (pesado)
FROM juan321/eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]


# CONSTRUIR UNA IMÁGEN MEDIANTE UN Dockerfile EN UNA CARPETA
# docker build -t juan321/springboot-sqlserver:3.0 -f dockerfile-juan/Dockerfile .


# EJECUTAR Y SUBIR TU IMÁGEN GENERADA A DOCKERHUB
# docker run -d --name springboot-sqlserver -p 8085:8085 juan321/springboot-sqlserver:3.0
# docker push juan321/springboot-sqlserver:3.0


# DESCARGAR Y SUBIR MAVEN A TU USUARIO DOCKERHUB
# docker pull maven:3.9-amazoncorretto-25-alpine
# docker tag 2246c1050783 juan321/maven:3.9-amazoncorretto-25-alpine
# docker push juan321/maven:3.9-amazoncorretto-25-alpine


# DESCARGAR Y SUBIR JDK25 A TU USUARIO DOCKERHUB
# docker pull eclipse-temurin:25-jre-alpine
# docker tag 5fcc27581b23 juan321/eclipse-temurin:25-jre-alpine
# docker push juan321/eclipse-temurin:25-jre-alpine


# EJECUTAR UN CONTENEDOR SQL SERVER MEDIANTE COMANDO
# docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=Admin12345" -p 1433:1433 --name sqlserver -d juan321/sql-server:2022