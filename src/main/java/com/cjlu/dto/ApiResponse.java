package com.cjlu.dto;


/*
* 响应类*/

public class ApiResponse<T> {
    private int code;
    private String msg;
    private T data;

    // 成功响应
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(1, "success", data);
    }

    // 失败响应
    public static <T> ApiResponse<T> error(String msg) {
        return new ApiResponse<>(0, msg, null);
    }

    // 构造器、Getter/Setter
    public ApiResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}