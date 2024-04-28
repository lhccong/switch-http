<h1 align="center"><a href="https://github.com/lhccong/switch-http" target="_blank">Switch-HTTP</a></h1>
<p align="center">
  <a href="https://www.oracle.com/technetwork/java/javase/downloads/index.html" target="_blank"><img alt="JDK" src="https://img.shields.io/badge/JDK-1.8.0_162-orange.svg"/></a>
</p>

## 简介

> 抽取一个简单 HTTP 的通用接口，底层实现根据具体引入依赖指定。

```xml
<dependency>
  <groupId>com.cong.http</groupId>
  <artifactId>switch-http</artifactId>
  <version>1.0.0</version>
</dependency>
```

## 特点

- 默认会按照下面的优先级自行寻找底层实现，`java 11 HttpClient -> OkHttp3 -> apache HttpClient -> hutool-http`
- 也可以自行实现 `com.cong.http.support.Http` 接口，通过 `HttpUtil.setHttp(new MyHttpImpl())` 设置进来
- 可以配置超时时间及代理
```java
HttpUtil.setConfig(HttpConfig.builder()
			.timeout(Constants.DEFAULT_TIMEOUT)
			.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 10080)))
			.build());
SimpleHttpResponse response = HttpUtil.get("https://www.google.com");
System.out.println("code = " + response.getCode());
System.out.println("body = " + response.getBody());
```

## TODO

