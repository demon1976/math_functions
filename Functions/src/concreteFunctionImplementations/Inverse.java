/**
 * 
 */
package concreteFunctionImplementations;

import Function.Surroundings;
import interval.Interval;

/**
 * @author dimaw
 *
 */
public final class Inverse extends FunctionOneParameter
{
	private OneVariableFunction m_funcToInvert;
	private Interval m_interval;

	/**
	 * @param funct2Invert 
	 * @param interval 
	 * 
	 */
	private Inverse(OneVariableFunction funct2Invert, Interval interval)
	{
		m_funcToInvert = funct2Invert;
		m_interval = interval;
	}
	
	/**
	 * @param funct2Invert
	 * @param interval
	 * @return inverse function
	 */
	public static OneVariableFunction getInstance(OneVariableFunction funct2Invert, Interval interval)
	{
       if(null == funct2Invert) throw new IllegalArgumentException("function is null");
       else if(null == interval) throw new IllegalArgumentException("intervall is null");
       if(funct2Invert instanceof Inverse) return ((Inverse) funct2Invert).m_funcToInvert;
       
       return new Inverse(funct2Invert, interval);
	}

	/**
	 * find a zero via Newton algorithm
	 */
	@Override
	public double valueAt(double x)
	{
		OneVariableFunction helpFunc = Substraction.getInstance(m_funcToInvert, Constant.getInstance(x));
		
		return m_interval.getFirstOccurrense(Surroundings.getZeroViaNewtonOf(helpFunc));
	}

	/**
	 * @return (f -1)'(x) = 1 / f'(f -1(x))
	 */
	@Override
	public OneVariableFunction getDerivation()
	{
	    if(null == m_derivation)
	    {
    		OneVariableFunction result = FuncOfFunc.getInstance(m_funcToInvert.getDerivation(), this);
    		m_derivation = Division.getInstance(Constant.ONE, result);
	    }

	    return m_derivation;
	}
    
    /**
     * either both are constants or one is constant and other Plain var, 
     * vorConstPower with power 1, or polynomial with at most 2 coeffs
     */
    @Override
    public boolean isLinear()
    {
        return m_funcToInvert.isLinear();
    }
    
    @Override
    public boolean equals(Object arg)
    {
        if(null == arg) return false;
        if(this == arg) return true;
        if(false == arg instanceof Inverse) return false;
        
        Inverse argInv = (Inverse) arg;
        if(!m_interval.equals(argInv.m_interval)) return false;
        if(!m_funcToInvert.equals(argInv.m_funcToInvert)) return false;
        
        return true;
    }
    
    @Override
    public int hashCode()
    {
        return (m_funcToInvert.hashCode() << 16 + m_interval.hashCode());
    }

}
