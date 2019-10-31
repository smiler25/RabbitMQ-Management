package com.smiler.rabbitadministration.queues.filter;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

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