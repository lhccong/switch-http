package com.cong.http.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 切换 HTTP 响应
 *
 * @author cong
 * @date 2024/04/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwitchHttpResponse {

    /**
     * 状态码
     */
    private int code;
    /**
     * 成功
     */
    private boolean success;
    /**
     * 头
     */
    private Map<String, List<String>> headers;
    /**
     * 身体
     */
    private String body;
    /**
     * 错误
     */
    private String error;
}
