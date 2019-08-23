/**
 * 
 */
package com.github.pfmiles.dstier1;

/**
 * @author pf-miles
 *
 */
public class ServerInMain {

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
		return ret;
	}

}
