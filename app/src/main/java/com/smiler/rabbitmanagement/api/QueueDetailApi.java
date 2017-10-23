package com.smiler.rabbitmanagement.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smiler.rabbitmanagement.detail.QueueInfo;

import java.io.IOException;

public class QueueDetailApi {

    public static QueueInfo getInfo() {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            String personJsonStr = "{\"consumer_details\":[],\"incoming\":[],\"deliveries\":[],\"messages_details\":{\"rate\":0.0},\"messages\":726,\"messages_unacknowledged_details\":{\"rate\":0.0},\"messages_unacknowledged\":0,\"messages_ready_details\":{\"rate\":0.0},\"messages_ready\":726,\"reductions_details\":{\"rate\":0.0},\"reductions\":112835,\"node\":\"rabbit@data_rabbit_1\",\"arguments\":{},\"exclusive\":false,\"auto_delete\":false,\"durable\":true,\"vhost\":\"/\",\"name\":\"OLD_S3_fields.23\",\"message_bytes_paged_out\":0,\"messages_paged_out\":0,\"backing_queue_status\":{\"mode\":\"default\",\"q1\":0,\"q2\":0,\"delta\":[\"delta\",\"undefined\",0,0,\"undefined\"],\"q3\":725,\"q4\":1,\"len\":726,\"target_ram_count\":\"infinity\",\"next_seq_id\":16384,\"avg_ingress_rate\":0.0,\"avg_egress_rate\":0.0,\"avg_ack_ingress_rate\":0.0,\"avg_ack_egress_rate\":0.0},\"head_message_timestamp\":null,\"message_bytes_persistent\":97308,\"message_bytes_ram\":97308,\"message_bytes_unacknowledged\":0,\"message_bytes_ready\":97308,\"message_bytes\":97308,\"messages_persistent\":726,\"messages_unacknowledged_ram\":0,\"messages_ready_ram\":726,\"messages_ram\":726,\"garbage_collection\":{\"minor_gcs\":4,\"fullsweep_after\":65535,\"min_heap_size\":233,\"min_bin_vheap_size\":46422,\"max_heap_size\":0},\"state\":\"running\",\"recoverable_slaves\":null,\"consumers\":0,\"exclusive_consumer_tag\":null,\"policy\":null,\"consumer_utilisation\":null,\"idle_since\":\"2017-10-20 13:37:49\",\"memory\":1115432}";
            return mapper.readValue(personJsonStr, QueueInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
