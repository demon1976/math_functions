/**
 * 
 */
package concreteFunctionImplementations;

/**
 * @author dimaw
 *
 */
public final class ArCosH extends FunctionOneParameter
{
	/**
	 * 
	 */
	private ArCosH(OneVariableFunction arg)
	{
        m_func = arg;
	}
    
    /**
     * @param arg
     * @return an instance which means the arCosH of argument
     */
    public static OneVariableFunction getInstance(OneVariableFunction arg)
    {
        if(arg instanceof CosH)
        {
            CosH argCosH = (CosH) arg;
            return argCosH.getFunctionArgument();
        }

        return new ArCosH(arg);
    }

	@Override
	public double valueAt(double x)
	{
	    x = m_func.valueAt(x);
		if(false == Double.isFinite(x)) throw new IllegalArgumentException("argument " + x + " is not finite"); //$NON-NLS-1$ //$NON-NLS-2$
		else if(x < 1.0) throw new IllegalArgumentException("argument should be greater or equal than zero but is " + x); //$NON-NLS-1$
		else if(x > 1E08)
		{
			return (Math.log(2.0) + Math.log(x));
		}
		else
		{
			return Math.log(x + Math.pow(x * x - 1.0, 0.5));
		}
	}

	@Override
	public OneVariableFunction getDerivation()
	{
		if(null == m_derivation)
		{
            OneVariableFunction divisor = Multiplication.getInstance(m_func, m_func);
            divisor = Substraction.getInstance(divisor, Constant.ONE);
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
        if(false == arg instanceof ArCosH)  return false;

        ArCosH argArCosH = (ArCosH) arg;
        if(m_func.equals(argArCosH.m_func)) return true;

        return false;
    }
    
    @Override
    public int hashCode()
    {
        return (m_func.hashCode()<<13 + 350936);
    }
}
