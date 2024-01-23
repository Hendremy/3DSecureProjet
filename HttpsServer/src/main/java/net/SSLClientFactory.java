package net;

import javax.net.ssl.SSLContext;

public class SSLClientFactory {
    SSLContext sslContext;

    public SSLClientFactory(SSLContext sslContext) {
        this.sslContext = sslContext;
    }

    public SSLClient create() throws SSLClientException {
        return new SSLClient("127.0.0.1", 8888, this.sslContext);
    }
}
