package dev.mbo.kafka.kraft

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

@Component
class SampleListener {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["testing"])
    fun listen(
        payload: String,
        @Header(KafkaHeaders.RECEIVED_TOPIC) topic: String,
        @Header(KafkaHeaders.OFFSET) offset: Long
    ) {
        log.info("Received: {} from {} @ {}", payload, topic, offset)
        if (payload.startsWith("fail")) {
            throw RuntimeException("failed")
        }
    }

}
