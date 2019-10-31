package com.smiler.rabbitadministration;

import android.app.Application;

import com.smiler.rabbitadministration.profiles.Profile;

import androidx.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

public class ManagementApplication extends Application {
    @Getter @Setter @Nullable
    private Profile profile;
}
