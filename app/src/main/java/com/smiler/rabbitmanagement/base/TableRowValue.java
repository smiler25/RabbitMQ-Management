package com.smiler.rabbitmanagement.base;

import lombok.Data;

@Data
public class TableRowValue {
    private String title;
    private String value;

    public TableRowValue(String title, String value) {
        this.title = title;
        this.value = value;
    }
}
