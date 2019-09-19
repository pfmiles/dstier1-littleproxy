/**
 * 
 */
package com.github.pfmiles.dstier1.impl;

import java.util.Date;

/**
 * A utility helper class to cooperate with google guava cache to implement
 * initiative expire control.
 * 
 * @author pf-miles
 *
 */
public class TimedCacheItem<T> {

	// the cached item
	private T item;
	// time to expire
	private Date expireOn;

	public TimedCacheItem(T item, Date expireOn) {
		this.item = item;
		this.expireOn = expireOn;
	}

	public TimedCacheItem(T item) {
		this.item = item;
		this.expireOn = new Date(Long.MAX_VALUE);// never expire on it's own initiative
	}

	public T getItem() {
		return item;
	}

	public void setItem(T item) {
		this.item = item;
	}

	public Date getExpireOn() {
		return expireOn;
	}

	public void setExpireOn(Date expireOn) {
		this.expireOn = expireOn;
	}

}
