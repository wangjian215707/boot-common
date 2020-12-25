package com.github.edu.boot2.admin.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/23
 */
@Data
@Entity
@Table(name = "t_sys_online_user")
public class TSysOnlineUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "TableGenerator")
    @TableGenerator(name = "TableGenerator",table = "t_sequence",
            pkColumnName = "name",
            valueColumnName = "id",
            pkColumnValue = "TSysOnlineUser")
    private Long id;

    private String userId;

    private String token;

    private String ip;

    private String address;

    private String browser;//浏览器

    private String loginTime;//登录时间
}
