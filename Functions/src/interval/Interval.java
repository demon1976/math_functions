package interval;

import java.util.Set;

/**
 * @author dimaw
 *
 */
public interface Interval 
{
	/**
	 * @param p_arg
	 * @return true if value inside of interval, false otherwise
	 */
	public boolean isValueIncluded(double p_arg);
	
	/**
	 * @param p_arg
	 * @return first occurrence of value inside of array inside of interval or NaN if not.
	 */
	public double getFirstOccurrense(Set<Double> p_arg);
}
