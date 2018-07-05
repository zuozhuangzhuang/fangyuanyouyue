FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD user-server-0.0.1-SNAPSHOT.jar user-server.jar
#RUN bash -c 'touch /user-server.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/user-server.jar"]
EXPOSE 8761