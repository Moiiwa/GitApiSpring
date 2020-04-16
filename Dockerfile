FROM openjdk:11
ARG JAR_FILE=target/github_agent.jar
COPY ${JAR_FILE} github_agent.jar
ENTRYPOINT ["java","-jar","/github_agent.jar"]
#251cb1e36448aafa45d2e29766250fe6b5330c1c
#sudo docker run --name db -e POSTGRES_PASSWORD=git -e POSTGRES_USER=git -e POSTGRES_DB=git -d postgres
#sudo docker run --name gitapi --link db:postgres -p 8080:8080 -d gitapispring_web  251cb1e36448aafa45d2e29766250fe6b5330c1c GitApiSpring