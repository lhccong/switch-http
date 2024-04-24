package com.cong.http.util;

import lombok.experimental.UtilityClass;

/**
 * <p>
 * 类工具类
 * </p>
 *
 * @author cong
 * @date 2024/04/24
 */
@UtilityClass
public class ClassUtil {

	/**
	 * 确定class是否可以被加载
	 *
	 * @param className   完整类名
	 * @param classLoader 类加载
	 * @return {boolean}
	 */
	public boolean isPresent(String className, ClassLoader classLoader) {
		try {
			Class.forName(className, true, classLoader);
			return true;
		} catch (Throwable ex) {
			return false;
		}
	}

}
