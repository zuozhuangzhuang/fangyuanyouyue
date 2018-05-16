FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD zipkin-server-0.0.1-SNAPSHOT.jar zipkin-server.jar
#RUN bash -c 'touch /zipkin-server.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/zipkin-server.jar"]
EXPOSE 8761