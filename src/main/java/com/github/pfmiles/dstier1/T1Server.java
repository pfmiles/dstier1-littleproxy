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

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.github.pfmiles.dstier1.impl.DsT1AdaptingHttpFilters;
import com.github.pfmiles.dstier1.impl.SortableFilterMethod;
import com.google.common.base.Strings;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.HttpProxyServerBootstrap;
import org.littleshoot.proxy.TransportProtocol;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.littleshoot.proxy.impl.ProxyUtils;
import org.littleshoot.proxy.impl.ThreadPoolConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class T1Server {

	private static final Logger logger = LoggerFactory.getLogger(T1Server.class);

	/**
	 * All the confs needed to start a T1 server
	 */
	private T1Conf conf;

	public T1Server(T1Conf conf) {
		this.conf = conf;
	}

	/**
	 * Start the T1 server
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public void start() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		logger.info("Loading and checking configurations...");
		if (conf.isLocalOnly()) {
			logger.info("Running as a local-only proxy.");
		}
		HttpProxyServerBootstrap bootstrap = DefaultHttpProxyServer.bootstrap().withAllowLocalOnly(conf.isLocalOnly());
		if (conf.isTransparent()) {
			logger.info("Running in transparent mode.");
		}
		bootstrap.withTransparent(conf.isTransparent());
		bootstrap.withIdleConnectionTimeout(conf.getIdleConnectionTimeout());
		bootstrap.withConnectTimeout(conf.getConnectTimeout());
		bootstrap.withMaxInitialLineLength(conf.getMaxInitialLineLength());
		bootstrap.withMaxHeaderSize(conf.getMaxHeaderSize());
		bootstrap.withMaxChunkSize(conf.getMaxChunkSize());

		if (!Strings.isNullOrEmpty(conf.getNic())) {
			logger.info("Outbound network interface card specified: " + conf.getNic());
			bootstrap.withNetworkInterface(new InetSocketAddress(conf.getNic(), 0));
		}

		if (conf.getMitmManager() != null) {
			logger.info("Running as Man in the Middle, using MITM manager: " + conf.getMitmManager().toString());
			bootstrap.withManInTheMiddle(conf.getMitmManager());
		}

		if (!Strings.isNullOrEmpty(conf.getDnssec())) {
			final String val = conf.getDnssec();
			if (ProxyUtils.isTrue(val)) {
				logger.info("Using DNSSEC");
				bootstrap.withUseDnsSec(true);
			} else if (ProxyUtils.isFalse(val)) {
				logger.info("Not using DNSSEC");
				bootstrap.withUseDnsSec(false);
			}
		}
		bootstrap.withName(conf.getName());
		bootstrap.withTransportProtocol(parseTp(conf.getTransportProtocol()));

		if (!Strings.isNullOrEmpty(conf.getRequestAddress())) {
			InetSocketAddress addr = new InetSocketAddress(conf.getRequestAddress(), conf.getPort());
			bootstrap.withAddress(addr);
			logger.info("Will listen on: " + addr);
		} else {
			bootstrap.withPort(conf.getPort());
			logger.info("Will listen on port: " + conf.getPort());
		}

		// bootstrap.withSslEngineSource(sslEngineSource) TODO not implemented yet, this
		// mutually exclusive with mitmManager

		// bootstrap.withAuthenticateSslClients(authenticateSslClients) TODO not
		// implemented yet, this only takes effect when sslEngineSource is set

		// bootstrap.withProxyAuthenticator(proxyAuthenticator) TODO not implemented
		// yet,
		// do authentication on proxy

		// bootstrap.withChainProxyManager(chainProxyManager) TODO not implemented yet

		// bootstrap.plusActivityTracker(activityTracker) TODO not implemented yet

		// bootstrap.withServerResolver(conf.getServerResolver());

		bootstrap.withThrottling(conf.getReadThrottleBytesPerSecond(), conf.getWriteThrottleBytesPerSecond());
		bootstrap.withAllowRequestToOriginServer(conf.isReverseMode());

		String viaPseudo = conf.getViaPseudonym();
		if (!Strings.isNullOrEmpty(viaPseudo)) {
			try {
				String host = InetAddress.getLocalHost().getHostName();
				viaPseudo = viaPseudo + '-' + host;
			} catch (UnknownHostException e) {
				// ignored...
			}
		}
		bootstrap.withProxyAlias(viaPseudo);

		ThreadPoolConfiguration poolConf = new ThreadPoolConfiguration();
		poolConf.withAcceptorThreads(conf.getAcceptorThreads());
		poolConf.withClientToProxyWorkerThreads(conf.getClientToProxyWorkerThreads());
		poolConf.withProxyToServerWorkerThreads(conf.getProxyToServerWorkerThreads());
		bootstrap.withThreadPoolConfiguration(poolConf);
		bootstrap.withSiteMappingManager(conf.getSiteMappingManager());

		/*
		 * every time on filters be invoking is to creating new ones, thread-safety by
		 * default
		 */
		bootstrap.withFiltersSource(new HttpFiltersSourceAdapter() {

			@Override
			public HttpFilters filterRequest(HttpRequest originalRequest) {
				return this.filterRequest(originalRequest, null);
			}

			// only invoked at initial requests(not chunks)
			@Override
			public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
				// 1.if no interests in site mapping, return null
				if (conf.getSiteMappingManager() == null) {
					return null;
				}
				String fromSite = ProxyUtils.extractSite(originalRequest);
				String toSite = conf.getSiteMappingManager().siteMapping(fromSite);
				if (StringUtils.isBlank(toSite)) {
					return null;
				}
				// 2.if no filters will be activated, return null
				if (conf.getFiltersFactory() == null) {
					return null;
				}
				Collection<T1Filter> filters = conf.getFiltersFactory().buildFilters(new RequestInfo(originalRequest));
				if (filters == null || filters.isEmpty()) {
					return null;
				}
				// dedup and keep insertion order...
				Set<T1Filter> fs = new LinkedHashSet<T1Filter>(filters);
				// Pair<reqMethods, rspMethods>
				Pair<List<SortableFilterMethod>, List<SortableFilterMethod>> filterMethods = ProxyUtils
						.buildSortedFilterMethods(fs);
				if (filterMethods == null) {
					return null;
				}
				List<SortableFilterMethod> reqMethods = filterMethods.getLeft();
				List<SortableFilterMethod> rspMethods = filterMethods.getRight();
				if (reqMethods == null || reqMethods.isEmpty() || rspMethods == null || rspMethods.isEmpty()) {
					return null;
				}
				if (reqMethods.size() != rspMethods.size()) {
					throw new RuntimeException("It's impossible to get here, just for coding validity.");
				}
				// 3.create adapting filters
				return new DsT1AdaptingHttpFilters(ctx, originalRequest, fromSite, toSite, reqMethods, rspMethods,
						conf.isFailFastOnFilterError());
			}

		});

		logger.info("Starting with configurations: \n" + conf.toString());
		bootstrap.start();
	}

	private TransportProtocol parseTp(String transportProtocol) {
		try {
			return TransportProtocol.valueOf(transportProtocol);
		} catch (Exception e) {
			String err = "Invalied transportProtocol name: " + String.valueOf(transportProtocol)
					+ ", only these are allowed(case sensitive): " + Arrays.toString(TransportProtocol.values());
			logger.error(err);
			throw e;
		}
	}

	public T1Conf getConf() {
		return conf;
	}

	public void setConf(T1Conf conf) {
		this.conf = conf;
	}
}
