package com.smiler.rabbitmanagement.connections;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Connection {
    private String timeout;
    private String vhost;
    private String user;
    private String protocol;
    private long connected_at;
    private long channel_max;
    private boolean ssl;
    private String ssl_hash;
    private String ssl_cipher;
    private String ssl_key_exchange;
    private String ssl_protocol;
    private String auth_mechanism;
    private String peer_host;
    private String host;
    private String peer_port;
    private String port;
    private String name;
    private String node;
    private String type;
    private long channels;
    private String state;

    @JsonProperty("client_properties")
    private ClientProperties clientProperties;

    @ToString
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private class ClientProperties {
        private String product;
        private String version;
        private String platform;
        private String copyright;
        private String information;

        @JsonProperty("connection_name")
        private String connectionName;
    }

}
