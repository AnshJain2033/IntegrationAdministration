FROM openjdk:8
WORKDIR /opt
ENV PORT 8083
EXPOSE 8083
COPY target/DIS-administration.jar /opt
ENTRYPOINT exec java $JAVA_OPT -jar DIS-administration.jar