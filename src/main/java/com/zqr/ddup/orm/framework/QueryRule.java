package com.zqr.ddup.orm.framework;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 查询规则构造器，实现多条件复杂查询的条件拼接
 * Role 角色，Rule 尺子，规则
 * @Auther: qingruizhu
 * @Date: 2019-05-13 15:12
 */
public class QueryRule implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int ASC_ORDER = 101;
    public static final int DESC_ORDER = 102;
    public static final int LIKE = 1;
    public static final int IN = 2;
    public static final int NOTIN = 3;
    public static final int BETWEEN = 4;
    public static final int EQ = 5;
    public static final int NOTEQ = 6;
    public static final int GT = 7;
    public static final int GE = 8;
    public static final int LT = 9;
    public static final int LE = 10;
    public static final int ISNULL = 11;
    public static final int ISNOTNULL = 12;
    public static final int ISEMPTY = 13;
    public static final int ISNOTEMPTY = 14;
    public static final int AND = 201;
    public static final int OR = 202;

    private List<Rule> ruleList = new ArrayList<Rule>();
    private List<QueryRule> queryRuleList = new ArrayList<QueryRule>();
    private String propertyName;

    private QueryRule() {
    }

    private QueryRule(String propertyName) {
        this.propertyName = propertyName;
    }

    public static QueryRule getInstance() {
        return new QueryRule();
    }

    public List<Rule> getRuleList() {
        return ruleList;
    }

    public List<QueryRule> getQueryRuleList() {
        return queryRuleList;
    }

    public String getPropertyName() {
        return propertyName;
    }

    //添加升序规则
    public QueryRule addAscOrder(String propertyName) {
        this.ruleList.add(new Rule(ASC_ORDER, propertyName));
        return this;
    }

    //添加降序规则
    public QueryRule addDescOrder(String propertyName) {
        this.ruleList.add(new Rule(DESC_ORDER, propertyName));
        return this;
    }

    public QueryRule andIsNull(String propertyName) {
        this.ruleList.add(new Rule(ISNULL, propertyName).setAndOr(AND));
        return this;
    }

    public QueryRule addAndIsNotNull(String propertyName) {
        this.ruleList.add(new Rule(ISNOTNULL, propertyName).setAndOr(AND));
        return this;
    }

    public QueryRule andIsEmpty(String propertyName) {
        this.ruleList.add(new Rule(ISEMPTY, propertyName).setAndOr(AND));
        return this;
    }

    public QueryRule andIsNotEmpty(String propertyName) {
        this.ruleList.add(new Rule(ISNOTEMPTY, propertyName).setAndOr(AND));
        return this;
    }

    public QueryRule andLike(String propertyName, Object value) {
        this.ruleList.add(new Rule(LIKE, propertyName, new Object[]{value}).setAndOr(AND));
        return this;
    }

    public QueryRule andEqual(String propertyName, Object value) {
        this.ruleList.add(new Rule(EQ, propertyName, new Object[]{value}).setAndOr(AND));
        return this;
    }

    public QueryRule andBetween(String propertyName, Object... values) {
        this.ruleList.add(new Rule(BETWEEN, propertyName, values).setAndOr(AND));
        return this;
    }

    public QueryRule andIn(String propertyName, List<Object> values) {
        this.ruleList.add(new Rule(IN, propertyName, new Object[]{values}).setAndOr(AND));
        return this;
    }

    public QueryRule andIn(String propertyName, Object... values) {
        this.ruleList.add(new Rule(IN, propertyName, values).setAndOr(AND));
        return this;
    }

    public QueryRule andNotIn(String propertyName, List<Object> values) {
        this.ruleList.add(new Rule(NOTIN, propertyName, new Object[]{values}).setAndOr(AND));
        return this;
    }

    public QueryRule orNotIn(String propertyName, Object... values) {
        this.ruleList.add(new Rule(NOTIN, propertyName, values).setAndOr(OR));
        return this;
    }

    public QueryRule andNotEqual(String propertyName, Object value) {
        this.ruleList.add(new Rule(NOTEQ, propertyName, new Object[]{value}).setAndOr(AND));
        return this;
    }

    public QueryRule andGreaterThan(String propertyName, Object value) {
        this.ruleList.add(new Rule(GT, propertyName, new Object[]{value}).setAndOr(AND));
        return this;
    }

    public QueryRule andGreaterEqual(String propertyName, Object value) {
        this.ruleList.add(new Rule(GE, propertyName, new Object[]{value}).setAndOr(AND));
        return this;
    }

    public QueryRule andLessThan(String propertyName, Object value) {
        this.ruleList.add(new Rule(LT, propertyName, new Object[]{value}).setAndOr(AND));
        return this;
    }

    public QueryRule andLessEqual(String propertyName, Object value) {
        this.ruleList.add(new Rule(LE, propertyName, new Object[]{value}).setAndOr(AND));
        return this;
    }

    public QueryRule orIsNull(String propertyName) {
        this.ruleList.add(new Rule(ISNULL, propertyName).setAndOr(OR));
        return this;
    }

    public QueryRule orIsNotNull(String propertyName) {
        this.ruleList.add(new Rule(ISNOTNULL, propertyName).setAndOr(OR));
        return this;
    }

    public QueryRule orIsEmpty(String propertyName) {
        this.ruleList.add(new Rule(ISEMPTY, propertyName).setAndOr(OR));
        return this;
    }

    public QueryRule orIsNotEmpty(String propertyName) {
        this.ruleList.add(new Rule(ISNOTEMPTY, propertyName).setAndOr(OR));
        return this;
    }

    public QueryRule orLike(String propertyName, Object value) {
        this.ruleList.add(new Rule(LIKE, propertyName, new Object[]{value}).setAndOr(OR));
        return this;
    }

    public QueryRule orEqual(String propertyName, Object value) {
        this.ruleList.add(new Rule(EQ, propertyName, new Object[]{value}).setAndOr(OR));
        return this;
    }

    public QueryRule orBetween(String propertyName, Object... values) {
        this.ruleList.add(new Rule(BETWEEN, propertyName, values).setAndOr(OR));
        return this;
    }

    public QueryRule orIn(String propertyName, List<Object> values) {
        this.ruleList.add(new Rule(IN, propertyName, new Object[]{values}).setAndOr(OR));
        return this;
    }

    public QueryRule orIn(String propertyName, Object... values) {
        this.ruleList.add(new Rule(IN, propertyName, values).setAndOr(OR));
        return this;
    }

    public QueryRule orNotEqual(String propertyName, Object value) {
        this.ruleList.add(new Rule(NOTEQ, propertyName, new Object[]{value}).setAndOr(OR));
        return this;
    }

    public QueryRule orGreaterThan(String propertyName, Object value) {
        this.ruleList.add(new Rule(GT, propertyName, new Object[]{value}).setAndOr(OR));
        return this;
    }

    public QueryRule orGreaterEqual(String propertyName, Object value) {
        this.ruleList.add(new Rule(GE, propertyName, new Object[]{value}).setAndOr(OR));
        return this;
    }

    public QueryRule orLessThan(String propertyName, Object value) {
        this.ruleList.add(new Rule(LT, propertyName, new Object[]{value}).setAndOr(OR));
        return this;
    }

    public QueryRule orLessEqual(String propertyName, Object value) {
        this.ruleList.add(new Rule(LE, propertyName, new Object[]{value}).setAndOr(OR));
        return this;
    }





    protected class Rule implements Serializable {
        private static final long serialVersionUID = 1L;
        private int type;//规则类型
        private String property_Name;
        private Object[] values;
        private int andOr = AND;

        public Rule(int paramInt, String paramString) {
            this.type = paramInt;
            this.property_Name = paramString;
        }

        public Rule(int paramInt, String paramString, Object[] paramValues) {
            this.type = paramInt;
            this.property_Name = paramString;
            this.values = paramValues;
        }

        public int getType() {
            return type;
        }

        public String getProperty_Name() {
            return property_Name;
        }

        public Object[] getValues() {
            return values;
        }

        public int getAndOr() {
            return andOr;
        }

        public Rule setAndOr(int andOr) {
            this.andOr = andOr;
            return this;

        }
    }

}
