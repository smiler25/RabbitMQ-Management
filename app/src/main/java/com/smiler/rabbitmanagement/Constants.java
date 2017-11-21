package com.smiler.rabbitmanagement;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;

public final class Constants {
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static final String STATE_PROFILE_ID = "CURRENT_PROFILE_ID";
    public static final String STATE_FILTER_ID = "FILTER_ID";
    public static final String STATE_SORT_TYPE = "SORT_TYPE";
    public static final String STATE_SORT_ASC = "SORT_ASC";

}
