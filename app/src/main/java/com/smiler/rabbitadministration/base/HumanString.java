package com.smiler.rabbitadministration.base;

import lombok.Data;

@Data
public class HumanString {
    private int resId;
    private String value;

    public HumanString(int resId, String value) {
        this.resId = resId;
        this.value = value;
    }
}
