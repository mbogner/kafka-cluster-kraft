#!/bin/bash
OUT_DIR=context/tmp
SCALA_VERSION=2.13
KAFKA_VERSION=3.3.1

KAFKA_URL="https://downloads.apache.org/kafka/${KAFKA_VERSION}/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz"
KAFKA_FOLDER=kafka
KAFKA_FILE="kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz"

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" >/dev/null 2>&1 && pwd)"
cd "${DIR}" || exit 1
source "$DIR/_shared.sh" || exit 99

echo "##############################"
echo "# workdir: $(pwd)"
echo "# scala: ${SCALA_VERSION}"
echo "# kafka: ${KAFKA_VERSION}"
echo "# kafka_url: ${KAFKA_URL}"
echo "# kafka_file: ${KAFKA_FILE}"
echo "# out: ${OUT_DIR}"
echo "##############################"

download "${KAFKA_URL}" "${OUT_DIR}/${KAFKA_FOLDER}" "${KAFKA_FILE}"

docker compose build
#docker stop $(docker ps | awk '{print $1}' | grep -v CONTAINER) >> /dev/null 2>&1
#docker system prune -f --volumes >> /dev/null 2>&1
