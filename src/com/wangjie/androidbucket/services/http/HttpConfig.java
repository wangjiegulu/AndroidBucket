package com.wangjie.androidbucket.services.http;

/**
 * @author Hubert He
 * @version V1.0
 * @Description Http配置 默认HTTP端口80，HTTPS端口9000，SO超时时间20秒，连接超时时间20秒
 * @Createdate 14-9-4 15:14
 */
public class HttpConfig {

    // HTTP Port
    private int httpPort;

    // HTTPS Port
    private int httpsPort;

    // So超时时间
    private int soTimeout;

    // 连接超时时间
    private int connectionTimeout;

    // Domain
    private String domain;

    /**
     * 默认构造函数
     */
    public HttpConfig() {
        this("", 80, 9000, 20000, 20000);
    }

    public HttpConfig(String domain, int httpPort, int httpsPort, int soTimeout, int connectionTimeout) {
        this.domain = domain;
        this.httpPort = httpPort;
        this.httpsPort = httpsPort;
        this.soTimeout = soTimeout;
        this.connectionTimeout = connectionTimeout;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public int getHttpsPort() {
        return httpsPort;
    }

    public void setHttpsPort(int httpsPort) {
        this.httpsPort = httpsPort;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
