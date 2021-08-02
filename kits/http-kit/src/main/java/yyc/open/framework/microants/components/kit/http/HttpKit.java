package yyc.open.framework.microants.components.kit.http;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * {@link HttpKit}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
public class HttpKit {
    private static final Logger logger = LoggerFactory.getLogger(HttpKit.class);

    private final HttpUriRequest request;
    private final CloseableHttpClient client;

    protected HttpKit(HttpUriRequest request, CloseableHttpClient client) {
        this.request = request;
        this.client = client;
    }

    public static HttpKitBuilder builder() {
        return new HttpKitBuilder();
    }

    public Result get() {
        Result.ResultBuilder builder = Result.builder();
        CloseableHttpResponse response = null;
        try {
            response = client.execute(request);
            builder.code(response.getStatusLine().getStatusCode());
            builder.msg(response.getEntity());
        } catch (IOException e) {
            logger.error("[Http Kit] execute encountered error:{}", e);
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.build();
    }
}
