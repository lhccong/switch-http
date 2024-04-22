/*
 * Copyright (c) 2019-2029, xkcoding & Yangkai.Shen & 沈扬凯 (237497819@qq.com & xkcoding.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cong.http.exception;

/**
 * <p>
 * 自定义异常
 * </p>
 *
 * @author cong
 * @date 2024/04/22
 */
public class SwitchHttpException extends RuntimeException {
	public SwitchHttpException(Throwable cause) {
		super(cause);
	}

	public SwitchHttpException(String message) {
		super(message);
	}

	public SwitchHttpException(String message, Throwable cause) {
		super(message, cause);
	}
}
