package com.smiler.rabbitmanagement;

import android.app.Application;

import com.smiler.rabbitmanagement.profiles.Profile;

import lombok.Getter;
import lombok.Setter;

public class ManagementApplication extends Application {
    @Getter @Setter
    private Profile profile;
}
