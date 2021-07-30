package yyc.open.framework.microants.components.kit.http;

/**
 * {@link HttpMethod}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
public enum HttpMethod {
    POST("POST"),
    PUT("PUT"),
    GET("GET"),
    DELETE("DELETE"),
    HEAD("HEAD"),
    PATCH("PATCH"),
    TRACE("TRACE"),
    OPTIONS("OPTIONS");

    String name;

    HttpMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
