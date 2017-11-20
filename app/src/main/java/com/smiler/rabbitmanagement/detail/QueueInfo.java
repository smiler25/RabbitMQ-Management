package com.smiler.rabbitmanagement.detail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.base.HumanString;

import java.util.ArrayList;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueueInfo {
    private String name;
    private String vhost;
    private Boolean durable;

    @JsonProperty("messages_ready")
    private Long ready;

    @JsonProperty("messages_unacknowledged")
    private Long unacked;

    @JsonProperty("messages")
    private Long total;

    @JsonProperty("message_bytes_persistent")
    private Long messageBytesPersistent;

    @JsonProperty("message_bytes_ram")
    private Long messageBytesRam;

    @JsonProperty("message_bytes_unacknowledged")
    private Long messageBytesUnacked;

    @JsonProperty("message_bytes_ready")
    private Long messageBytesReady;

    @JsonProperty("message_bytes")
    private Long messageBytes;

    @JsonProperty("messages_persistent")
    private Long messagesPersistent;

    @JsonProperty("messages_unacknowledged_ram")
    private Long messagesUnackedRam;

    @JsonProperty("messages_ready_ram")
    private Long messagesReadyRam;

    @JsonProperty("messages_ram")
    private Long messagesRam;

    private Long consumers;
    private String state;

    ArrayList<HumanString> getHumanStrings() {
        return new ArrayList<HumanString>() {{
            add(new HumanString(R.string.name, name));
            add(new HumanString(R.string.vhost, vhost));
            add(new HumanString(R.string.durable, Boolean.toString(durable)));
            add(new HumanString(R.string.state, state));
            add(new HumanString(R.string.consumers, Long.toString(consumers)));
            add(new HumanString(R.string.message_bytes, Long.toString(messageBytes)));
            add(new HumanString(R.string.message_bytes_ram, Long.toString(messageBytesRam)));
            add(new HumanString(R.string.message_bytes_ready, Long.toString(messageBytesReady)));
            add(new HumanString(R.string.message_bytes_unacked, Long.toString(messageBytesUnacked)));
            add(new HumanString(R.string.messages_ram, Long.toString(messagesRam)));
            add(new HumanString(R.string.messages_ready_ram, Long.toString(messagesReadyRam)));
            add(new HumanString(R.string.messages_unacked_ram, Long.toString(messagesUnackedRam)));
            add(new HumanString(R.string.messages_persistent, Long.toString(messagesPersistent)));
        }};
    }
}
