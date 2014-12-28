/**
 * 
 */
package com.rmbin.db.mongo.annotation;

/**
 * @author louis-he
 *
 */
public @interface Entity {

	public String name() default Default.NAME;
	
	public SplitType split() default SplitType.NONE;
	
	public boolean capped() default false;
	
	public long capSize() default Default.CAP_SIZE;
	
	public long capMax() default Default.CAP_MAX;
	
}
