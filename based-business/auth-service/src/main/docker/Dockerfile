FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD auth-service-0.0.1-SNAPSHOT.jar auth-service.jar
#RUN bash -c 'touch /auth-service.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/auth-service.jar"]
EXPOSE 8761