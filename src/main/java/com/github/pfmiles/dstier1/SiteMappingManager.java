/**
 * 
 */
package com.github.pfmiles.dstier1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * To manage the site mapping while proxying. For example: if specified mapping
 * from 'http://a.com' to 'http://b.com', then this would make all requests to
 * the proxy through 'http://a.com' be sent to destination 'http://b.com'.
 * Mappings are specified in a three-factor-form url like
 * 'https://google.com:488' which contains 'scheme', 'host' and 'port' infos.
 * Port info could be omitted when it's a well-known service-port pair like
 * http/80 or https/443.
 * 
 * @author pf-miles
 *
 */
public abstract class SiteMappingManager {
	private static final Logger logger = LoggerFactory.getLogger(SiteMappingManager.class);

	/**
	 * Specify with the original site, returns the mapping result or null. User
	 * should implement this method for reasonable mapping logic.
	 * 
	 * @param origSite
	 *            the original site, 'http://www.a.com:8080' for example
	 * @return the mapped destination site, 'https://www.b.com' for example, or null
	 *         indicates that the original site is of no interest to the proxy, this
	 *         would make the proxy just by pass all the reqs/resps related with the
	 *         site without any filtering.
	 */
	protected abstract String doSiteMapping(String origSite);

	/**
	 * The site mapping functionality introduced, do error handling internally.
	 * 
	 * @param origSite
	 *            the original site
	 * @return the mapped site
	 */
	public final String siteMapping(String origSite) {
		try {
			return this.doSiteMapping(origSite);
		} catch (Throwable e) {
			logger.error("Site mapping throws exception, all the reqs/rsps belonging to this site will be by passed.",
					e);
			return null;
		}
	}

}
