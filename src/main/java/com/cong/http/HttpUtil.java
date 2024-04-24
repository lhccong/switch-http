package com.cong.http;

import com.cong.http.config.HttpConfig;
import com.cong.http.constants.Constants;
import com.cong.http.exception.SwitchHttpException;
import com.cong.http.support.AbstractHttp;
import com.cong.http.support.Http;
import com.cong.http.support.HttpHeader;
import com.cong.http.support.SwitchHttpResponse;
import com.cong.http.util.ClassUtil;
import lombok.experimental.UtilityClass;

import java.util.Map;

/**
 * <p>
 * 请求工具类
 * </p>
 *
 * @author cong
 * @date 2024/04/23
 */
@UtilityClass
public class HttpUtil {
	private static AbstractHttp proxy;

	private void selectHttpProxy() {
		AbstractHttp defaultProxy = null;
		ClassLoader classLoader = HttpUtil.class.getClassLoader();
		// 基于 java 11 HttpClient
		if (ClassUtil.isPresent("java.net.http.HttpClient", classLoader)) {
			defaultProxy = getHttpProxy(com.cong.http.support.java11.HttpClientImpl.class);
		}
		// 基于 okhttp3
		if (null == defaultProxy && ClassUtil.isPresent("okhttp3.OkHttpClient", classLoader)) {
			defaultProxy = getHttpProxy(com.cong.http.support.okhttp3.OkHttp3Impl.class);
		}

		if (defaultProxy == null) {
			throw new SwitchHttpException("Has no HttpImpl defined in environment!");
		}

		proxy = defaultProxy;
	}

	private static <T extends AbstractHttp> AbstractHttp getHttpProxy(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (Throwable e) {
			return null;
		}
	}

	public void setHttp(AbstractHttp http) {
		proxy = http;
	}

	private void checkHttpNotNull(Http proxy) {
		if (null == proxy) {
			selectHttpProxy();
		}
	}

	public void setConfig(HttpConfig httpConfig) {
		checkHttpNotNull(proxy);
		if (null == httpConfig) {
			httpConfig = HttpConfig.builder().timeout(Constants.DEFAULT_TIMEOUT).build();
		}
		proxy.setHttpConfig(httpConfig);
	}

	/**
	 * GET 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	public SwitchHttpResponse get(String url) {
		checkHttpNotNull(proxy);
		return proxy.get(url);
	}

	/**
	 * GET 请求
	 *
	 * @param url    URL
	 * @param params 参数
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	public SwitchHttpResponse get(String url, Map<String, String> params, boolean encode) {
		checkHttpNotNull(proxy);
		return proxy.get(url, params, encode);
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
	public SwitchHttpResponse get(String url, Map<String, String> params, HttpHeader header, boolean encode) {
		checkHttpNotNull(proxy);
		return proxy.get(url, params, header, encode);
	}

	/**
	 * POST 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	public SwitchHttpResponse post(String url) {
		checkHttpNotNull(proxy);
		return proxy.post(url);
	}

	/**
	 * POST 请求
	 *
	 * @param url  URL
	 * @param data JSON 参数
	 * @return 结果
	 */
	public SwitchHttpResponse post(String url, String data) {
		checkHttpNotNull(proxy);
		return proxy.post(url, data);
	}

	/**
	 * POST 请求
	 *
	 * @param url    URL
	 * @param data   JSON 参数
	 * @param header 请求头
	 * @return 结果
	 */
	public SwitchHttpResponse post(String url, String data, HttpHeader header) {
		checkHttpNotNull(proxy);
		return proxy.post(url, data, header);
	}

	/**
	 * POST 请求
	 *
	 * @param url    URL
	 * @param params form 参数
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	public SwitchHttpResponse post(String url, Map<String, String> params, boolean encode) {
		checkHttpNotNull(proxy);
		return proxy.post(url, params, encode);
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
	public SwitchHttpResponse post(String url, Map<String, String> params, HttpHeader header, boolean encode) {
		checkHttpNotNull(proxy);
		return proxy.post(url, params, header, encode);
	}
}
