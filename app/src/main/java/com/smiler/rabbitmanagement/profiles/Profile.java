package com.smiler.rabbitmanagement.profiles;

import android.app.Activity;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import static com.smiler.rabbitmanagement.Constants.STATE_PROFILE_ID;

@Accessors(chain = true)
@ToString
@Data
@Entity(tableName = "profile")
public class Profile {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String host;
    public String login;
    public String password;
    public String authKey;

    @Ignore
    private Boolean storeCredentials;

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

    public Profile(Profile profile, boolean copyCredentials) {
        this.title = profile.title;
        this.host = profile.host;
        this.login = profile.login;
        if (copyCredentials) {
            this.password = profile.password;
            this.authKey = profile.authKey;
        }
    }

    private static String generateAuthKey(String login, String password) {
        String credentials = String.format("%s:%s", login, password);
        return Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
    }

    private Profile(String title, String host, String authKey) {
        this.title = title;
        this.host = host;
        this.authKey = authKey;
    }

    public void saveCurrent(Activity activity) {
        activity.getPreferences(Context.MODE_PRIVATE).edit().putInt(STATE_PROFILE_ID, id).apply();
    }

    public static int getSavedId(Activity activity) {
        SharedPreferences statePref = activity.getPreferences(Context.MODE_PRIVATE);
        return statePref.getInt(STATE_PROFILE_ID, -1);
    }

}
