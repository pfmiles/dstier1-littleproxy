/**
 * 
 */
package com.github.pfmiles.dstier1;

import java.util.Collection;

/**
 * Builds filters(T1Filter) to be used to process reqs/rsps. Filters will be
 * created in a per-request manner. So they are thread-safe by natural.
 * 
 * @author pf-miles
 *
 */
public interface FiltersFactory {
	/**
	 * Builds new filter instances. This method is invoked at every time a brand new
	 * request is arrived. Note that an arrival of a chunked content does not cause
	 * the invocation of this method, only initial requests does. So generally
	 * speaking, I may create new filter instances at the arrival of a initial
	 * request and continue using it statefully during the following progress of a
	 * chunked transference.
	 * 
	 * @param reqInfo
	 *            read-only request info
	 * @return new filter instances, thay will be used in a thread-safe-per-request
	 *         env, and can keep states.
	 */
	Collection<T1Filter> buildFilters(RequestInfo reqInfo);
}
