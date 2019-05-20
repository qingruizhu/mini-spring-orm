package com.zqr.ddup.orm.framework;

/**
 * @Description:    sql排序组件
 * @Auther: qingruizhu
 * @Date: 2019-05-13 17:16
 */
public class Order {
    /**
     * 升序/降序
     */
    private boolean ascending;
    /**
     * 升降序的字段
     */
    private String propertyName;

    public Order(boolean ascending, String propertyName) {
        this.ascending = ascending;
        this.propertyName = propertyName;
    }

    public static Order asc(String propertyName){
        return new Order(true, propertyName);
    }
    public static Order desc(String propertyName){
        return new Order(false, propertyName);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
