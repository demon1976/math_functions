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
public final class PlainVar extends OneVariableFunction
{
    /**
     * 
     */
    public static final PlainVar VAR = new PlainVar();

	/**
	 * 
	 */
	private PlainVar() {}

	@Override
	public double valueAt(double x)
	{
		return x;
	}

	@Override
	public OneVariableFunction getDerivation()
	{
		return Constant.ONE;
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
        // there is just one possible instance of plain variable. So if the references are not identical then they are not eqal
        if(this == arg) return true;
        return false;
    }
    
    @Override
    public int hashCode()
    {
        return -465946592;
    }

}
