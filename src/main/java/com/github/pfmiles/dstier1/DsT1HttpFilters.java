package com.github.pfmiles.dstier1;

import java.net.InetSocketAddress;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main filter in which all the 'DS' logic embed.
 * 
 * @author pf-miles
 *
 */
public class DsT1HttpFilters extends HttpFiltersAdapter {

	private static final Logger logger = LoggerFactory.getLogger(DsT1HttpFilters.class);

	public DsT1HttpFilters(HttpRequest originalRequest, ChannelHandlerContext ctx) {
		super(originalRequest, ctx);
	}

	public DsT1HttpFilters(HttpRequest originalRequest) {
		super(originalRequest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.littleshoot.proxy.HttpFiltersAdapter#clientToProxyRequest(io.netty.
	 * handler.codec.http.HttpObject)
	 */
	@Override
	public HttpResponse clientToProxyRequest(HttpObject httpObject) {
		logger.info("clientToProxyRequest: " + httpObject.toString());
		// TODO Auto-generated method stub
		return super.clientToProxyRequest(httpObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.littleshoot.proxy.HttpFiltersAdapter#proxyToServerRequest(io.netty.
	 * handler.codec.http.HttpObject)
	 */
	@Override
	public HttpResponse proxyToServerRequest(HttpObject httpObject) {
		logger.info("proxyToServerRequest: " + httpObject.toString());
		// TODO Auto-generated method stub
		return super.proxyToServerRequest(httpObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.littleshoot.proxy.HttpFiltersAdapter#proxyToServerRequestSending()
	 */
	@Override
	public void proxyToServerRequestSending() {
		logger.info("proxyToServerRequestSending...");
		// TODO Auto-generated method stub
		super.proxyToServerRequestSending();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.littleshoot.proxy.HttpFiltersAdapter#proxyToServerRequestSent()
	 */
	@Override
	public void proxyToServerRequestSent() {
		logger.info("proxyToServerRequestSent.");
		// TODO Auto-generated method stub
		super.proxyToServerRequestSent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.littleshoot.proxy.HttpFiltersAdapter#serverToProxyResponse(io.netty.
	 * handler.codec.http.HttpObject)
	 */
	@Override
	public HttpObject serverToProxyResponse(HttpObject httpObject) {
		logger.info("serverToProxyResponse: " + httpObject.toString());
		// TODO Auto-generated method stub
		return super.serverToProxyResponse(httpObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.littleshoot.proxy.HttpFiltersAdapter#serverToProxyResponseTimedOut()
	 */
	@Override
	public void serverToProxyResponseTimedOut() {
		logger.info("serverToProxyResponseTimedOut...");
		// TODO Auto-generated method stub
		super.serverToProxyResponseTimedOut();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.littleshoot.proxy.HttpFiltersAdapter#serverToProxyResponseReceiving()
	 */
	@Override
	public void serverToProxyResponseReceiving() {
		logger.info("serverToProxyResponseReceiving...");
		// TODO Auto-generated method stub
		super.serverToProxyResponseReceiving();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.littleshoot.proxy.HttpFiltersAdapter#serverToProxyResponseReceived()
	 */
	@Override
	public void serverToProxyResponseReceived() {
		logger.info("serverToProxyResponseReceived.");
		// TODO Auto-generated method stub
		super.serverToProxyResponseReceived();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.littleshoot.proxy.HttpFiltersAdapter#proxyToClientResponse(io.netty.
	 * handler.codec.http.HttpObject)
	 */
	@Override
	public HttpObject proxyToClientResponse(HttpObject httpObject) {
		logger.info("proxyToClientResponse: " + httpObject.toString());
		// TODO Auto-generated method stub
		return super.proxyToClientResponse(httpObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.littleshoot.proxy.HttpFiltersAdapter#proxyToServerConnectionQueued()
	 */
	@Override
	public void proxyToServerConnectionQueued() {
		logger.info("proxyToServerConnectionQueued...");
		// TODO Auto-generated method stub
		super.proxyToServerConnectionQueued();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.littleshoot.proxy.HttpFiltersAdapter#proxyToServerResolutionStarted(java.
	 * lang.String)
	 */
	@Override
	public InetSocketAddress proxyToServerResolutionStarted(String resolvingServerHostAndPort) {
		logger.info("proxyToServerResolutionStarted: " + resolvingServerHostAndPort);
		// TODO Auto-generated method stub
		return super.proxyToServerResolutionStarted(resolvingServerHostAndPort);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.littleshoot.proxy.HttpFiltersAdapter#proxyToServerResolutionFailed(java.
	 * lang.String)
	 */
	@Override
	public void proxyToServerResolutionFailed(String hostAndPort) {
		logger.info("proxyToServerResolutionFailed: " + hostAndPort);
		// TODO Auto-generated method stub
		super.proxyToServerResolutionFailed(hostAndPort);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.littleshoot.proxy.HttpFiltersAdapter#proxyToServerResolutionSucceeded(
	 * java.lang.String, java.net.InetSocketAddress)
	 */
	@Override
	public void proxyToServerResolutionSucceeded(String serverHostAndPort, InetSocketAddress resolvedRemoteAddress) {
		logger.info("proxyToServerResolutionSucceeded: " + serverHostAndPort + ", resolved: " + resolvedRemoteAddress);
		// TODO Auto-generated method stub
		super.proxyToServerResolutionSucceeded(serverHostAndPort, resolvedRemoteAddress);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.littleshoot.proxy.HttpFiltersAdapter#proxyToServerConnectionStarted()
	 */
	@Override
	public void proxyToServerConnectionStarted() {
		logger.info("proxyToServerConnectionStarted...");
		// TODO Auto-generated method stub
		super.proxyToServerConnectionStarted();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.littleshoot.proxy.HttpFiltersAdapter#
	 * proxyToServerConnectionSSLHandshakeStarted()
	 */
	@Override
	public void proxyToServerConnectionSSLHandshakeStarted() {
		logger.info("proxyToServerConnectionSSLHandshakeStarted...");
		// TODO Auto-generated method stub
		super.proxyToServerConnectionSSLHandshakeStarted();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.littleshoot.proxy.HttpFiltersAdapter#proxyToServerConnectionFailed()
	 */
	@Override
	public void proxyToServerConnectionFailed() {
		logger.info("proxyToServerConnectionFailed...");
		// TODO Auto-generated method stub
		super.proxyToServerConnectionFailed();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.littleshoot.proxy.HttpFiltersAdapter#proxyToServerConnectionSucceeded(io.
	 * netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void proxyToServerConnectionSucceeded(ChannelHandlerContext serverCtx) {
		logger.info("proxyToServerConnectionSucceeded, serverCtx: " + serverCtx);
		// TODO Auto-generated method stub
		super.proxyToServerConnectionSucceeded(serverCtx);
	}

}
