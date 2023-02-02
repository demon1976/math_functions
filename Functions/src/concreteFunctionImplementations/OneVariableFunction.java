package concreteFunctionImplementations;

import java.util.Set;

import Function.Surroundings;

/**
 * @author dimaw
 *
 */
public abstract class OneVariableFunction 
{
	protected OneVariableFunction m_derivation = null;
	/**
	 * @param x value at which function should be evaluated
	 * @return value of the function at x
	 */
	public abstract double valueAt(double x);
	
	/**
	 * @return derivation function
	 */
	public abstract OneVariableFunction getDerivation();
	
	/**
	 * if one zero was found then it will be returned. By polynomials 
	 * it will be tried to find all zeros of function. 
	 * @return array of zeros of function or null when nothing found
	 */
	public Set<Double> getZeroOfFunc()
    {
        return Surroundings.getZeroViaNewtonOf(this);
    }
	
	/**
	 * Try to find any zero of function between borders
	 * If one zero was found then it will be returned. By polynomials 
	 * it will be tried to find all zeros of function. 
	 * @param leftBound
	 * @param rightBound
	 * @return array of zeros of function or null when nothing found
	 */
	public Set<Double> getZeroOfFunc(double leftBound, double rightBound)
    {
        return Surroundings.getZeroViaNewtonOf(this, leftBound, rightBound);
    }
	
	
	
	/**
	 * @return true if the function is periodical, false otherwise
	 */
	@SuppressWarnings("static-method")
    public boolean isPeriodic()
    {
        return false;
    }
	
	/**
	 * @return the period of the function if it is periodic or NaN otherwise
	 */
	@SuppressWarnings("static-method")
    public double getPeriod()
	{
	    return Double.NaN;
	}
	
	/**
	 * @return true if the function is a*x + b, false otherwise
	 */
	@SuppressWarnings("static-method")
    public boolean isLinear()
	{
	    return false;
	}
}
