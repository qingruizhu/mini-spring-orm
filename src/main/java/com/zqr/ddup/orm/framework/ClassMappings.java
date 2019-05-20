package com.zqr.ddup.orm.framework;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2019-05-16 17:04
 */
public class ClassMappings {

    private static final Set<Class<?>> SUPPORTED_SQL_OBJECTS = new HashSet<Class<?>>();

    private ClassMappings() {
    }

    static {
        //默认支持自动类型转换
        Class<?>[] classes = {
                boolean.class, Boolean.class,
                short.class, Short.class,
                int.class, Integer.class,
                long.class, Long.class,
                float.class, Float.class,
                double.class, Double.class,
                String.class,
                Date.class,
                Timestamp.class,
                BigDecimal.class};
        SUPPORTED_SQL_OBJECTS.addAll(Arrays.asList(classes));
    }

    static boolean supportSqlObject(Class<?> clzz) {
        return clzz.isEnum() || SUPPORTED_SQL_OBJECTS.contains(clzz);
    }

    public static Map<String, Method> findPublicGetters(Class<?> clazz) {
        Map<String, Method> getMethods = new HashMap<String, Method>();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (Modifier.isStatic(method.getModifiers())) {
                continue;
            }
            if (method.getParameterTypes().length != 0) {
                continue;
            }
            if (method.getName().equals("getClass")) {
                continue;
            }
            Class<?> returnType = method.getReturnType();
            if (void.class.equals(returnType)) {
                continue;
            }
            if (!supportSqlObject(returnType)) {
                continue;
            }
            //处理返回参数为boolean类型的method
            if ((returnType.equals(boolean.class)
                    || returnType.equals(Boolean.class)) && method.getName().startsWith("is") && method.getName().length() > 2) {
                getMethods.put(getGetterName(method), method);
                continue;
            }
            if (!method.getName().startsWith("get")) {
                continue;
            }
            if (method.getName().length() < 4) {
                continue;
            }
            getMethods.put(getGetterName(method), method);
        }
        return getMethods;
    }
    //去掉get，首字母小写
    public static String getGetterName(Method getter) {
        String name = getter.getName();
        if (name.startsWith("is")) {
            name = name.substring(2);
        } else {
            name = name.substring(3);
        }
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }

    public static Map<String,Method> findPublicSetters(Class<?> clzz) {
        Map<String, Method> setMethods = new HashMap<String, Method>();
        Method[] methods = clzz.getMethods();
        for (Method method : methods) {
            if (Modifier.isStatic(method.getModifiers())) {
                continue;
            }
            if (method.getParameterTypes().length != 1) {
                continue;
            }
            if (!void.class.equals(method.getReturnType())) {
                continue;
            }
            if (!method.getName().startsWith("set")) {
                continue;
            }
            if (method.getName().length() < 4) {
                continue;
            }
            if (!SUPPORTED_SQL_OBJECTS.contains(method.getParameterTypes()[0])) {
                continue;
            }
            setMethods.put(getSetterName(method), method);
        }
        return setMethods;
    }

    //去掉set，首字母小写
    private static String getSetterName(Method method) {
        String allName = method.getName();
        String name = allName.substring(3);
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }

    public static Field[] findFields(Class<?> clzz){
        return clzz.getDeclaredFields();
    }

}
