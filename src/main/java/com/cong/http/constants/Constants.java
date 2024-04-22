package com.cong.http.constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * 常量池
 * </p>
 *
 * @author cong
 * @date 2024/04/22
 */
public interface Constants {
	/**
	 * 超时时长，单位毫秒
	 */
	int DEFAULT_TIMEOUT = 3000;

	/**
	 * 编码格式
	 */
	Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;

	/**
	 * JSON
	 */
	String CONTENT_TYPE_JSON = "application/json; charset=utf-8";

	/**
	 * Content-Type
	 */
	String CONTENT_TYPE = "Content-Type";

	/**
	 * Content-Encoding
	 */
	String CONTENT_ENCODING = "Content-Encoding";

	/**
	 * User-Agent
	 */
	String USER_AGENT = "User-Agent";

	/**
	 * 模拟 User-Agent
	 */
	String USER_AGENT_DATA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36 simple-http";

	/**
	 * 空字符串
	 */
	String EMPTY = "";

}
