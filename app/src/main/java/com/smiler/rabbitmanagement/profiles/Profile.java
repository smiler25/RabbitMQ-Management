package com.smiler.rabbitmanagement.profiles;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import lombok.Data;

@Data
public class Profile {
    private static final String STATE_TITLE = "TITLE";
    private static final String STATE_HOST = "HOST";
    private static final String STATE_KEY = "KEY";
    private String title;
    private String host;
    private String login;
    private String password;
    private String authKey;

    private static String generateAuthKey(String login, String password) {
        String credentials = String.format("%s:%s", login, password);
        return Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
    }

    private Profile(String title, String host, String authKey) {
        this.title = title;
        this.host = host;
        this.authKey = authKey;
    }

    public Profile(String title, String host, String login, String password) {
        if (host.endsWith("/")) {
            host = host.substring(0, host.length() - 1);
        }
        this.title = title;
        this.host = host;
        this.login = login;
        this.password = password;
        this.authKey = generateAuthKey(login, password);
    }

    public Profile save(Activity activity) {
        SharedPreferences statePref = activity.getPreferences(Context.MODE_PRIVATE);
        statePref.edit()
                .putString(STATE_TITLE, title)
                .putString(STATE_HOST, host)
                .putString(STATE_KEY, authKey)
                .apply();
        return this;
    }

    public static Profile getProfile(Activity activity) {
        SharedPreferences statePref = activity.getPreferences(Context.MODE_PRIVATE);
        return new Profile(
                statePref.getString(STATE_TITLE, null),
                statePref.getString(STATE_HOST, null),
                statePref.getString(STATE_KEY, null)
        );
    }
}
