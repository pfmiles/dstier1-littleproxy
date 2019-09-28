/**
 * 
 */
package com.github.pfmiles.dstier1;

import org.littleshoot.proxy.impl.ProxyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author pf-miles
 *
 */
public class ServerInMain {

	private static final Logger logger = LoggerFactory.getLogger(ServerInMain.class);

	public static void main(String[] args) throws Exception {
		T1Conf conf = resolveConf();
		T1Server s = new T1Server(conf);
		s.start();
	}

	private static T1Conf resolveConf() {
		T1Conf ret = new T1Conf();
		ret.setPort(8080);
		ret.setNic(null);
		ret.setLocalOnly(false);
		// ret.setSiteMappingManager(siteMappingManager);
		// ret.setFiltersFactory(filtersFactory);
		ret.setIdleConnectionTimeout(3600);
		ret.setSiteMappingManager(new SiteMappingManager() {

			@Override
			protected String doSiteMapping(String origSite) {
				if (ProxyUtils.siteEquals("http://abc.com", origSite)) {
					return "http://bumonitor.stable.alipay.net:8080";
				} else {
					return null;
				}
			}
		});
		return ret;
	}

}
