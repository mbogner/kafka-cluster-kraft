# kafka-cluster-kraft

This project creates a simple kafka image that can be used in kraft clusters (kafka cluster without zookeeper). To speed
the container build up a `build.sh` script was added that downloads kafka to local disk instead of downloading it within
the container build process itself. After running the script you should have all required files on your disk, and you
can run `docker compose build` without the `build.sh` script. So it's just a download helper. With the container built
you are able to spin up the 3 node sample cluster via `docker compose up -d`.

Kafka is started with the default server.properties file that ships with kafka if no properties are set in the
environment. Additional fields are added at the end of this file for added configuration options.

```properties
# https://cwiki.apache.org/confluence/display/KAFKA/KIP-631%3A+The+Quorum-based+Kafka+Controller
process.roles=broker,controller
node.id=1
controller.quorum.voters=1@kafka1:9093
listeners=PLAINTEXT://:9092,CONTROLLER://:9093,EXTERNAL://:9094
inter.broker.listener.name=PLAINTEXT
advertised.listeners=PLAINTEXT://kafka1:9093,EXTERNAL://kafka1:9094
controller.listener.names=CONTROLLER
listener.security.protocol.map=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT,SSL:SSL,SASL_PLAINTEXT:SASL_PLAINTEXT,SASL_SSL:SASL_SSL,EXTERNAL:PLAINTEXT
num.network.threads=3
num.io.threads=8
socket.send.buffer.bytes=102400
socket.receive.buffer.bytes=102400
socket.request.max.bytes=104857600
log.dirs=/tmp/kraft-combined-logs
num.partitions=1
num.recovery.threads.per.data.dir=1
offsets.topic.replication.factor=1
transaction.state.log.replication.factor=1
transaction.state.log.min.isr=1
log.retention.hours=168
log.segment.bytes=1073741824
log.retention.check.interval.ms=300000
# non default properties
broker.rack=rack1
default.replication.factor=1
```

All these properties can be changed through env variables which are the key uppercase and points replaced by underscore.
For example `process.roles` becomes `PROCESS_ROLES`. For the replacement a script named `config.sh` is used that uses
`sed` to replace placeholders (see `context/kraft-server.properties`) with defaults or env variables if those are set.

The external listener is meant for connections from outside the docker container. The default listener named
`PLAINTEXT` on port 9092 didn't work for connections from outside the container so a separate listener named `EXTERNAL`
was added on port 9094 which can be configured via `EXTERNAL_PORT` env variable.

Setting the `KAFKA_HOSTNAME` is very important because default hostnames within docker containers are random and kafka
needs proper names for cluster communication.

## docker compose

There is a simple compose file that creates a 3 node kraft cluster. The included spring boot application is configured
to use that cluster and listen to a single topic named `testing`.

The script `generate-cluster-id.sh` shows how to generate a proper cluster-id as used in the compose file.

```shell
./generate-cluster-id.sh
0V90lJfMRtm1-niD6hpEXg
```

Kafka requires cluster-ids like this and will fail to start if you're not generating them with the tool. Of course, you
don't have to use `generate-cluster-id.sh` and run the kafka script directly.

There is also a sample how to run commands on the cluster via the included kafka scripts from within a cluster
container. The script `list-topics.sh` connects to the container `kafka1` and runs a kafka script to list all topics.

To simplify scripting container names were fixed in the sample by setting `container_name` in the compose file.

## kafdrop

For better overview `kafdrop` was included in the compose file and configured to be available
under http://localhost:9000.
