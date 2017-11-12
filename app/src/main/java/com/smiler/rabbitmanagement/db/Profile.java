package com.smiler.rabbitmanagement.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

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
}
