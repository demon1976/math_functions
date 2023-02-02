/**
 * 
 */
package concreteFunctionImplementations;

/**
 * @author dimaw
 *
 */
public final class ArcTan extends FunctionOneParameter
{
	/**
	 * 
	 */
	private ArcTan(OneVariableFunction arg)
	{
        m_func = arg;
	}
	
	/**
	 * @param arg
	 * @return an instance which means the arcTan of argument
	 */
	public static OneVariableFunction getInstance(OneVariableFunction arg)
	{
        if(arg instanceof Tan)
        {
            Tan argTan = (Tan) arg;
            return argTan.getFunctionArgument();
        }
        return new ArcTan(arg);
	}

	@Override
	public double valueAt(double x)
	{
        return Math.atan(m_func.valueAt(x));
	}

	@Override
	public OneVariableFunction getDerivation()
	{
		if(null == m_derivation)
		{
            OneVariableFunction divisor = Multiplication.getInstance(m_func, m_func);
			divisor = Addition.getInstance(Constant.ONE,divisor);

			m_derivation = Division.getInstance(Constant.ONE, divisor);
            m_derivation = Multiplication.getInstance(m_derivation, m_func.getDerivation());
		}
		
		return m_derivation;
	}
    
    @Override
    public boolean equals(Object arg)
    {
        if(null == arg)                     return false;
        if(this == arg)                     return true;
        if(false == arg instanceof ArcTan)  return false;

        ArcTan argArcTan = (ArcTan) arg;
        if(m_func.equals(argArcTan.m_func)) return true;

        return false;
    }
    
    @Override
    public int hashCode()
    {
        return (m_func.hashCode()<<9 + 5301436);
    }

}
