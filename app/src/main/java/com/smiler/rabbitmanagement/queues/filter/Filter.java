package com.smiler.rabbitmanagement.queues.filter;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@ToString
@Data
@Entity(tableName = "queue_filter")
public class Filter {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String value;
    public boolean regex;
}