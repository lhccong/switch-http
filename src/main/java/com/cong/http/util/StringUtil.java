package com.cong.http.util;

import lombok.experimental.UtilityClass;

/**
 * <p>
 * 字符串工具类
 * </p>
 *
 * @author cong
 * @date 2024/04/24
 */
@UtilityClass
public class StringUtil {

	public boolean isEmpty(String str) {
		return null == str || str.trim().isEmpty();
	}

	public boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 如果给定字符串{@code str}中不包含{@code appendStr}，则在{@code str}后追加{@code appendStr}；
	 * 如果已包含{@code appendStr}，则在{@code str}后追加{@code otherwise}
	 *
	 * @param str       给定的字符串
	 * @param appendStr 需要追加的内容
	 * @param otherwise 当{@code appendStr}不满足时追加到{@code str}后的内容
	 * @return 追加后的字符串
	 */
	public String appendIfNotContain(String str, String appendStr, String otherwise) {
		if (isEmpty(str) || isEmpty(appendStr)) {
			return str;
		}
		if (str.contains(appendStr)) {
			return str.concat(otherwise);
		}
		return str.concat(appendStr);
	}

}
