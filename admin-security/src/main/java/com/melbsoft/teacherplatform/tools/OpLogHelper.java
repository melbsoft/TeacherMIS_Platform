package com.melbsoft.teacherplatform.tools;

import com.google.common.collect.Maps;

import java.util.Map;

public class OpLogHelper {
    private static ThreadLocal<Map<String, Object>> store = ThreadLocal.withInitial(() -> {
        return Maps.newHashMap();
    });

    public static Object put(String key, Object value) {
        Map<String, Object> map = store.get();
        return map.put(key, value);
    }


    public static Map<? extends String, ?> pop() {
        Map<String, Object> data = store.get();
        store.remove();
        return data;
    }
}
