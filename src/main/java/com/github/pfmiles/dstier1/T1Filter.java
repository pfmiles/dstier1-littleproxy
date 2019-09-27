/**
 * 
 */
package com.github.pfmiles.dstier1;

import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;

/**
 * The filter components interface for request & response filtering. T1 filters
 * could examine or modify request and response data pass through. Not all
 * filter implementations are activated by default, it depends on the result of
 * method 'active'.
 * <p />
 * T1 filter instances are created in a per-connection manner, so it's
 * thread-safe by default and could keep internal states during its activation.
 * 
 * @author pf-miles
 *
 */
public interface T1Filter {
	/**
	 * Tell if this filter should be activated upon a particular incoming request.
	 * 
	 * @param req
	 *            information about the incoming request
	 * @return true if active on this request, false otherwise.
	 */
	boolean active(RequestInfo req);

	/**
	 * Filtering requests from client to server. Return null if you want the
	 * requesting progress to continue, or a hand-crafted HttpResponse object to
	 * short-circuit the process. The HttpObjectAggregator.decode method could be a
	 * reference to this method's implementation.
	 * 
	 * @param httpObj
	 *            the HttpRequest object to be filtered, or HttpContent object
	 *            during a chunked encoding request transmission.
	 * @return null to continue processing as usual, or a short-circuit response
	 */
	HttpResponse onRequesting(HttpObject httpObj);

	/**
	 * Filtering responses from server to client, returning null will force a
	 * disconnect. The HttpObjectAggregator.decode method could be a reference to
	 * this method's implementation.
	 * 
	 * @param httpObj
	 *            the response(HttpResponse) or chunk(HttpContent) object to be
	 *            filtered.
	 * @return a modified or unchanged response object(could either be a
	 *         HttpResponse or a HttpContent), null will force a disconnect at once.
	 */
	HttpObject onResponding(HttpObject httpObj);
}
