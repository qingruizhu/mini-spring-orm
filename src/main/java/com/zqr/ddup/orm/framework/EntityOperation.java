package com.zqr.ddup.orm.framework;


import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 实例对象的反射操作
 * @Auther: qingruizhu
 * @Date: 2019-05-17 13:40
 */
public class EntityOperation<T> {
    private Logger log = Logger.getLogger(EntityOperation.class);
    public Class<T> entityClass = null;//泛型实体class对象
    public final Map<String, PropertyMapping> mappings;
    public final RowMapper<T> rowMapper;

    public final String tableName;
    public String allColumn = "*";
    public Field pkField;


    public EntityOperation(Class<T> clzz, String pk) throws Exception {
        if (!clzz.isAnnotationPresent(Entity.class)) {
            throw new Exception("在" + clzz.getName() + "中没有找到Entiry注解，不能做ORM映射");
        }
        this.entityClass = clzz;
        Table table = entityClass.getAnnotation(Table.class);
        if (null != table) {
            this.tableName = table.name();
        } else {
            this.tableName = entityClass.getSimpleName();
        }
        Map<String, Method> getters = ClassMappings.findPublicGetters(entityClass);
        Map<String, Method> setters = ClassMappings.findPublicSetters(entityClass);
        Field[] fields = ClassMappings.findFields(entityClass);
        //填充主键字段
        fillPkFieldAndAllColumn(pk, fields);
        this.mappings = getPropertyMappings(getters, setters, fields);
        this.allColumn = this.mappings.keySet().toString().
                replace("[", "").
                replace("]", "").
                replaceAll(" ", "");
        this.rowMapper = createRowMapper();
    }

    private RowMapper<T> createRowMapper() {

        return null;
    }

    private Map<String, PropertyMapping> getPropertyMappings(Map<String, Method> getters, Map<String, Method> setters, Field[] fields) {
        HashMap<String, PropertyMapping> mappings = new HashMap<String, PropertyMapping>();
        String propertyName;
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            //去除transient的影响
            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }
            //处理is开头的字段
            propertyName = field.getName();
            if (propertyName.startsWith("is")) {
                propertyName = propertyName.substring(2);
            }
            //首字母小写
            propertyName = Character.toLowerCase(propertyName.charAt(0)) + propertyName.substring(1);
            Method getter = getters.get(propertyName);
            Method setter = setters.get(propertyName);
            if (null == getter || null == setter) {
                continue;
            }
            //处理mappings中的key
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                mappings.put(column.name(), new PropertyMapping(getter, setter, field));
            } else {
                mappings.put(field.getName(), new PropertyMapping(getter, setter, field));
            }
        }
        return mappings;
    }

    /**
     * 填充主键字段
     *
     * @param pk
     * @param fields
     */
    private void fillPkFieldAndAllColumn(String pk, Field[] fields) {
        try {
            if (!StringUtils.isEmpty(pk)) {
                pkField = entityClass.getDeclaredField(pk);
                pkField.setAccessible(true);
                return;
            }
        } catch (Exception e) {
            log.debug("没有找到主键列，主键列名称必须与属性名相同");
        }
        for (int i = 0; i < fields.length; i++) {
            Field fd = fields[i];
            Id id = fd.getAnnotation(Id.class);
            if (null != id) {
                pkField = fd;
                break;
            }
        }
    }


}

class PropertyMapping {

    final boolean insertable;
    final boolean updatable;
    final String columnName;
    final boolean id;
    final Method getter;
    final Method setter;
    final Class enumClass;
    final String fieldName;

    public PropertyMapping(Method getter, Method setter, Field field) {
        this.getter = getter;
        this.setter = setter;
        this.enumClass = getter.getReturnType().isEnum() ? getter.getReturnType() : null;
        Column column = field.getAnnotation(Column.class);
        this.insertable = column == null || column.insertable();
        this.updatable = column == null || column.updatable();
        this.columnName = column == null ? ClassMappings.getGetterName(getter) : ("".equals(column.name()) ? ClassMappings.getGetterName(getter) : column.name());
        this.id = field.isAnnotationPresent(Id.class);
        this.fieldName = field.getName();
    }

    @SuppressWarnings("unchecked")
    Object get(Object target) throws Exception {
        Object r = getter.invoke(target);
        return enumClass == null ? r : Enum.valueOf(enumClass, (String) r);
    }

    @SuppressWarnings("unchecked")
    void set(Object target, Object value) throws Exception {
        if (enumClass != null && value != null) {
            value = Enum.valueOf(enumClass, (String) value);
        }
        //BeanUtils.setProperty(target, fieldName, value);
        try {
            if (value != null) {
                setter.invoke(target, setter.getParameterTypes()[0].cast(value));
            }
        } catch (Exception e) {
            e.printStackTrace();
            /**
             * 出错原因如果是boolean字段 mysql字段类型 设置tinyint(1)
             */
            System.err.println(fieldName + "--" + value);
        }

    }
}
