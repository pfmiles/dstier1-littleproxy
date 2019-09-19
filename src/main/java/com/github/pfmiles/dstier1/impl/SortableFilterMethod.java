/**
 * 
 */
package com.github.pfmiles.dstier1.impl;

import java.lang.reflect.Method;

import com.github.pfmiles.dstier1.T1Filter;

/**
 * Filter method encapsulation which can be sorted by execution order.
 * 
 * @author pf-miles
 *
 */
public class SortableFilterMethod implements Comparable<SortableFilterMethod> {
	private T1Filter filter;
	private Method method;
	private int priority;

	public SortableFilterMethod(T1Filter filter, Method method, int priority) {
		this.filter = filter;
		this.method = method;
		this.priority = priority;

	}

	@Override
	public int compareTo(SortableFilterMethod o) {
		return this.priority - o.priority;
	}

	public T1Filter getFilter() {
		return filter;
	}

	public void setFilter(T1Filter filter) {
		this.filter = filter;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

}
