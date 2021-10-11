package yyc.open.component.report.entity;

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
	 * File path name: supports png. JPG, PDF, etc
	 */
	private String file;
	/**
	 * Request parameter: input to option when echarts is used
	 */
	private Map<String, Object> opt;

	public PhantomJS(String reqMethod,
			String file,
			Map<String, Object> opt) {
		this.reqMethod = reqMethod;
		this.file = file;
		this.opt = opt;
	}

	public static PhantomJSBuilder builder() {
		return new PhantomJSBuilder();
	}

	public static class PhantomJSBuilder {

		private String reqMethod;
		private String file;
		private Map<String, Object> opt;

		public PhantomJSBuilder reqMethod(String reqMethod) {
			this.reqMethod = reqMethod;
			return this;
		}

		public PhantomJSBuilder file(String file) {
			this.file = file;
			return this;
		}

		public PhantomJSBuilder opt(Map<String, Object> opt) {
			this.opt = opt;
			return this;
		}

		public PhantomJS build() {
			return new PhantomJS(this.reqMethod, this.file, this.opt);
		}
	}
}
