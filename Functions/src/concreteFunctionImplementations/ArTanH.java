/**
 * 
 */
package concreteFunctionImplementations;

import Function.Surroundings;

/**
 * @author dimaw
 *
 */
public final class ArTanH extends FunctionOneParameter
{
	/**
	 * 
	 */
	private ArTanH(OneVariableFunction arg)
	{
        m_func = arg;
	}
    
    /**
     * @param arg
     * @return an instance which means the arCosH of argument
     */
    public static OneVariableFunction getInstance(OneVariableFunction arg)
    {
        if(arg instanceof TanH)
        {
            TanH argTanH = (TanH) arg;
            return argTanH.getFunctionArgument();
        }

        return new ArTanH(arg);
    }

	@Override
	public double valueAt(double x)
	{
        x = m_func.valueAt(x);
        
		if(false == Double.isFinite(x)) throw new IllegalArgumentException("argument " + x + " is not finite"); //$NON-NLS-1$ //$NON-NLS-2$
		else if((Math.abs(x) > 1.0) || (Math.abs(x - 1.0) < Surroundings.getEpsilon()))
		{
			throw new IllegalArgumentException("argument should be in interval (-1.0; 1.0) but is " + x); //$NON-NLS-1$
		}
		else
		{
			double result = (1.0 + x) / (1.0 - x);
			result = Math.log(result);
			
			return (0.5 * result);
		}
	}

	@Override
	public OneVariableFunction getDerivation()
	{
		if(null == m_derivation)
		{
            OneVariableFunction result = Multiplication.getInstance(m_func, m_func);
			result = Substraction.getInstance(Constant.ONE, result);
			m_derivation = Division.getInstance(Constant.ONE, result);
            m_derivation = Multiplication.getInstance(m_derivation, m_func.getDerivation());
		}
		
		return m_derivation;

	}
    
    @Override
    public boolean equals(Object arg)
    {
        if(null == arg)                     return false;
        if(this == arg)                     return true;
        if(false == arg instanceof ArTanH)  return false;

        ArTanH argArTanH = (ArTanH) arg;
        if(m_func.equals(argArTanH.m_func)) return true;

        return false;
    }
    
    @Override
    public int hashCode()
    {
        return (m_func.hashCode()<<3 + 6);
    }

}
