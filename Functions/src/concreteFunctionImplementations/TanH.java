/**
 * 
 */
package concreteFunctionImplementations;

/**
 * @author dimaw
 *
 */
public final class TanH extends FunctionOneParameter
{
	/**
	 * 
	 */
	private TanH(OneVariableFunction arg)
	{
        m_func = arg;
	}
	
	/**
	 * @param arg
	 * @return an instance which means the sinh of argument
	 */
	public static OneVariableFunction getInstance(OneVariableFunction arg)
	{
        if(arg instanceof ArTanH)
        {
            ArTanH argArtanH = (ArTanH) arg;
            return argArtanH.getFunctionArgument();
        }
        return new TanH(arg);
	}

	@Override
	public double valueAt(double x)
	{
        x = m_func.valueAt(x);
		double ePowX = Math.exp(x);
		double ePowMinusX = Math.exp(-x);
		
		return ((ePowX - ePowMinusX) / (ePowX + ePowMinusX));
	}

	@Override
	public OneVariableFunction getDerivation()
	{
		if(null == m_derivation)
		{
			m_derivation = CosH.getInstance(m_func);
			m_derivation = Multiplication.getInstance(m_derivation, m_derivation);
			m_derivation = Division.getInstance(Constant.ONE, m_derivation);
			m_derivation = Multiplication.getInstance(m_derivation, m_func.getDerivation());
		}

		return m_derivation;
	}
    
    @Override
    public boolean equals(Object arg)
    {
        if(null == arg)                  return false;
        if(this == arg)                  return true;
        if(false == arg instanceof TanH) return false;

        TanH argTanH = (TanH) arg;
        if(m_func.equals(argTanH.m_func)) return true;

        return false;
    }
    
    @Override
    public int hashCode()
    {
        return (m_func.hashCode()<<13 + 1001);
    }

}
