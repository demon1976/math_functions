/**
 * 
 */
package Function;

/**
 * @author dimaw
 *
 */
public final class ZeroPoint
{
	
	private double m_zero;
	private static double s_epsilon;

	/**
	 * @param p_value 
	 * 
	 */
	public ZeroPoint(double p_value)
	{
		if(Double.isNaN(p_value)) throw new IllegalArgumentException("NaN couldn't be a zero point");
		if(Double.isInfinite(p_value)) throw new IllegalArgumentException("INF couldn't be a zero point");
		
		m_zero = p_value;
	}

	/**
	 * @return the s_epsilon
	 */
	public static double get_epsilon()
	{
		return s_epsilon;
	}

	/**
	 * @param p_epsilon the s_epsilon to set
	 */
	public static void set_epsilon(double p_epsilon)
	{
		s_epsilon = p_epsilon;
	}
	
	@Override
	public boolean equals(Object arg)
	{
		if(this == arg) return true;
		if(null == arg) return false;
		if(getClass() != arg.getClass()) return false;
		
		ZeroPoint zp = (ZeroPoint) arg;
		
		if(Double.isNaN(zp.m_zero)) return false; // NaN couldn't be a zero point
		if(Double.isInfinite(zp.m_zero)) return false; // INF couldn't be a zero point
		
		// so arg is finite
		if(Math.abs(m_zero - zp.m_zero) > s_epsilon) return false;
		
		return true;
	}
	
	@Override
	public int hashCode()
	{
		return ((int) m_zero);
	}

}
