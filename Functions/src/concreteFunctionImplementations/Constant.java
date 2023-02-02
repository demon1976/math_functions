/**
 * 
 */
package concreteFunctionImplementations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import Function.*;

/**
 * @author dimaw
 *
 */
public final class Constant extends OneVariableFunction implements Function
{
    /**
     * 
     */
    public static final Constant ONE = new Constant(1.0);
    /**
     * 
     */
    public static final Constant ZERO = new Constant(0.0);
    /**
     * 
     */
    public static final Constant HALF = new Constant(0.5);
    /**
     * 
     */
    public static final Constant LN2 = new Constant(Math.log(2.0));
    /**
     * 
     */
    public static final Constant PI = new Constant(Math.PI);
    /**
     * 
     */
    public static final Constant E = new Constant(Math.E);

	private double m_constValue;
	/**
	 * @param constValue 
	 * 
	 */
	private Constant(double constValue) 
	{
		m_constValue = constValue;
	}
	
	/**
	 * @param arg
	 * @return a function for this constant
	 */
	public static OneVariableFunction getInstance(double arg)
	{
        if(Double.isNaN(arg)) throw new IllegalArgumentException("value is NaN");
        if(Double.isInfinite(arg)) throw new IllegalArgumentException("value is infinite");
        
        if(Math.abs(arg) < Surroundings.getEpsilon()) return Constant.ZERO;
        if(Math.abs(arg - 1.0) < Surroundings.getEpsilon()) return Constant.ONE;
        
        return new Constant(arg);
	}

	@Override
	public double valueAt(double x) 
	{
		return m_constValue;
	}
	
	@Override
	public  OneVariableFunction getDerivation()
	{
		return ZERO;
	}

	@SuppressWarnings("boxing")
    @Override
	/**
	 * makes no sense. If constant is zero then each value between
	 *  -INF and +INF is zero of function. Otherwise there is no zero
	 */
	public Set<Double> getZeroOfFunc()
	{
        HashSet<Double> result = new HashSet<>();
		if(Math.abs(m_constValue) < Surroundings.getEpsilon())
		{
		    result.add(Double.NEGATIVE_INFINITY);
            result.add(Double.MIN_VALUE);
            result.add(Double.POSITIVE_INFINITY);
            result.add(Double.MAX_VALUE);
            
		}
        return result;
	}

	@SuppressWarnings("boxing")
    @Override
	/**
	 * makes no sense. If constant is zero then each value between
	 *  -INF and +INF is zero of function. Otherwise there is no zero
	 */
	public Set<Double> getZeroOfFunc(double leftBound, double rightBound)
	{
        HashSet<Double> result = new HashSet<>();
        if(Math.abs(m_constValue) < Surroundings.getEpsilon())
        {
            result.add(leftBound);
            result.add(rightBound);
            
        }
        return result;
	}

	@Override
	public double valueAt(HashMap<String, Double> p_set)
	{
		return m_constValue;
	}

	@Override
	public Function getDerivation(String p_var)
	{
		return ZERO;
	}
	

    
    @Override
    public boolean isLinear()
    {
        return true;
    }
    
    @Override
    public boolean equals(Object arg)
    {
        if(null == arg) return false;
        if(this == arg) return true;
        if(false == arg instanceof Constant) return false;
        
        Constant argConst = (Constant) arg;
        if((Double.isNaN(m_constValue)) && (Double.isNaN(argConst.m_constValue))) return true;
        if((!Double.isNaN(m_constValue)) && (Double.isNaN(argConst.m_constValue))) return false;
        if((Double.isNaN(m_constValue)) && (!Double.isNaN(argConst.m_constValue))) return false;

        if((!Double.isInfinite(m_constValue)) && (Double.isInfinite(argConst.m_constValue))) return false;
        if((Double.isInfinite(m_constValue)) && (!Double.isInfinite(argConst.m_constValue))) return false;
        
        if((Double.isInfinite(m_constValue)) && (Double.isInfinite(argConst.m_constValue)))
        {
            if(m_constValue == argConst.m_constValue) return true;
            
            return false;
        }
        
        return (Math.abs(m_constValue - argConst.m_constValue) <= Surroundings.getEpsilon());
    }
    
    @Override
    public int hashCode()
    {
        return ((int) m_constValue);
    }
}

