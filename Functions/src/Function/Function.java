package Function;

import java.util.HashMap;

/**
 * 
 */

/**
 * @author dimaw
 *
 */
public interface Function
{
	/**
	 * computes value of function at (x1, ... , xn)
	 * @param p_set 
	 * @return value of the function at (x1, ... , xn)
	 */
	public double valueAt(HashMap<String, Double> p_set);
	
	/**
	 * computes the derivation of this to var
	 * @param var the variable for computation of derivation
	 * @return Function, which is derivation of this
	 */
	public Function getDerivation(String var);
	
	
	/**
	 * @return a copy of this Function
	 */
	//public Function copy();
	

}


