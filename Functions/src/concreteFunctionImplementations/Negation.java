/**
 * 
 */
package concreteFunctionImplementations;

/**
 * @author dimaw
 *
 */
public final class Negation extends FunctionOneParameter
{

	/**
	 * @param func 
	 * 
	 */
	private Negation(OneVariableFunction func)
	{
	    m_func = func;
	}
	
	/**
	 * @param func
	 * @return negation of this function
	 */
	public static OneVariableFunction getInstance(OneVariableFunction func)
	{
	    if(func instanceof Negation) return ((Negation) func).m_func;
	    
	    return new Negation(func);
	}

	@Override
	public double valueAt(double x)
	{
		return (- m_func.valueAt(x));
	}

	@Override
	public OneVariableFunction getDerivation()
	{
		if(null == m_derivation)
		{
		    m_derivation = new Negation(m_func.getDerivation());
		}
		
		return m_derivation;
	}

    @Override
    public boolean isPeriodic()
    {
        return m_func.isPeriodic();
    }
    
    @Override
    public double getPeriod()
    {
        return m_func.getPeriod();
    }
    
    @Override
    public boolean isLinear()
    {
        return m_func.isLinear();
    }
    
    @Override
    public boolean equals(Object arg)
    {
        if(null == arg) return false;
        if(this == arg) return true;
        if(false == arg instanceof Negation) return false;
        
        Negation argNeg = (Negation) arg;
        return m_func.equals(argNeg.m_func);
    }
    
    @Override
    public int hashCode()
    {
        return (-m_func.hashCode());
    }

}
