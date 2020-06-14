package io.github.ericwu0930.configure;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 * @author erichwu
 * @date 2020/6/13
 */
// 当Spring容器中没有TomcatEmbeddedServletContainerFactory这个bean时，
// 会把此bean加载进spring容器内
@Component
public class WebServerConfiguration implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        ((TomcatServletWebServerFactory)factory).addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                Http11NioProtocol protocolHandler = (Http11NioProtocol) connector.getProtocolHandler();
                // 定制化keepalivetimeout,设置30秒内没有请求者服务器自动断开keepalive连接
                protocolHandler.setKeepAliveTimeout(3000);
                // 发送请求超过10000个自动断开keepalive连接
                protocolHandler.setMaxKeepAliveRequests(10000);
            }
        });
    }
}
