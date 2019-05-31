package com.lgb.bootweb.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

public class ObjectUtils {

    private static final String JAVAP = "java.";
    private static final String JAVADATESTR = "java.util.Date";

    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map map = new HashMap();
        Class clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);//obj类中的成员变量为private,故必须进行此操作
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }

    public static Map<String, String> objectToMapString(String timeFormatStr, Object obj, String[] excludeFields)
            throws IllegalAccessException {
        Map map = new HashMap();

        if (excludeFields.length != 0) {
            List list = Arrays.asList(excludeFields);
            objectTransfer(timeFormatStr, obj, map, list);
        } else {
            objectTransfer(timeFormatStr, obj, map, null);
        }
        return map;
    }

    private static Map<String, String> objectTransfer(String timeFormatStr, Object obj, Map<String, String> map, List<String> excludeFields)
            throws IllegalAccessException {
        boolean isExclude = false;

        String formatStr = "YYYY-MM-dd HH:mm:ss";

        if ((timeFormatStr != null) && (!timeFormatStr.isEmpty())) {
            formatStr = timeFormatStr;
        }
        if (excludeFields != null) {
            isExclude = true;
        }
        Class clazz = obj.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = clazz.getSimpleName() + "." + field.getName();

            if ((!isExclude) || (!excludeFields.contains(fieldName))) {
                field.setAccessible(true);
                Object value = field.get(obj);
                Class valueClass = value.getClass();
                if (valueClass.isPrimitive()) {
                    map.put(fieldName, value.toString());
                } else if (valueClass.getName().contains("java.")) {
                    if (valueClass.getName().equals("java.util.Date")) {
                        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
                        Date date = (Date) value;
                        String dataStr = sdf.format(date);
                        map.put(fieldName, dataStr);
                    } else {
                        map.put(fieldName, value.toString());
                    }
                } else objectTransfer(timeFormatStr, value, map, excludeFields);
            }
        }
        return map;
    }
}
