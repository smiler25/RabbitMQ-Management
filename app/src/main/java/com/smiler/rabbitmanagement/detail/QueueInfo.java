package com.smiler.rabbitmanagement.detail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueueInfo {
    private String name;
    private boolean durable;

    @JsonProperty("messages_ready")
    private long ready;

    @JsonProperty("messages_unacknowledged")
    private long unacked;

    @JsonProperty("messages")
    private long total;

    @JsonProperty("message_bytes_persistent")
    private long messageBytesPersistent;

    @JsonProperty("message_bytes_ram")
    private long messageBytesRam;

    @JsonProperty("message_bytes_unacknowledged")
    private long messageBytesUnacknowledged;

    @JsonProperty("message_bytes_ready")
    private long messageBytesReady;

    @JsonProperty("message_bytes")
    private long messageBytes;

    @JsonProperty("messages_persistent")
    private long messagesPersistent;

    @JsonProperty("messages_unacknowledged_ram")
    private long messagesUnacknowledgedRam;

    @JsonProperty("messages_ready_ram")
    private long messagesReadyRam;

    @JsonProperty("messages_ram")
    private long messagesRam;

    private long consumers;

}
