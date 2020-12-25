package com.github.edu.boot2.admin.util;

/**
 * 使用DBUtil查询时，ORACLE中使用的相关sql语句写法
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/12
 */
enum OracleSqlEnum implements ISqlService{

    QUERY_SQL_ENTITY_USER_LOGIN("SELECT T.ID,T.NAME,T.USER_ID,T.YHSF,T.YHSX,T.DLCS FROM T_SYS_USER T WHERE T.USER_ID = ? AND T.IS_LOCKED != "
            +ConstantEnum.ENUM_STATE_YES.getNum()+
            " AND T.ZT =  "+ConstantEnum.ENUM_STATE_QY.getNum())//查询登录用户信息

    ,
    QUERY_SQL_ALL_USER_ROLE_BYUSERID("SELECT T.ID,T.NAME,T.CODE,T.RTYPE FROM T_SYS_ROLE T " +
            "LEFT JOIN T_SYS_ROLE_USER S ON T.ID= S.ROLE_ID WHERE " +
            "T.ZT = "+ConstantEnum.ENUM_STATE_QY.getNum()+" AND S.USER_ID = ? ORDER BY T.PX DESC ")//查询用户角色信息
    ;
    private String sql;

    OracleSqlEnum(String sql){
        this.sql=sql;
    }

    public String getSql() {
        return sql;
    }
}
