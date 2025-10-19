
# Estágio 1: Build da Aplicação com Maven
FROM maven:3.8-openjdk-17 AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Estágio 2: Criação da Imagem Final com JRE
FROM openjdk:17-jre-slim
WORKDIR /app
# Copia o JAR do estágio de build
COPY --from=build /app/target/*.jar app.jar
# Expõe a porta que o Spring usa (8081 no seu caso)
EXPOSE 8081
# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]