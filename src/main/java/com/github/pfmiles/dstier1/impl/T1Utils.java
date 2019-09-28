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

import org.apache.commons.lang3.StringUtils;
import org.littleshoot.proxy.impl.ProxyUtils;

/**
 * @author pf-miles
 *
 */
public abstract class T1Utils {

	/**
	 * Convenience method to tell if the specified two sites are the same
	 * 
	 * @param site1
	 * @param site2
	 * @return true if the two sites are the same, 'http://a.com' and
	 *         'http://a.com:80' for example, false otherwise.
	 */
	public static boolean siteEquals(String site1, String site2) {
		if (site1 == null || !ProxyUtils.SITE_PATTERN.matcher(site1).matches()) {
			throw new IllegalArgumentException("Site: '" + site1 + "' is invalid.");
		}
		if (site2 == null || !ProxyUtils.SITE_PATTERN.matcher(site2).matches()) {
			throw new IllegalArgumentException("Site: '" + site2 + "' is invalid.");
		}
		site1 = site1.toLowerCase();
		site2 = site2.toLowerCase();

		String proto1 = StringUtils.substringBefore(site1, "://");
		String proto2 = StringUtils.substringBefore(site2, "://");
		if (!proto1.equals(proto2)) {
			return false;
		}

		String domain1 = null;
		String noPro1 = StringUtils.substringAfter(site1, "://");
		if (noPro1.contains(":")) {
			domain1 = StringUtils.substringBefore(noPro1, ":");
		} else {
			domain1 = noPro1;
		}
		String domain2 = null;
		String noPro2 = StringUtils.substringAfter(site2, "://");
		if (noPro2.contains(":")) {
			domain2 = StringUtils.substringBefore(noPro2, ":");
		} else {
			domain2 = noPro2;
		}
		if (!domain1.equals(domain2)) {
			return false;
		}

		int port1 = -1;
		if (noPro1.contains(":")) {
			port1 = Integer.parseInt(StringUtils.substringAfter(noPro1, ":"));
		} else {
			// well-known default ports for http/https
			if ("http".equals(proto1)) {
				port1 = 80;
			} else {
				port1 = 443;
			}
		}
		int port2 = -1;
		if (noPro2.contains(":")) {
			port2 = Integer.parseInt(StringUtils.substringAfter(noPro2, ":"));
		} else {
			// well-known default ports for http/https
			if ("http".equals(proto2)) {
				port2 = 80;
			} else {
				port2 = 443;
			}
		}
		if (port1 != port2) {
			return false;
		}

		return true;
	}
}
