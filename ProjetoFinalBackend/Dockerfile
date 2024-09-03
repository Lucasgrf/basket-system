# Use uma imagem base Java para o seu aplicativo
FROM openjdk:21-jdk-slim

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo JAR da aplicação para o diretório /app no container
COPY target/*.jar app.jar

# Expõe a porta que o Spring Boot usa (por padrão 8080)
EXPOSE 8080

# Comando para executar o JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
