FROM alpine/git as git
WORKDIR /home/git
RUN git clone https://github.com/CodeURJC-DAW-2022-23/webapp17.git

FROM eclipse-temurin:17-jdk-alpine
COPY --from=git home/git/webapp17 webapp17
RUN ls; sleep 5
WORKDIR webapp17/backend
RUN  ./mvnw install
ENTRYPOINT ["java","-jar","target/webapp17-0.0.1-SNAPSHOT.jar"]