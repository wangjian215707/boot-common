package com.github.edu.boot2.admin.util;

/**
 * 系统管理常量
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/12
 */
public enum ConstantEnum {

    ENUM_STATE_JY(0,"禁用"),

    ENUM_STATE_QY(1,"启用"),

    MENU_TYPE_WJJ(1,"文件夹"),

    MENU_TYPE_CD(2,"菜单"),

    FUNCTION_TYPE_API(2,"接口"),

    FUNCTION_TYPE_FUN(1,"方法"),

    MENU_ROLE_TYPE_SYSTEM(1,"系统角色"),

    MENU_ROLE_TYPE_BM(2,"部门角色"),

    ENUM_STATE_YES(1,"是"),

    ENUM_STATE_NO(0,"否"),

    ENUM_TYPE_USER_SYSADMIN(0,"系统管理员"),

    ENUM_TYPE_USER_ADMIN(1,"管理员"),

    ENUM_TYPE_USER_GUEST(2,"普通用户"),

    ENUM_ID_TYPE_STRING(1,"String"),

    ENUM_ID_TYPE_INTEGER(2,"Integer"),

    ENUM_ID_TYPE_lONG(3,"Long"),

    ENUM_ID_TYPE_TOKEN_TYPE_USER(1,"用户权限校验"),

    ENUM_ID_TYPE_TOKEN_TYPE_CLIENT(2,"客户端权限校验"),
  ;
    ConstantEnum(int num,String code){
        this.num=num;
        this.code=code;
    }
    private Integer num;
    private String code;

    public Integer getNum() {
        return num;
    }

    public String getCode() {
        return code;
    }
}
