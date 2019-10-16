/**
 * 
 */
package com.github.pfmiles.dstier1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.pfmiles.dstier1.impl.T1Utils;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * For development debugging...
 * 
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
				if (T1Utils.siteEquals("http://abc.com", origSite)) {
					return "https://www.baidu.com";
				} else {
					return null;
				}
			}
		});
		ret.setFiltersFactory(new FiltersFactory() {

			@Override
			public Collection<T1Filter> buildFilters(RequestInfo reqInfo) {
				List<T1Filter> fs = new ArrayList<>();
				fs.add(new T1Filter() {

					@Override
					public boolean active(RequestInfo req) {
						return HttpMethod.GET.equals(req.getMethod());
					}

					@Override
					public HttpResponse onRequesting(HttpObject httpObj) {
						logger.info("Proxy 1 On requesting: " + httpObj.toString());
						return null;
					}

					@Override
					@ExeOrder(1)
					public HttpObject onResponding(HttpObject httpObj) {
						logger.info("Proxy 1 On responding: " + httpObj.toString());
						return httpObj;
					}
				});
				fs.add(new T1Filter() {

					@Override
					public boolean active(RequestInfo req) {
						return HttpMethod.GET.equals(req.getMethod());
					}

					@Override
					public HttpResponse onRequesting(HttpObject httpObj) {
						logger.info("Proxy 2 On requesting: " + httpObj.toString());
						return null;
					}

					@Override
					public HttpObject onResponding(HttpObject httpObj) {
						logger.info("Proxy 2 On responding: " + httpObj.toString());
						return httpObj;
					}
				});
				return fs;
			}
		});
		return ret;
	}

}
