#!/bin/bash
if [[ "" == "${KAFKA_CLUSTER_ID}" ]]; then
  echo "KAFKA_CLUSTER_ID not set. please set it to proper uuid"
  exit 1
fi

CONFIG_FILE=/opt/kafka/config/kraft/server-local.properties
./config.sh /opt/kafka/kraft-server.properties "${CONFIG_FILE}"

echo "starting cluster ${KAFKA_CLUSTER_ID} with config ${CONFIG_FILE}"
kafka-storage.sh format -t "${KAFKA_CLUSTER_ID}" -c "${CONFIG_FILE}"
kafka-server-start.sh "${CONFIG_FILE}"
