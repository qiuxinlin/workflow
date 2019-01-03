package com.scxys.activiti.bean;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @Author: qiuxinlin
 * @Description: 统一返回类
 * @Date: 2018/9/7
 */
public class ResponseVO<T> implements Serializable {
    private static final long serialVersionUID = 3991293264646158705L;
    private String msg;
    private int code;
    private T data;

    public ResponseVO() {
    }

    private ResponseVO(int code, String msg) {
        this(code, msg, null);
    }

    private ResponseVO(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ResponseVO<T> errorRes(int code, String msg) {
        return new ResponseVO(code, msg);
    }

    public static <T> ResponseVO<T> errorRes(int code, String msg, T data) {
        return new ResponseVO(code, msg, data);
    }

    public static <T> ResponseVO<T> successRes(String msg, T data) {
        return new ResponseVO(HttpStatus.OK.value(), msg, data);
    }

    public static <T> ResponseVO<T> successRes(String msg) {
        return new ResponseVO(HttpStatus.OK.value(), msg);
    }

    public static <T> ResponseVO<T> successRes() {
        return new ResponseVO(HttpStatus.OK.value(), "success");
    }
}
