/**
 * 
 */
package concreteFunctionImplementations;

/**
 * @author dimaw
 *
 */
public final class SinH extends FunctionOneParameter
{
	/**
	 * 
	 */
	private SinH(OneVariableFunction arg)
	{
        m_func = arg;
	}
	
	/**
	 * @param arg
	 * @return an instance which means the sinh of argument
	 */
	public static OneVariableFunction getInstance(OneVariableFunction arg)
	{
        if(arg instanceof ArSinH)
        {
            ArSinH argArSinH = (ArSinH) arg;
            return argArSinH.getFunctionArgument();
        }
        return new SinH(arg);
	}

	@Override
	public double valueAt(double x)
	{
        x = m_func.valueAt(x);
		return (0.5*(Math.exp(x) - Math.exp(-x)));
	}

	@Override
	public OneVariableFunction getDerivation()
	{
		if(null == m_derivation)
		{
            m_derivation = CosH.getInstance(m_func);
            m_derivation = Multiplication.getInstance(m_derivation, m_func.getDerivation());
		}

		return m_derivation;
	}
    
    @Override
    public boolean equals(Object arg)
    {
        if(null == arg)                  return false;
        if(this == arg)                  return true;
        if(false == arg instanceof SinH) return false;

        SinH argSinH = (SinH) arg;
        if(m_func.equals(argSinH.m_func)) return true;

        return false;
    }
    
    @Override
    public int hashCode()
    {
        return (m_func.hashCode()<<14 + 63);
    }

}
