package iloveyesterday.mobile.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

public final class JsonUtil {

    public static <T> T stringToObject(String json) {
        T obj = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            obj = objectMapper.readValue(json,
                    new TypeReference<T>() {
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static BigDecimal getDetailPrice(List<LinkedHashMap<String, Object>> list) {
        BigDecimal totalMoney = new BigDecimal("0");
        for (LinkedHashMap<String, Object> item : list) {
            String key = item.get("key").toString();
            List<LinkedHashMap<String, String>> value = (List<LinkedHashMap<String, String>>) item.get("value");
            int selected = Integer.parseInt(item.get("selected").toString());
            LinkedHashMap<String, String> map = value.get(selected);
            BigDecimal money = new BigDecimal(map.get("money"));
            totalMoney = BigDecimalUtil.add(totalMoney.doubleValue(), money.doubleValue());
        }
        return totalMoney;
    }

    public static BigDecimal getDetailPrice(String detail) {
        List<LinkedHashMap<String, Object>> list = JsonUtil.stringToObject(detail);
        return JsonUtil.getDetailPrice(list);
    }
}
