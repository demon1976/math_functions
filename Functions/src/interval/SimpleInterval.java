/**
 * 
 */
package interval;

import java.util.Set;

import Function.Surroundings;

/**
 * @author dimaw
 *
 */
public final class SimpleInterval implements Interval 
{

	private double m_left;
	private double m_right;
	
	/**
	 * @param left 
	 * @param right 
	 * 
	 */
	public SimpleInterval(double left, double right) 
	{
		if(Double.isNaN(left) || Double.isNaN(right)) throw new IllegalArgumentException("either left or right border is NaN"); //$NON-NLS-1$
		else if(Double.compare(left, right) >= 0) throw new IllegalArgumentException("right border " + right + " is less then left border " + left); //$NON-NLS-1$ //$NON-NLS-2$
		else
		{
			m_left = left;
			m_right = right;
		}
	}

	@Override
	public boolean isValueIncluded(double arg) 
	{
		if(Double.isNaN(arg)) return false;
		else if(Double.compare(m_left, arg) > 0) return false;
		else if(Double.compare(m_right, arg) < 0) return false;
		else return true;
	}
	
	/**
	 * @param arg
	 * @return get first occurrence which is inside of interval or NaN when no value is inside
	 */
	@Override
	public double getFirstOccurrense(Set<Double> arg) 
	{
		for(double d : arg)
		{
			if(isValueIncluded(d)) return d;
		}
		
		// none of values in array is inside of interval, otherwise it were returned inside of for-loop
		return Double.NaN;
	}
    
    @Override
    public boolean equals(Object arg)
    {
        if(null  == arg) return false;
        if(this  == arg) return true;
        if(false == arg instanceof SimpleInterval) return false;
        
        SimpleInterval argInt = (SimpleInterval) arg;
        if(Math.abs(m_left  - argInt.m_left)  > Surroundings.getEpsilon()) return false;
        if(Math.abs(m_right - argInt.m_right) > Surroundings.getEpsilon()) return false;
        
        return true;
    }
    
    @Override
    public int hashCode()
    {
        return (((int) m_left) << 16 + ((int) m_right));
    }

}
