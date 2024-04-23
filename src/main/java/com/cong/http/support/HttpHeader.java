package com.cong.http.support;

import lombok.Getter;

import java.util.Map;

/**
 * HTTP 标头
 *
 * @author cong
 * @date 2024/04/23
 */
@Getter
public class HttpHeader {
    private final Map<String, String> headers;

    public HttpHeader(Map<String, String> headers) {
        this.headers = headers;
    }
    public HttpHeader add(String key, String value){
        this.headers.put(key, value);
        return this;
    }
    public HttpHeader addAll(Map<String, String> headers){
        this.headers.putAll(headers);
        return this;
    }
}
