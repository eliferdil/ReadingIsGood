FROM adoptopenjdk/openjdk11
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME
COPY target/*.war app.war
EXPOSE 8080
CMD ["java", "-jar", "app.war"]