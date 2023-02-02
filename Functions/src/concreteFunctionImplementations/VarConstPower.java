/**
 * 
 */
package concreteFunctionImplementations;

import java.util.HashSet;
import java.util.Set;

import Function.Surroundings;

/**
 * x^n
 * @author dimaw
 *
 */
public final class VarConstPower extends OneVariableFunction 
{
    private double m_power;
    /**
     * 
     */
    private VarConstPower(double power)
    {
        m_power = power;
    }

	/**
	 * @param power 
	 * @return a func which represents x^n
	 * 
	 */
	public static OneVariableFunction getInstance(double power)
	{
		if(Double.isNaN(power)) throw new IllegalArgumentException("power is NaN"); //$NON-NLS-1$
		else if(Double.isInfinite(power)) throw new IllegalArgumentException("power is infinite"); //$NON-NLS-1$
		else if(Math.abs(power) < Surroundings.getEpsilon())
		{
		    return Constant.ONE; 
		}
        else if(Math.abs(power - 1.0) < Surroundings.getEpsilon())
        {
            return PlainVar.VAR; 
        }
        else if(Math.abs(power + 1.0) < Surroundings.getEpsilon())
        {
            return Division.getInstance(Constant.ONE, PlainVar.VAR); 
        }
		else
		{
			return new VarConstPower(power);
		}
	}

	@Override
	public double valueAt(double x) 
	{
		return (Math.pow(x, m_power));
	}

	@Override
	public OneVariableFunction getDerivation() 
	{
		if(null == m_derivation)
		{
		    if(Math.abs(m_power - 1.0) < Surroundings.getEpsilon())
		    {
		        m_derivation = Multiplication.getInstance(Constant.getInstance(2.0), PlainVar.VAR);
		    }
		    else
		    {
		        m_derivation = Multiplication.getInstance(Constant.getInstance(m_power), 
		                                              VarConstPower.getInstance(m_power - 1.0));
            }
		}

		return m_derivation;
	}
    
    @SuppressWarnings("boxing")
    @Override
    public Set<Double> getZeroOfFunc()
    {
        HashSet<Double> result = new HashSet<>();
        result.add(0.0);
        return result;
    }
        
    /**
     * Try to find any zero of function between borders
     * If one zero was found then it will be returned. By polynomials 
     * it will be tried to find all zeros of function. 
     * @param leftBound
     * @param rightBound
     * @return array of zeros of function or null when nothing found
     */
    @SuppressWarnings("boxing")
    @Override
    public Set<Double> getZeroOfFunc(double leftBound, double rightBound)
    {
        HashSet<Double> result = new HashSet<>();
        if((leftBound <= 0.0) && (0.0 <= rightBound)) result.add(0.0);
        return result;
    }
    
    @Override
    public boolean equals(Object arg)
    {
        if(null == arg) return false;
        if(this == arg) return true;
        if(false == arg instanceof VarConstPower) return false;
        
        VarConstPower other = (VarConstPower) arg;
        if(Math.abs(m_power - other.m_power) > Surroundings.getEpsilon()) return false;

        return true;
    }
    
    @Override
    public int hashCode()
    {
        return ((((int) m_power)<<9) + 666);
    }
    
    double getPower()
    {
        return m_power;
    }

}
