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
package com.github.pfmiles.dstier1.impl;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Encapsulates values, provides a reference to manage them. Used in store
 * values during a request/response progress.
 * 
 * @author pf-miles
 *
 */
public class ValueHolder {
	/**
	 * from which the site mapping, it's the original requesting site in the request
	 */
	private String fromSite;
	/**
	 * to which the site mapped, it's generated by the configured site mapping
	 * manager
	 */
	private String toSite;
	/**
	 * tell if the current request/response needs filtering
	 */
	private Boolean needFiltering;
	/**
	 * filter methods for this request process
	 */
	private Pair<List<SortableFilterMethod>, List<SortableFilterMethod>> filterMethods;

	public Pair<List<SortableFilterMethod>, List<SortableFilterMethod>> getFilterMethods() {
		return filterMethods;
	}

	public void setFilterMethods(Pair<List<SortableFilterMethod>, List<SortableFilterMethod>> filterMethods) {
		this.filterMethods = filterMethods;
	}

	public Boolean getNeedFiltering() {
		return needFiltering;
	}

	public void setNeedFiltering(Boolean needFiltering) {
		this.needFiltering = needFiltering;
	}

	public String getFromSite() {
		return fromSite;
	}

	public void setFromSite(String fromSite) {
		this.fromSite = fromSite;
	}

	public String getToSite() {
		return toSite;
	}

	public void setToSite(String toSite) {
		this.toSite = toSite;
	}

}
