package yyc.open.framework.microants.components.kit.report.entity;

import yyc.open.framework.microants.components.kit.common.validate.NonNull;

import java.util.Map;

/**
 * {@link PhantomJS}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/1
 */
public class PhantomJS {
    /**
     * Request method: Supports table and echarts
     */
    private String reqMethod;
    /**
     * Requested Address: Address requested when table
     */
    private String url;
    /**
     * File path name: supports png. JPG, PDF, etc
     */
    private String file;
    /**
     * Request parameter: input to option when echarts is used
     */
    private Map<String, Object> opt;
    /**
     * Whether to disable PhantomJS True: Not to disable PhantomJS False: disable PhantomJS
     */
    private boolean close = true;

    public static PhantomJSBuilder builder() {
        return new PhantomJSBuilder();
    }

    public static class PhantomJSBuilder {
        private String reqMethod;
        private String url;
        private String file;
        private Map<String, Object> opt;
        private boolean close = true;

        public PhantomJSBuilder reqMethod(@NonNull String reqMethod) {
            this.reqMethod = reqMethod;
            return this;
        }

        public PhantomJSBuilder url(@NonNull String url) {
            this.url = url;
            return this;
        }

        public PhantomJSBuilder file(@NonNull String file) {
            this.file = file;
            return this;
        }

        public PhantomJSBuilder opt(@NonNull Map<String, Object> opt) {
            this.opt = opt;
            return this;
        }

        public PhantomJSBuilder close(@NonNull boolean closed) {
            this.close = closed;
            return this;
        }

        public PhantomJS build() {
            return new PhantomJS();
        }
    }
}
