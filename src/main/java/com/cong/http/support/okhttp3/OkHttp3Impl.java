package com.cong.http.support.okhttp3;

import com.cong.http.config.HttpConfig;
import com.cong.http.constants.Constants;
import com.cong.http.support.AbstractHttp;
import com.cong.http.support.HttpHeader;
import com.cong.http.support.SwitchHttpResponse;
import com.cong.http.util.MapUtil;
import com.cong.http.util.StringUtil;
import okhttp3.*;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * okhttp3 impl
 *
 * @author cong
 * @date 2024/04/24
 */
public class OkHttp3Impl extends AbstractHttp {

    public static final MediaType CONTENT_TYPE_JSON = MediaType.get(Constants.CONTENT_TYPE_JSON);

    private final OkHttpClient.Builder httpClientBuilder;

    public OkHttp3Impl() {
        this(new HttpConfig());
    }

    public OkHttp3Impl(OkHttpClient.Builder httpClientBuilder, HttpConfig httpConfig) {
        super(httpConfig);
        this.httpClientBuilder = httpClientBuilder;
    }

    protected OkHttp3Impl(HttpConfig httpConfig) {
        this(new OkHttpClient().newBuilder(), httpConfig);
    }

    private SwitchHttpResponse exec(Request.Builder requestBuilder) {
        // 构建请求并添加请求头
        this.addHeader(requestBuilder);
        Request request = requestBuilder.build();

        OkHttpClient httpClient;
        // 根据http配置设置代理或创建无代理的OkHttpClient实例
        if (null != httpConfig.getProxy()) {
            httpClient = httpClientBuilder.connectTimeout(Duration.ofMillis(httpConfig.getTimeout())).writeTimeout(Duration.ofMillis(httpConfig.getTimeout())).readTimeout(Duration.ofMillis(httpConfig.getTimeout())).proxy(httpConfig.getProxy()).build();
        } else {
            httpClient = httpClientBuilder.connectTimeout(Duration.ofMillis(httpConfig.getTimeout())).writeTimeout(Duration.ofMillis(httpConfig.getTimeout())).readTimeout(Duration.ofMillis(httpConfig.getTimeout())).build();
        }
        try (Response response = httpClient.newCall(request).execute()) {
            int code = response.code();
            boolean successful = response.isSuccessful();
            Map<String, List<String>> headers = response.headers().toMultimap();

            ResponseBody responseBody = response.body();

            // 提取响应体内容
            String body = null == responseBody ? null : responseBody.string();
            // 构建并返回响应实例
            return new SwitchHttpResponse(successful, code, headers, body, null);
        } catch (IOException e) {
            e.printStackTrace();
            // 在出现IO异常时，返回一个错误响应实例
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

    /**
     * 添加request header
     *
     * @param builder Request.Builder
     */
    private void addHeader(Request.Builder builder) {
        builder.header(Constants.USER_AGENT, Constants.USER_AGENT_DATA);
    }

    @Override
    public SwitchHttpResponse get(String url, Map<String, String> params, HttpHeader header, boolean encode) {
        // 解析并初始化URL构建器
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();

        // 根据encode参数，选择添加编码后的或未编码的查询参数
        if (encode) {
            MapUtil.forEach(params, urlBuilder::addEncodedQueryParameter);
        } else {
            MapUtil.forEach(params, urlBuilder::addQueryParameter);
        }
        // 构建最终的HTTP URL
        HttpUrl httpUrl = urlBuilder.build();

        // 初始化请求构建器，并设置URL
        Request.Builder requestBuilder = new Request.Builder().url(httpUrl);

        // 如果提供了头部信息，添加到请求构建器中
        if (header != null) {
            MapUtil.forEach(header.getHeaders(), requestBuilder::addHeader);
        }

        // 将请求设置为GET方式
        requestBuilder = requestBuilder.get();

        // 执行请求并返回结果
        return exec(requestBuilder);

    }

    @Override
    public SwitchHttpResponse post(String url) {
        return this.post(url, Constants.EMPTY);
    }

    @Override
    public SwitchHttpResponse post(String url, String data) {
        return this.post(url, data, null);
    }

    @Override
    public SwitchHttpResponse post(String url, String data, HttpHeader header) {
        if (StringUtil.isEmpty(data)) {
            data = Constants.EMPTY;
        }
        RequestBody body = RequestBody.create(data, CONTENT_TYPE_JSON);

        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (header != null) {
            MapUtil.forEach(header.getHeaders(), requestBuilder::addHeader);
        }
        requestBuilder = requestBuilder.post(body);

        return exec(requestBuilder);
    }

    @Override
    public SwitchHttpResponse post(String url, Map<String, String> params, boolean encode) {
        return this.post(url, params, null, encode);
    }

    @Override
    public SwitchHttpResponse post(String url, Map<String, String> params, HttpHeader header, boolean encode) {
        // 根据是否编码，构建请求的FormBody
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (encode) {
            MapUtil.forEach(params, formBuilder::addEncoded);
        } else {
            MapUtil.forEach(params, formBuilder::add);
        }
        FormBody body = formBuilder.build();

        // 构建请求的Request，包括设置URL和头部信息
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (header != null) {
            MapUtil.forEach(header.getHeaders(), requestBuilder::addHeader);
        }
        requestBuilder = requestBuilder.post(body);

        // 执行请求并返回响应
        return exec(requestBuilder);
    }

}
