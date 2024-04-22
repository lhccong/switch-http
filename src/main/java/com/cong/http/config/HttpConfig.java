package com.cong.http.config;

import com.cong.http.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.Proxy;

/**
 * HTTP 配置
 *
 * @author cong
 * @date 2024/04/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpConfig {
    /**
     * 超时时长，单位毫秒
     */
    private int timeout = Constants.DEFAULT_TIMEOUT;
    /**
     * 代理配置
     */
    private Proxy proxy;
}
