FROM openjdk:11
ARG JAR_FILE=target/githubAgent.jar
COPY ${JAR_FILE} githubAgent.jar
ENTRYPOINT ["java","-jar","/githubAgent.jar"]
#251cb1e36448aafa45d2e29766250fe6b5330c1c
#sudo docker run --name db -e POSTGRES_PASSWORD=git -e POSTGRES_USER=git -e POSTGRES_DB=gitapi -d postgres
#sudo docker run --name gitapi --link db:postgres -p 8080:8080 -d gitapispring_web  251cb1e36448aafa45d2e29766250fe6b5330c1c GitApiSpring