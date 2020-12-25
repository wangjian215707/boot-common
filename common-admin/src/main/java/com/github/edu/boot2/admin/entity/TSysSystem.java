package com.github.edu.boot2.admin.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 接入系统管理
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/16
 */
@Entity
@Table(name = "t_sys_system")
@Data
public class TSysSystem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "TableGenerator")
    @TableGenerator(name = "TableGenerator",table = "t_sequence",
            pkColumnName = "name",
            valueColumnName = "id",
            pkColumnValue = "TSysSystem")
    private Long id;

    private String name;//系统名称

    private String url;//系统地址

    private String secret;//密钥

    private String code;//系统编号

    private Integer state;//当前状态

    private Integer yzfs;//验证方式：1，用户权限校验；2、客户端权限校验

    private String pubKey;//公钥

    private String priKey;//私钥
}
