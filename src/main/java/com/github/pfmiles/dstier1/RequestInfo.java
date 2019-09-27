/**
 * 
 */
package com.github.pfmiles.dstier1;

import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;

/**
 * A read-only request info.
 * 
 * @author pf-miles
 *
 */
public class RequestInfo {

	private HttpMethod method;
	private String uri;
	private HeaderInfo headers;

	public RequestInfo(HttpRequest request) {
		this.method = request.getMethod();
		this.uri = request.getUri();
		this.headers = new HeaderInfo(request.headers());
	}

	public HttpMethod getMethod() {
		return method;
	}

	public String getUri() {
		return uri;
	}

	public HeaderInfo getHeaders() {
		return headers;
	}

}
