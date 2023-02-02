/**
 * 
 */
package concreteFunctionImplementations;

/**
 * @author dimaw
 *
 */
public final class ArSinH extends FunctionOneParameter
{
	/**
	 * 
	 */
	private ArSinH(OneVariableFunction arg)
	{
        m_func = arg;
	}
    
    /**
     * @param arg
     * @return an instance which means the arCosH of argument
     */
    public static OneVariableFunction getInstance(OneVariableFunction arg)
    {
        if(arg instanceof SinH)
        {
            SinH argSinH = (SinH) arg;
            return argSinH.getFunctionArgument();
        }

        return new ArSinH(arg);
    }

	@Override
	public double valueAt(double x)
	{
        x = m_func.valueAt(x);

		if(false == Double.isFinite(x)) throw new IllegalArgumentException("argument " + x + " is not finite");
		
		double multFactor = 1.0;
		if(x < 0.0)
		{
			multFactor = -1.0;
			x = -x;
		}
		
		if(x > 1E08)
		{
			return (multFactor * (Math.log(2.0) + Math.log(x)));
		}
		else if(x < 0.125)
		{
			double argQuadrat = x * x; // x^2
			double coef2 = 0.5;
			double coef3 = coef2 * 0.75;
			double coef4 = coef3 * 5.0 / 6.0;
			
			coef2 /= -3.0;
			coef3 /= 5.0;
			coef4 /= -7.0;
			
			double result = coef4 * argQuadrat;
			result += coef3;
			result *= argQuadrat;
			result += coef2;
			result *= argQuadrat;
			result += 1.0;
			result *= x;
			
			return (multFactor * result);
		}
		else
		{
			return (multFactor * Math.log(x + Math.pow(x * x + 1.0, 0.5)));
		}
	}

	@Override
	public OneVariableFunction getDerivation()
	{
		if(null == m_derivation)
		{
            OneVariableFunction divisor = Multiplication.getInstance(m_func, m_func);
            divisor = Addition.getInstance(divisor, Constant.ONE);
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
        if(false == arg instanceof ArSinH)  return false;

        ArSinH argArSinH = (ArSinH) arg;
        if(m_func.equals(argArSinH.m_func)) return true;

        return false;
    }
    
    @Override
    public int hashCode()
    {
        return (m_func.hashCode()<<13 + 35936);
    }

}
