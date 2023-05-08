package com.week10springsecurity.entity;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private String msg;
    private T data;
    private Object object;
}
