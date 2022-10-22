#!/bin/bash
docker exec -ti kafka1 bash bin/kafka-topics.sh --bootstrap-server=kafka1:9194,kafka2:9294,kafka3:9394 --list
