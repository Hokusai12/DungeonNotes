FROM eclipse-temurin:17
RUN mkdir ./opt/app
COPY /target/*.war ./opt/app/app.war
CMD ["java", "-jar", "/opt/app/app.war"]