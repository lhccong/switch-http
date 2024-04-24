package com.cong.http.support.java11;


import com.cong.http.config.HttpConfig;
import com.cong.http.constants.Constants;
import com.cong.http.support.AbstractHttp;
import com.cong.http.support.HttpHeader;
import com.cong.http.support.SwitchHttpResponse;
import com.cong.http.util.MapUtil;
import com.cong.http.util.StringUtil;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * java 11 HttpClient
 *
 * @author cong
 * @date 2024/04/24
 */
public class HttpClientImpl extends AbstractHttp {
	private final HttpClient.Builder clientBuilder;

	public HttpClientImpl() {
		this(new HttpConfig());
	}

	public HttpClientImpl(HttpConfig httpConfig) {
		this(HttpClient.newBuilder(), httpConfig);
	}

	public HttpClientImpl(HttpClient.Builder clientBuilder, HttpConfig httpConfig) {
		super(httpConfig);
		this.clientBuilder = clientBuilder;
	}

	private SwitchHttpResponse exec(HttpRequest.Builder builder) {
		this.addHeader(builder);
		try {
			HttpClient client;

			if (null != httpConfig.getProxy()) {
				client = clientBuilder.connectTimeout(Duration.ofMillis(httpConfig.getTimeout())).proxy(new DefaultProxySelector(httpConfig)).build();
			} else {
				client = clientBuilder.connectTimeout(Duration.ofMillis(httpConfig.getTimeout())).build();
			}
			HttpRequest request = builder.build();
			HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

			int code = httpResponse.statusCode();
			boolean successful = isSuccess(httpResponse);
			Map<String, List<String>> headers = httpResponse.headers().map();
			String body = httpResponse.body();
			return new SwitchHttpResponse(successful, code, headers, body, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new SwitchHttpResponse(false, 500, null, null, e.getMessage());
		}
	}

	private boolean isSuccess(HttpResponse<String> response) {
		if (response == null) {
			return false;
		}
		return response.statusCode() >= 200 && response.statusCode() < 300;
	}

	/**
	 * 添加request header
	 *
	 * @param builder HttpRequest.Builder
	 */
	private void addHeader(HttpRequest.Builder builder) {
		builder.header(Constants.USER_AGENT, Constants.USER_AGENT_DATA);
	}

	/**
	 * GET 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	@Override
	public SwitchHttpResponse get(String url) {
		return this.get(url, null, false);
	}

	/**
	 * GET 请求
	 *
	 * @param url    URL
	 * @param params 参数
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	@Override
	public SwitchHttpResponse get(String url, Map<String, String> params, boolean encode) {
		return this.get(url, params, null, encode);
	}

	/**
	 * GET 请求
	 *
	 * @param url    URL
	 * @param params 参数
	 * @param header 请求头
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	@Override
	public SwitchHttpResponse get(String url, Map<String, String> params, HttpHeader header, boolean encode) {
		String baseUrl = StringUtil.appendIfNotContain(url, "?", "&");
		String reqUrl = baseUrl + MapUtil.parseMapToString(params, encode);

		HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(reqUrl)).GET().timeout(Duration.ofMillis(httpConfig.getTimeout()));

		if (header != null) {
			MapUtil.forEach(header.getHeaders(), builder::header);
		}

		return exec(builder);
	}

	/**
	 * POST 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	@Override
	public SwitchHttpResponse post(String url) {
		return this.post(url, null);
	}

	/**
	 * POST 请求
	 *
	 * @param url  URL
	 * @param data JSON 参数
	 * @return 结果
	 */
	@Override
	public SwitchHttpResponse post(String url, String data) {
		return this.post(url, data, null);
	}

	/**
	 * POST 请求
	 *
	 * @param url    URL
	 * @param data   JSON 参数
	 * @param header 请求头
	 * @return 结果
	 */
	@Override
	public SwitchHttpResponse post(String url, String data, HttpHeader header) {
        assert HttpRequest.newBuilder() != null;
        HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofMillis(httpConfig.getTimeout()));

		if (StringUtil.isNotEmpty(data)) {
			builder.POST(HttpRequest.BodyPublishers.ofString(data, Constants.DEFAULT_ENCODING));
			builder.header(Constants.CONTENT_ENCODING, Constants.DEFAULT_ENCODING.displayName());
			builder.header(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
		} else {
			builder.POST(HttpRequest.BodyPublishers.noBody());
		}

		if (header != null) {
			MapUtil.forEach(header.getHeaders(), builder::header);
		}

		return this.exec(builder);
	}

	/**
	 * POST 请求
	 *
	 * @param url    URL
	 * @param params form 参数
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	@Override
	public SwitchHttpResponse post(String url, Map<String, String> params, boolean encode) {
		return this.post(url, params, null, encode);
	}

	/**
	 * POST 请求
	 *
	 * @param url    URL
	 * @param params form 参数
	 * @param header 请求头
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	@Override
	public SwitchHttpResponse post(String url, Map<String, String> params, HttpHeader header, boolean encode) {
		String baseUrl = StringUtil.appendIfNotContain(url, "?", "&");
		String reqUrl = baseUrl + MapUtil.parseMapToString(params, encode);
		return this.post(reqUrl, null, header);
	}
}
