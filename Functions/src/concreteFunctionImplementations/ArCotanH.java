/**
 * 
 */
package concreteFunctionImplementations;

import Function.Surroundings;

/**
 * @author dimaw
 *
 */
public final class ArCotanH extends FunctionOneParameter
{
	/**
	 * 
	 */
	private ArCotanH(OneVariableFunction arg)
	{
        m_func = arg;
	}
    
    /**
     * @param arg
     * @return an instance which means the arOtanH of argument
     */
    public static OneVariableFunction getInstance(OneVariableFunction arg)
    {/*
        if(arg instanceof CosH)
        {
            CosH argCosH = (CosH) arg;
            return argCosH.getFunctionArgument();
        }*/

        return new ArCotanH(arg);
    }

	@Override
	public double valueAt(double x)
	{
        x = m_func.valueAt(x);
		if(false == Double.isFinite(x)) throw new IllegalArgumentException("argument " + x + " is not finite"); //$NON-NLS-1$ //$NON-NLS-2$
		else if((Math.abs(x) < 1.0) || (Math.abs(x - 1.0) < Surroundings.getEpsilon()))
		{
			throw new IllegalArgumentException("argument should be in interval (-INF; 1.0) or (1.0; INF) but is " + x); //$NON-NLS-1$
		}
		else
		{
			double result = (x + 1.0) / (x - 1.0);
			result = Math.log(result);
			
			return (0.5 * result);
		}
	}

	@Override
	public OneVariableFunction getDerivation()
	{
		if(null == m_derivation)
		{
            OneVariableFunction divisor = Multiplication.getInstance(m_func, m_func);
            divisor = Substraction.getInstance(Constant.ONE, divisor);
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
        if(false == arg instanceof ArCotanH)  return false;

        ArCotanH argArCotanH = (ArCotanH) arg;
        if(m_func.equals(argArCotanH.m_func)) return true;

        return false;
    }
    
    @Override
    public int hashCode()
    {
        return (m_func.hashCode()<<10 + 7350936);
    }
}
