package com.cong.http;

import com.cong.http.config.HttpConfig;
import com.cong.http.constants.Constants;
import com.cong.http.support.SwitchHttpResponse;
import junit.framework.TestCase;

/**
 * HTTP util 测试
 *
 * @author cong
 * @date 2024/04/24
 */
public class HttpUtilTest extends TestCase {
    public void testGet() {
        HttpUtil.setConfig(HttpConfig.builder()
                .timeout(Constants.DEFAULT_TIMEOUT)
                .build());
        SwitchHttpResponse response = HttpUtil.get("https://www.baidu.com");
        System.out.println("code = " + response.getCode());
        System.out.println("body = " + response.getBody());
        assertEquals(200, response.getCode());
    }
}