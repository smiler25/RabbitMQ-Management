package com.smiler.rabbitadministration;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;

public final class Constants {
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    static final String TAG_FRAGMENT_CONFIRM = "TAG_FRAGMENT_CONFIRM";
    static final String STATE_CURRENT_PAGE = "CURRENT_PAGE";
    public static final String STATE_PROFILE_ID = "CURRENT_PROFILE_ID";
    static final String STATE_FILTER_ID = "FILTER_ID";
    static final String STATE_SORT_TYPE = "SORT_TYPE";
    static final String STATE_SORT_ASC = "SORT_ASC";
    public static final String AMQP_URL_FORMAT = "amqp:///%s";

    public static final int REQUEST_TIMEOUT_MS = 10000;
    public static final int REQUEST_MAX_RETRIES = 3;
    public static final float REQUEST_BACKOFF_MULT = 2f;
}