package com.kmbeast.pojo.api;

/**
 * 请求响应码
 */
public enum ResultCode {

    REQUEST_SUCCESS(200), // 请求成功码
    REQUEST_ERROR(400); //请求失败码

    private Integer code;

    @Override
    public String toString() {
        return "ResultCode{" +
                "code=" + code +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    ResultCode(Integer code) {
        this.code = code;
    }
}
