package com.cong.http.support;

import com.cong.http.config.HttpConfig;
import lombok.Setter;

/**
 * 摘要 HTTP
 *
 * @author cong
 * @date 2024/04/24
 */
@Setter
public abstract class AbstractHttp implements Http {
    protected HttpConfig httpConfig;

    protected AbstractHttp(HttpConfig httpConfig) {
        this.httpConfig = httpConfig;
    }

}
