FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD goods-server-0.0.1-SNAPSHOT.jar goods-server.jar
#RUN bash -c 'touch /goods-server.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/goods-server.jar"]
EXPOSE 8761