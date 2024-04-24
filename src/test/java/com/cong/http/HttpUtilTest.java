package com.cong.http;

import com.cong.http.config.HttpConfig;
import com.cong.http.constants.Constants;
import com.cong.http.support.SwitchHttpResponse;
import junit.framework.TestCase;

import java.net.InetSocketAddress;
import java.net.Proxy;

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
                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890)))
                .build());
        SwitchHttpResponse response = HttpUtil.get("https://www.baidu.com");
        System.out.println("code = " + response.getCode());
        System.out.println("body = " + response.getBody());
        assertEquals(200, response.getCode());
    }
    public void testPost() {
        HttpUtil.setConfig(HttpConfig.builder()
                .timeout(Constants.DEFAULT_TIMEOUT)
                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890)))
                .build());
        SwitchHttpResponse response = HttpUtil.post("https://www.baidu.com");
        System.out.println("code = " + response.getCode());
        System.out.println("body = " + response.getBody());
        assertEquals(200, response.getCode());
    }
}