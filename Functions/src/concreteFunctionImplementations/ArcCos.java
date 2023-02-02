/**
 * 
 */
package concreteFunctionImplementations;

/**
 * @author dimaw
 *
 */
public final class ArcCos extends FunctionOneParameter
{
	/**
	 * 
	 */
	private ArcCos(OneVariableFunction arg)
	{
        m_func = arg;
	}
	
	/**
	 * @param arg
	 * @return an instance which means the arccos of argument
	 */
	public static OneVariableFunction getInstance(OneVariableFunction arg)
	{
        if(arg instanceof Cos)
        {
            Cos argCos = (Cos) arg;
            return argCos.getFunctionArgument();
        }
        return new ArcCos(arg);
	}
	

	@Override
	public double valueAt(double x)
	{
		return Math.acos(m_func.valueAt(x));
	}

	@Override
	public OneVariableFunction getDerivation()
	{
		if(null == m_derivation)
		{
			OneVariableFunction divisor = Multiplication.getInstance(m_func, m_func);
            divisor = Substraction.getInstance(Constant.ONE,divisor);
            divisor = FuncPowerFunc.getInstance(divisor, Constant.HALF);
			OneVariableFunction quot = Division.getInstance(Constant.ONE, divisor);

			m_derivation = Negation.getInstance(quot);
			m_derivation = Multiplication.getInstance(m_derivation, m_func.getDerivation());
		}
		
		return m_derivation;
	}
    
    @Override
    public boolean equals(Object arg)
    {
        if(null == arg)                     return false;
        if(this == arg)                     return true;
        if(false == arg instanceof ArcCos)  return false;

        ArcCos argArcCos = (ArcCos) arg;
        if(m_func.equals(argArcCos.m_func)) return true;

        return false;
    }
    
    @Override
    public int hashCode()
    {
        return (m_func.hashCode()<<16 + 351436);
    }

}
