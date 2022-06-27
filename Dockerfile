FROM openjdk:17

VOLUME /tmp

RUN rm -f /etc/localtime \
&& ln -sv /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
&& echo "Asia/Shanghai" > /etc/timezone

MAINTAINER he tao <study.hetao@foxmail.com>

ADD ./target/*.jar app.jar

EXPOSE 8080

WORKDIR /tmp

ENTRYPOINT java --add-opens java.base/java.lang=ALL-UNNAMED -Xms128M -Xmx256M -jar /app.jar
