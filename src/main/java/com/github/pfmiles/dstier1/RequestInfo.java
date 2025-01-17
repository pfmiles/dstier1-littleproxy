/*******************************************************************************
 * Copyright 2019 pf-miles
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
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
