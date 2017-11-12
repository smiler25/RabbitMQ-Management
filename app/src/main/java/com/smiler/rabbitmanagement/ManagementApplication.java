package com.smiler.rabbitmanagement;

import android.app.Application;

import com.smiler.rabbitmanagement.profiles.ActiveProfile;

import lombok.Getter;
import lombok.Setter;

public class ManagementApplication extends Application {
    @Getter @Setter
    private ActiveProfile profile;
}
