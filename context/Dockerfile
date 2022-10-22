FROM azul/zulu-openjdk-alpine:17-latest
MAINTAINER m@mbo.dev

RUN apk add --no-cache bash

ADD tmp/kafka/kafka_2.13-3.3.1.tgz /opt
RUN mv /opt/kafka* /opt/kafka
WORKDIR /opt/kafka
COPY docker_cmd.sh /opt/kafka/docker_cmd.sh
COPY kraft-server.properties /opt/kafka/kraft-server.properties
COPY config.sh /opt/kafka/config.sh

ENV PATH="/opt/kafka/bin:/opt/jdk/bin:${PATH}"
ENV PS1="[\u:\w]\$ "

VOLUME /tmp/kraft-combined-logs

CMD [ "bash", "-c", "/opt/kafka/docker_cmd.sh" ]
