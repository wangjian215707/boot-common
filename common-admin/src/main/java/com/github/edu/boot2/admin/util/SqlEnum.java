package com.github.edu.boot2.admin.util;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/12
 */
public enum SqlEnum {

    QUERY_SQL_ENTITY_USER_LOGIN{
        @Override
        String getSql(String type) {
            if (type.toLowerCase().equals("oracle")) {//Oracle
                return OracleSqlEnum.QUERY_SQL_ENTITY_USER_LOGIN.getSql();
            }
            if (type.toLowerCase().equals("mysql")) {//
                return MySqlSqlEnum.QUERY_SQL_ENTITY_USER_LOGIN.getSql();
            }
            return null;
        }
    },
    QUERY_SQL_ALL_USER_ROLE_BYUSERID{
        @Override
        String getSql(String type) {
            if (type.toLowerCase().equals("oracle")) {//Oracle
                return OracleSqlEnum.QUERY_SQL_ALL_USER_ROLE_BYUSERID.getSql();
            }
            if (type.toLowerCase().equals("mysql")) {//
                return MySqlSqlEnum.QUERY_SQL_ALL_USER_ROLE_BYUSERID.getSql();
            }
            return null;
        }
    };

    abstract String getSql(String type);

    public String sql(String type) {
        return getSql(type);
    }
}
