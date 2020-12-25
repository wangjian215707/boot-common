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
@Table(name = "t_sys_image_code")
public class TSysImageCode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "TableGenerator")
    @TableGenerator(name = "TableGenerator",table = "t_sequence",
            pkColumnName = "name",
            valueColumnName = "id",
            pkColumnValue = "TSysImageCode")
    private Long id;

    private String uuid;

    private String text;

}
