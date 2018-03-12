package iloveyesterday.mobile.common;

public enum ResponseCode {
    SUCCESS(0,"成功"),
    ERROR(1,"失败"),
    NEED_LOGIN(10,"请登陆"),
    ILLEGAL_ARGUMENT(2,"参数错误"),
    NO_PRIVILEGE(3, "无权限");

    private final int code;
    private final String msg;


    ResponseCode(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }
}
