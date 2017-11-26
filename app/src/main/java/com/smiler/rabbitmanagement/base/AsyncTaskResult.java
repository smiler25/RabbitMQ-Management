package com.smiler.rabbitmanagement.base;

import lombok.Data;

@Data
public class AsyncTaskResult<T> {
    private T result;
    private Exception error;

    public AsyncTaskResult(T result) {
        super();
        this.result = result;
    }

    public AsyncTaskResult(Exception error) {
        super();
        this.error = error;
    }
}