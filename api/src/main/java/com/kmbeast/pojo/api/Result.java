package com.kmbeast.pojo.api;

/**
 * 响应基类
 * @param <T>
 */
public class Result<T> {
    private Integer code; //响应状态码
    private String message; //响应消息

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }

    public Result() {
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
