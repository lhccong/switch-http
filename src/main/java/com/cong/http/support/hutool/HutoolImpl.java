package com.cong.http.support.hutool;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.cong.http.config.HttpConfig;
import com.cong.http.support.AbstractHttp;
import com.cong.http.support.HttpHeader;
import com.cong.http.support.SwitchHttpResponse;
import com.cong.http.util.MapUtil;
import com.cong.http.util.StringUtil;
import com.cong.http.util.UrlUtil;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Hutool 实现
 * </p>
 *
 * @author cong
 * @date 2024/04/25
 */
public class HutoolImpl extends AbstractHttp {
    public HutoolImpl() {
        this(new HttpConfig());
    }

    protected HutoolImpl(HttpConfig httpConfig) {
        super(httpConfig);
    }

    /**
     * 执行
     *
     * @param request 请求
     * @return {@link SwitchHttpResponse}
     */
    private SwitchHttpResponse exec(HttpRequest request) {
        // 设置超时时长
        request = request.timeout(httpConfig.getTimeout());
        // 设置代理
        if (null != httpConfig.getProxy()) {
            request = request.setProxy(httpConfig.getProxy());
        }
        try (HttpResponse response = request.execute()) {
            int code = response.getStatus();
            boolean successful = response.isOk();
            String body = response.body();
            Map<String, List<String>> headers = response.headers();
            return new SwitchHttpResponse(successful, code, headers, body, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new SwitchHttpResponse(false, 500, null, null, e.getMessage());
        }
    }

    @Override
    public SwitchHttpResponse get(String url) {
        return this.get(url, null, false);
    }

    @Override
    public SwitchHttpResponse get(String url, Map<String, String> params, boolean encode) {
        return this.get(url, params, null, encode);
    }

    @Override
    public SwitchHttpResponse get(String url, Map<String, String> params, HttpHeader header, boolean encode) {
        String baseUrl = StringUtil.appendIfNotContain(url, "?", "&");
        url = baseUrl + MapUtil.parseMapToString(params, encode);

        HttpRequest request = HttpRequest.get(url);

        if (header != null) {
            MapUtil.forEach(header.getHeaders(), request::header);
        }

        return exec(request);
    }

    @Override
    public SwitchHttpResponse post(String url) {
        HttpRequest request = HttpRequest.post(url);
        return this.exec(request);
    }

    @Override
    public SwitchHttpResponse post(String url, String data) {
        return this.post(url, data, null);
    }

    /**
     * 发布
     *
     * @param url    网址
     * @param data   数据
     * @param header 页眉
     * @return {@link SwitchHttpResponse}
     */
    @Override
    public SwitchHttpResponse post(String url, String data, HttpHeader header) {
        HttpRequest request = HttpRequest.post(url);

        if (StringUtil.isNotEmpty(data)) {
            request.body(data);
        }

        if (header != null) {
            MapUtil.forEach(header.getHeaders(), request::header);
        }
        return this.exec(request);
    }

    /**
     * 发布
     *
     * @param url    网址
     * @param params 参数
     * @param encode 编码
     * @return {@link SwitchHttpResponse}
     */
    @Override
    public SwitchHttpResponse post(String url, Map<String, String> params, boolean encode) {
        return this.post(url, params, null, encode);
    }

    /**
     * 发布
     *
     * @param url    网址
     * @param params 参数
     * @param header 页眉
     * @param encode 编码
     * @return {@link SwitchHttpResponse}
     */
    @Override
    public SwitchHttpResponse post(String url, Map<String, String> params, HttpHeader header, boolean encode) {
        HttpRequest request = HttpRequest.post(url);

        if (encode) {
            MapUtil.forEach(params, (k, v) -> request.form(k, UrlUtil.urlEncode(v)));
        } else {
            MapUtil.forEach(params, request::form);
        }

        if (header != null) {
            MapUtil.forEach(header.getHeaders(), request::header);
        }
        return this.exec(request);
    }
}
