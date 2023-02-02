/**
 * 
 */
package concreteFunctionImplementations;

import Function.Surroundings;

/**
 * @author dimaw
 *
 */
public final class Addition extends OneVariableFunction
{
	private OneVariableFunction m_first;
	private OneVariableFunction m_second;

	private Addition(OneVariableFunction first, OneVariableFunction second)
	{
		m_first = first;
		m_second = second;
	}
	
	/**
	 * @param first
	 * @param second
	 * @return a function for this sum
	 */
	public static OneVariableFunction getInstance(OneVariableFunction first, OneVariableFunction second)
	{
	    if(first instanceof Constant)
        {
	        double valOfConst = first.valueAt(0.0);
            if(second instanceof Constant) return Constant.getInstance(valOfConst + second.valueAt(0.0));
            else if(Math.abs(valOfConst) < Surroundings.getEpsilon()) return second;
        }
        else if(second instanceof Constant)
        {
            double valOfConst = second.valueAt(0.0);
            if(Math.abs(valOfConst) < Surroundings.getEpsilon()) return first;
        }
	    
	    return new Addition(first, second);
	}

	@Override
	public double valueAt(double x)
	{
		return (m_first.valueAt(x) + m_second.valueAt(x));
	}

	@Override
	public OneVariableFunction getDerivation()
	{
		if(null == m_derivation)
		{
			m_derivation = getInstance(m_first.getDerivation(), m_second.getDerivation());
		}
		
		return m_derivation;
	}

	@Override
	public boolean isPeriodic()
	{
		return (m_first.isPeriodic() && m_second.isPeriodic());
	}
	
	@Override
    public double getPeriod()
    {
	    if(isPeriodic())
	    {
            return Surroundings.commonPeriod(m_first.getPeriod(), m_second.getPeriod());
	    }
	    else
        {
	        return Double.NaN;
        }
    }
	
	@Override
    public boolean isLinear()
    {
	    return (m_first.isLinear() && m_second.isLinear());
    }
	
	@Override
    public boolean equals(Object arg)
    {
	    if(null == arg)                        return false;
	    if(this == arg)                        return true;
	    if(false == arg instanceof Addition)   return false;
	    
	    Addition argAdd = (Addition) arg;
	    if(((m_first.equals(argAdd.m_first))  && (m_second.equals(argAdd.m_second))) ||
	       ((m_first.equals(argAdd.m_second)) && (m_second.equals(argAdd.m_first ))))
	    {
	        return true;
	    }
	    
	    return false;
    }
	
	@Override
    public int hashCode()
    {
	    return ((m_first.hashCode()<<16) + m_second.hashCode());
    }

}
