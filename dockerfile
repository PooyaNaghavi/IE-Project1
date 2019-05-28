FROM maven:3.3-jdk-8 as builder
WORKDIR /app
ADD . .
RUN mvn install -f /usr/src/mymaven
RUN mkdir /wars/
RUN find /app/ -iname '*.war' -exec cp {} /wars/ \;

FROM tomcat:7.0.90-jre8
COPY --from=builder /wars/* /usr/local/tomcat/webapps/
