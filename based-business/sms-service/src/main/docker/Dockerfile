FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD sms-server-0.0.1-SNAPSHOT.jar sms-server.jar
#RUN bash -c 'touch /sms-server.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/sms-server.jar"]
EXPOSE 8761