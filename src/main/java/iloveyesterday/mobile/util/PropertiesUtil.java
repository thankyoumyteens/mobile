package iloveyesterday.mobile.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Properties props;

    static {
        String fileName = "mobile.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName), "UTF-8"));
        } catch (IOException e) {
            logger.error("配置文件读取异常", e);
        }
    }

    public static String getProperty(String key) {
        String value = props.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return value.trim();
    }

    public static String getProperty(String key, String defaultValue) {

        String value = props.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        return value.trim();
    }

    public static String getImageHost(String dir) {
        if (!StringUtils.isBlank(dir)) {
            return PropertiesUtil.getProperty("ftp.server.http.prefix") + dir;
        } else {
            return PropertiesUtil.getProperty("ftp.server.http.prefix");
        }
    }

    public static String getImageHost() {
        String server = PropertiesUtil.getProperty("aliyun.oss.server");
        if (server == null) {
            return "";
        }
        if (!server.endsWith("/")) {
            server = server + "/";
        }
        return server;
    }

}
