package com.smiler.rabbitmanagement.connections;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.base.HumanString;

import java.util.ArrayList;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

import static com.smiler.rabbitmanagement.Constants.DATE_FORMATTER;

@ToString
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Connection {
    private String timeout;
    private String vhost;
    private String user;
    private String protocol;
    private Long connected_at;
    private String channel_max;
    private String ssl;
    private String ssl_hash;
    private String ssl_cipher;
    private String ssl_key_exchange;
    private String ssl_protocol;
    private String auth_mechanism;
    private String host;
    private String port;
    private String name;
    private String node;
    private String type;
    private String channels;
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
//        private String copyright;
        private String information;

        @JsonProperty("connection_name")
        private String connectionName;
    }

    ArrayList<HumanString> getHumanStrings() {
        return new ArrayList<HumanString>() {{
            add(new HumanString(R.string.name, name));
            add(new HumanString(R.string.vhost, vhost));
            add(new HumanString(R.string.state, state));
            add(new HumanString(R.string.heartbeat, timeout));
            add(new HumanString(R.string.user, user));
            add(new HumanString(R.string.node, node));
            add(new HumanString(R.string.type, type));
            add(new HumanString(R.string.host, host));
            add(new HumanString(R.string.port, port));
            add(new HumanString(R.string.protocol, protocol));
            add(new HumanString(R.string.connected_at, DATE_FORMATTER.format(new Date(connected_at))));
            add(new HumanString(R.string.channel_max, channel_max));
            add(new HumanString(R.string.channels, channels));
            add(new HumanString(R.string.authentication, auth_mechanism));
            add(new HumanString(R.string.ssl, ssl));
            add(new HumanString(R.string.ssl_hash, ssl_hash));
            add(new HumanString(R.string.ssl_cipher, ssl_cipher));
            add(new HumanString(R.string.ssl_key_exchange, ssl_key_exchange));
            add(new HumanString(R.string.ssl_protocol, ssl_protocol));
        }};
    }

    ArrayList<HumanString> getHumanStringsClient() {
        return new ArrayList<HumanString>() {{
            add(new HumanString(R.string.platform, clientProperties.platform));
            add(new HumanString(R.string.product, clientProperties.product));
            add(new HumanString(R.string.version, clientProperties.version));
            add(new HumanString(R.string.information, clientProperties.information));
        }};
    }

}
