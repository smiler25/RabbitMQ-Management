package com.smiler.rabbitmanagement.channels;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Channel {
    private String reductions;
    private String vhost;
    private String user;
    private String number;
    private String name;
    private String node;
    private String state;
    private String confirm;
    private String transactional;
    private String idle_since;

    @JsonProperty("global_prefetch_count")
    private int globalPrefetchCount;

    @JsonProperty("prefetch_count")
    private int prefetchCount;

    @JsonProperty("acks_uncommitted")
    private String acksUncommitted;

    @JsonProperty("messages_uncommitted")
    private String messagesUncommitted;

    @JsonProperty("messages_unconfirmed")
    private String messagesUnconfirmed;

    @JsonProperty("messages_unacknowledged")
    private String messagesUnacknowledged;

    @JsonProperty("consumer_count")
    private String consumerCount;

    @JsonProperty("connection_details")
    private ConnectionsDetails connectionDetails;

    @ToString
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private class ConnectionsDetails {
        private String  consumers;
        private String  queues;
        private String  exchanges;
        private String  connections;
        private String  channels;
    }

}
