package com.github.edu.security.login.company.entity;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/3
 * Time: 21:18
 */
@Data
public class UserInformation {

    private Integer errcode;

    private String errmsg;

    private String userid;

    private String name;

   /* private String mobile;*/

    private String gender;

    /*private String email;*/

    private String avatar;

    /*private String qr_code;*/

}
