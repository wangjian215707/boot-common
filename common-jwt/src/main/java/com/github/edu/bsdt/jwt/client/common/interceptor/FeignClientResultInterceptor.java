package com.github.edu.bsdt.jwt.client.common.interceptor;

import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.edu.bsdt.jwt.client.common.component.ClientTokenComponent;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/16
 * Time: 0:01
 */
@Slf4j
public class FeignClientResultInterceptor implements RequestInterceptor {

    /**
     * 自定义头 名称
     */
    @Value("${server.custom.token.header.token-header}")
    private String tokenHeader;//token

    @Value("${server.custom.token.header.client-id}")
    private String clientId;//key

    @Value("${server.custom.token.jwt.client-key}")
    private String clientKey;

    @Autowired
    private ClientTokenComponent clientTokenComponent;

    @Override
    public void apply(RequestTemplate template) {
        try {
            String code = clientTokenComponent.getToken();
            if (StringUtils.isNotBlank(code)) {
                template.header(tokenHeader, code);
                template.header(clientId, clientKey);
            }
        } catch (Exception e) {
            log.error(" 设置请求头信息失败！：" + e.getMessage());
        }
    }
}
