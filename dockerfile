FROM maven:3.3-jdk-8 as builder
COPY . /usr/src/mymaven
WORKDIR /usr/src/mymaven
RUN mvn install -f /usr/src/mymaven
RUN mkdir /usr/src/wars/
RUN find /usr/src/mymaven/ -iname '*.war' -exec cp {} /usr/src/wars/ \;

FROM tomcat:7.0.90-jre8
COPY --from=builder /usr/src/wars/* /usr/local/tomcat/webapps/
