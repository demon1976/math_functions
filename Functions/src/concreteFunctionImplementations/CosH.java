/**
 * 
 */
package concreteFunctionImplementations;

import java.util.HashSet;
import java.util.Set;

/**
 * @author dimaw
 *
 */
public final class CosH extends FunctionOneParameter
{
	/**
	 * 
	 */
	private CosH(OneVariableFunction arg)
	{
        m_func = arg;
	}
	
	/**
	 * @param arg
	 * @return an instance which means the cosh of argument
	 */
	public static OneVariableFunction getInstance(OneVariableFunction arg)
	{
        if(arg instanceof ArCosH)
        {
            ArCosH argArCosH = (ArCosH) arg;
            return argArCosH.getFunctionArgument();
        }
        return new CosH(arg);
	}

	@Override
	public double valueAt(double x)
	{
	    x = m_func.valueAt(x);
		return (0.5*(Math.exp(x) + Math.exp(-x)));
	}

	@Override
	public OneVariableFunction getDerivation()
	{
		if(null == m_derivation)
		{
			m_derivation = SinH.getInstance(m_func);
            m_derivation = Multiplication.getInstance(m_derivation, m_func.getDerivation());
		}
		
		return m_derivation;
	}

    @Override
    public Set<Double> getZeroOfFunc()
    {
        return (new HashSet<>());
    }

    @Override
    public Set<Double> getZeroOfFunc(double p_leftBound, double p_rightBound)
    {
        return (new HashSet<>());
    }
    
    @Override
    public boolean equals(Object arg)
    {
        if(null == arg)                  return false;
        if(this == arg)                  return true;
        if(false == arg instanceof CosH) return false;

        CosH argCosH = (CosH) arg;
        if(m_func.equals(argCosH.m_func)) return true;

        return false;
    }
    
    @Override
    public int hashCode()
    {
        return (m_func.hashCode()<<8 + 851436);
    }

}
