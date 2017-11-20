package com.smiler.rabbitmanagement.channels;


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
    private String globalPrefetchCount;

    @JsonProperty("prefetch_count")
    private String prefetchCount;

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

    ArrayList<HumanString> getHumanStrings() {
        return new ArrayList<HumanString>() {{
            add(new HumanString(R.string.name, name));
            add(new HumanString(R.string.vhost, vhost));
            add(new HumanString(R.string.user, user));
            add(new HumanString(R.string.number, number));
            add(new HumanString(R.string.node, node));
            add(new HumanString(R.string.state, state));
            add(new HumanString(R.string.global_prefetch_count, globalPrefetchCount));
            add(new HumanString(R.string.prefetch_count, prefetchCount));
            add(new HumanString(R.string.messages_uncommitted, messagesUncommitted));
            add(new HumanString(R.string.messages_unconfirmed, messagesUnconfirmed));
            add(new HumanString(R.string.messages_unacked, messagesUnacknowledged));
            add(new HumanString(R.string.acks_uncommitted, acksUncommitted));
            add(new HumanString(R.string.consumer_count, consumerCount));
            add(new HumanString(R.string.confirm, confirm));
            add(new HumanString(R.string.transactional, transactional));
            add(new HumanString(R.string.idle_since, idle_since));
            add(new HumanString(R.string.reductions, reductions));
        }};
    }

}
