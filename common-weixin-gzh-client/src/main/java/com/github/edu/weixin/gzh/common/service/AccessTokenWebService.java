package com.github.edu.weixin.gzh.common.service;

import com.github.edu.weixin.gzh.common.entity.AccessTokenEntity;

public interface AccessTokenWebService {

    AccessTokenEntity getAccessToken(String code);
}
