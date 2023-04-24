FROM alpine/git as git
WORKDIR /home/git
RUN git clone https://github.com/CodeURJC-DAW-2022-23/webapp17.git

FROM node:slim as frontend
COPY --from=git home/git/webapp17/frontend/gustosa/ /webapp17/frontend/gustosa/
WORKDIR /webapp17/frontend/gustosa/
RUN npm run install-legacy
RUN npx ng build --base-href='/new/'

FROM amazoncorretto:17-al2-jdk as builder
COPY --from=git home/git/webapp17/backend/pom.xml /webapp17/backend/
COPY --from=git home/git/webapp17/backend/src/ /webapp17/backend/src/
COPY --from=git home/git/webapp17/backend/.mvn/ /webapp17/backend/.mvn/
COPY --from=git home/git/webapp17/backend/mvnw /webapp17/backend/mvnw
COPY --from=frontend /webapp17/frontend/gustosa/dist/gustosa/ /webapp17/backend/src/main/resources/static/new/
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