/**
 * 
 */
package concreteFunctionImplementations;

/**
 * @author dimaw
 *
 */
public final class ArcSin extends FunctionOneParameter
{
	/**
	 * 
	 */
	private ArcSin(OneVariableFunction arg)
	{
        m_func = arg;
	}
	
	/**
	 * @param arg
	 * @return an instance which means the arcsin of argument
	 */
	public static OneVariableFunction getInstance(OneVariableFunction arg)
	{
        if(arg instanceof Sin)
        {
            Sin argSin = (Sin) arg;
            return argSin.getFunctionArgument();
        }
        return new ArcSin(arg);
	}

	@Override
	public double valueAt(double x)
	{
        return Math.asin(m_func.valueAt(x));
	}

	@Override
	public OneVariableFunction getDerivation()
	{
		if(null == m_derivation)
		{
            OneVariableFunction divisor = Multiplication.getInstance(m_func, m_func);
            divisor = Substraction.getInstance(Constant.ONE,divisor);
            divisor = FuncPowerFunc.getInstance(divisor, Constant.HALF);

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
        if(false == arg instanceof ArcSin)  return false;

        ArcSin argArcSin = (ArcSin) arg;
        if(m_func.equals(argArcSin.m_func)) return true;

        return false;
    }
    
    @Override
    public int hashCode()
    {
        return (m_func.hashCode()<<15 + 301436);
    }
}
