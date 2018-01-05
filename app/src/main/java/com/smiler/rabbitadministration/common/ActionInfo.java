package com.smiler.rabbitadministration.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActionInfo {
    private ActionTypes action;
    private String text;
}