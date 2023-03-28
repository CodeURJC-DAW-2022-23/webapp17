FROM alpine/git as git
WORKDIR /home/git
RUN git clone https://github.com/CodeURJC-DAW-2022-23/webapp17.git

FROM amazoncorretto:17-al2-jdk as builder
COPY --from=git home/git/webapp17 /webapp17
WORKDIR /webapp17/backend
RUN  ./mvnw package


FROM amazoncorretto:17

# Define el directorio de trabajo donde se encuentra el JAR
WORKDIR /usr/app/

# Copia el JAR del contenedor de compilación
COPY --from=builder /webapp17/backend/target/*.jar /usr/app/

# Indica el puerto que expone el contenedor
EXPOSE 8080

# Comando que se ejecuta al hacer docker run

ENTRYPOINT ["java","-jar","webapp17-0.0.1-SNAPSHOT.jar"]