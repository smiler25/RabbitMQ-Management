package com.smiler.rabbitadministration.base.api;

public final class RequestPath {
    public static final String CHANNELS = "/api/channels";
    public static final String CONNECTIONS = "/api/connections";
    public static final String OVERVIEW = "/api/overview";
    public static final String QUEUES_LIST = "/api/queues";
    public static final String QUEUE_DETAIL = "/api/queues/%s/%s/";
    public static final String QUEUE_DELETE = "/api/queues/%s/%s";
    public static final String QUEUE_PURGE = "/api/queues/%s/%s/contents";
    public static final String QUEUE_MOVE = "/api/parameters/shovel/%s/move";

    public static final String DEFAULT_VHOST = "/";
    public static final String DEFAULT_VHOST_URL = "%2F";
}
