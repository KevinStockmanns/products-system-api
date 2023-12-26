# Utilizar la imagen oficial de Maven que incluye Java
FROM maven:3.8.4-openjdk-17-slim

# Establecer el directorio de trabajo en /app
WORKDIR /app

# Copiar el código fuente de la aplicación
COPY . /app

# Empaquetar la aplicación con Maven
RUN mvn clean package




# Crear una nueva imagen basada en OPENDK
FROM openjdk:17-jdk-slim

# Exponer el puerto que utilizara la aplicación
EXPOSE 8080

# Crear el directorio /app/ e incluir los indices de Lucene
RUN mkdir -p /app/
COPY --from=build /app/indicesLuceme /app/indicesLuceme

# Copiar el archivo JAR construido desde la etapa anterior
COPY --from=build /app/build/libs/analisis-0.0.1-SNAPSHOT.jar /app/analisis-0.0.1-SNAPSHOT.jar

# Establecer el punto de entrada para ejecutar la aplicacion
ENTRYPOINT [ "java", "-jar", "/app/analisis-0.0.1-SNAPSHOT.jar" ]



# # Instala Maven
# RUN apt-get update && \
#     apt-get install -y maven

# # Construye el proyecto con Maven
# RUN mvn clean package

# # Comando para ejecutar tu aplicación al iniciar el contenedor
# CMD ["java", "-jar", "target/tu-aplicacion.jar"]