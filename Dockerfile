FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} *.jar
ENTRYPOINT ["java","-jar","/*.jar"]
#251cb1e36448aafa45d2e29766250fe6b5330c1c
#sudo docker run --name InnoMetricsDB -e POSTGRES_PASSWORD=1nn0M3tr1c5 -e POSTGRES_USER=postgres -e POSTGRES_DB=gihthub -d postgres
#sudo docker run --name gitapi --link InnoMetricsDB:postgres -p 9095:9095 -d moiwa/gitapispring_web 251cb1e36448aafa45d2e29766250fe6b5330c1c GitApiSpring