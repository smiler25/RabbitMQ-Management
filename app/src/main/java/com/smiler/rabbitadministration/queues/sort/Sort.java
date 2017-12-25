package com.smiler.rabbitadministration.queues.sort;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@ToString
@Data
public class Sort {
    private SortTypes type;
    private Boolean ascending;
}