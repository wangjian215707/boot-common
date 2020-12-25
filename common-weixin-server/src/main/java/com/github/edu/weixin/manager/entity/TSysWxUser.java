package com.github.edu.weixin.manager.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "T_SYS_WX_USER")
public class TSysWxUser implements Serializable {
    @Id
    private String openid;

    private String image;

    private String userid;

    private String pwd;

    private String cookie;

    private String name;
}
