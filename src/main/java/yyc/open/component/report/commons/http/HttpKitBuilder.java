package yyc.open.component.report.commons.http;

import com.google.gson.GsonBuilder;
import java.util.Map;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * {@link HttpKitBuilder}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
public class HttpKitBuilder {

	private CloseableHttpClient client;
	private String url;
	private HttpMethod httpMethod;
	private String body;

	public HttpKitBuilder post() {
		this.httpMethod = HttpMethod.POST;
		return this;
	}

	public HttpKitBuilder post(String url) {
		this.url = url;
		this.post();
		return this;
	}

	public HttpKitBuilder get() {
		this.httpMethod = HttpMethod.GET;
		return this;
	}

	public HttpKitBuilder body(Map<String, Object> body) {
		this.body = new GsonBuilder().create().toJson(body);
		return this;
	}

	public HttpKitBuilder body(String body) {
		this.body = body;
		return this;
	}

	public HttpKit build() {
		client = HttpClients.createDefault();

		RequestBuilder requestBuilder = RequestBuilder
				.create(this.httpMethod.getName())
				.setUri(this.url);

		if (!this.body.isEmpty()) {
			requestBuilder.setEntity(new StringEntity(this.body, ContentType.APPLICATION_JSON));
		}

		return new HttpKit(requestBuilder.build(), client);
	}
}
