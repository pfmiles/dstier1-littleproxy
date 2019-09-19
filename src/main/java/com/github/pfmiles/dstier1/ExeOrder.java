/**
 * 
 */
package com.github.pfmiles.dstier1;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specify the execution order of request/response filtering method. A lesser
 * value indicates a greater priority which leads to be executed earlier. A
 * filtering method which is not annotated by this annotation is considered to
 * have a priority of 0.
 * 
 * @author pf-miles
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExeOrder {
	/**
	 * the priority, lesser means earlier
	 * 
	 * @return the priority
	 */
	int value();
}
