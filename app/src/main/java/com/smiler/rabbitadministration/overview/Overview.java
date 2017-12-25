package com.smiler.rabbitadministration.overview;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Overview {
    @JsonProperty("management_version")
    private String managementVersion;

    @JsonProperty("rabbitmq_version")
    private String rabbitmqVersion;

    @JsonProperty("cluster_name")
    private String clusterName;

    @JsonProperty("erlang_version")
    private String erlangVersion;

    @JsonProperty("queue_totals")
    private OverviewQueueTotals queueTotals;

    @JsonProperty("object_totals")
    private OverviewObjectTotals objectTotals;


    @ToString
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class OverviewQueueTotals {
        @JsonProperty("messages_ready")
        private String messagesReady;

        @JsonProperty("messages_unacknowledged")
        private String messagesUnacked;

        private String messages;
    }

    @ToString
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class OverviewObjectTotals {
        private String  consumers;
        private String  queues;
        private String  exchanges;
        private String  connections;
        private String  channels;
    }

}
