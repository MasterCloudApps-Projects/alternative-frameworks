FROM maven:3.6-jdk-8 as builder
COPY pom.xml /code/pom.xml
WORKDIR /code
RUN mvn dependency:go-offline
COPY . /code/
RUN mvn package

FROM openjdk:8-jre
COPY --from=builder /code/target/*.jar /usr/app/springboot-chat.jar
WORKDIR /usr/app
EXPOSE 5000
CMD [ "java", "-jar", "springboot-chat.jar" ] 

# BUILD  docker build -t maes95/webchat .
# RUN_APP docker run -p 5000:5000 maes95/webchat
# DEPLOY:
# - heroku login
# - heroku container:login
# - heroku container:push web
# - heroku container:release web
