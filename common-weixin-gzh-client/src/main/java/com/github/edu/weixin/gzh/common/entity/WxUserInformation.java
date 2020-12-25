package com.github.edu.weixin.gzh.common.entity;

import lombok.Data;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-12-12
 */
@Data
public class WxUserInformation {

    private Integer errcode;

    private String errmsg;

    private String openid;

    private String nickname;//	用户昵称

    private Integer sex;//	用户的性别，值为1时是男性，值为2时是女性，值为0时是未知

    private String province;//	用户个人资料填写的省份

    private String city;//	普通用户个人资料填写的城市

    private String country;//	国家，如中国为CN

    private String headimgurl;//	用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），
    // 用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。


}
