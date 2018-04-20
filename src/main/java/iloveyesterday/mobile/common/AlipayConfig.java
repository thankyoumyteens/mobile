package iloveyesterday.mobile.common;

import iloveyesterday.mobile.util.PropertiesUtil;

public final class AlipayConfig {

    // 商户appid
    public static String APPID = PropertiesUtil.getProperty("alipay.appid");
    // 私钥
    public static String RSA_PRIVATE_KEY = PropertiesUtil.getProperty("alipay.rsaPrivateKey");
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = PropertiesUtil.getProperty("alipay.notifyUrl");
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = PropertiesUtil.getProperty("alipay.returnUrl");
    // 请求网关地址
    public static String URL = PropertiesUtil.getProperty("alipay.url");
    // 编码
    public static String CHARSET = PropertiesUtil.getProperty("alipay.charset");
    // 返回格式
    public static String FORMAT = PropertiesUtil.getProperty("alipay.format");
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = PropertiesUtil.getProperty("alipay.alipayPublicKey");
    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = PropertiesUtil.getProperty("alipay.signtype");
}
