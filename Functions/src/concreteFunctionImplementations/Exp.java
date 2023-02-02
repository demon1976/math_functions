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
public final class Exp extends FunctionOneParameter
{
    /**
     * 
     */
    private Exp(OneVariableFunction arg)
    {
        m_func = arg;
    }
    
    /**
     * @param arg
     * @return an instance which means the cosh of argument
     */
    public static OneVariableFunction getInstance(OneVariableFunction arg)
    {
        if(arg instanceof Ln)
        {
            Ln argLn = (Ln) arg;
            return argLn.getFunctionArgument();
        }
        return new Exp(arg);
    }

	@Override
	public double valueAt(double x) 
	{
		return Math.exp(m_func.valueAt(x));
	}

	@Override
	public OneVariableFunction getDerivation() 
	{
		if(null == m_derivation)
		{
            m_derivation = Multiplication.getInstance(this, m_func.getDerivation());
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
        if(false == arg instanceof Exp) return false;

        Exp argExp = (Exp) arg;
        if(m_func.equals(argExp.m_func)) return true;

        return false;
    }
    
    @Override
    public int hashCode()
    {
        return (m_func.hashCode()<<6 + 37);
    }

}
