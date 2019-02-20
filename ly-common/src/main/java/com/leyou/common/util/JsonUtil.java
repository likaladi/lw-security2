package com.leyou.common.util;

import com.google.gson.Gson;

public class JsonUtil {
    /**
     * 将bean转换成Json字符串
     * @param bean
     * @return
     */
    public static String beanToJSONString(Object bean) {
        return new Gson().toJson(bean);
    }

    /**
     * 将Json字符串转换成对象：
     * 注：转换后的对象使用前进行强转：例如bean bean1 = (bean)FormatUtil.JSONToObject(json, bean.class);
     * @param json
     * @param beanClass
     * @return
     */
    public static Object JSONToObject(String json,Class beanClass) {
        Gson gson = new Gson();
        Object res = gson.fromJson(json, beanClass);
        return res;
    }
}
