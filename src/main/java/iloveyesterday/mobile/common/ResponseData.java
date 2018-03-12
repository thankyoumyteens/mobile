package iloveyesterday.mobile.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;

@JsonSerialize(include =  JsonSerialize.Inclusion.NON_NULL)
public class ResponseData<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    @JsonIgnore
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    private ResponseData(int status) {
        this.status = status;
        this.msg = msg;
    }

    private ResponseData(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ResponseData(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ResponseData(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResponseData<T> success() {
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg());
    }

    public static <T> ResponseData<T> successMessage(String msg) {
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(),
                msg);
    }

    public static <T> ResponseData<T> success(T data) {
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(),
                data);
    }

    public static <T> ResponseData<T> success(String msg, T data) {
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(),
                msg, data);
    }

    public static <T> ResponseData<T> error() {
        return new ResponseData<>(ResponseCode.ERROR.getCode(),
                ResponseCode.ERROR.getMsg());
    }

    public static <T> ResponseData<T> error(String msg) {
        return new ResponseData<>(ResponseCode.ERROR.getCode(), msg);
    }

    public static <T> ResponseData<T> error(int code, String msg) {
        return new ResponseData<>(code, msg);
    }
}
