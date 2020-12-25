package com.github.edu.boot2.admin.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 保存用户token信息
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/21
 */
@Data
@Entity
@Table(name = "t_sys_user_token")
public class TSysUserToken implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "TableGenerator")
    @TableGenerator(name = "TableGenerator",table = "t_sequence",
            pkColumnName = "name",
            valueColumnName = "id",
            pkColumnValue = "TSysUserToken")
    private Long id;

    private String userId;

    private Long stateTime;

    private Long endTime;

    private String token;

    private String clientId;
}
