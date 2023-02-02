/**
 * 
 */
package concreteFunctionImplementations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import Function.Surroundings;

/**
 * @author dimaw
 *
 * the numeration of coefficients starts with a0 which is coeff without zero
 * a1 is coef by x, a2 by x^2 and so on until an by x^n. So if array of coefs 
 * has length n, then the highest x power is x^(n-1) and the index of its coed
 * is (n-1)
 */
public final class Polynomial extends OneVariableFunction 
{
	private List<Double> m_coefs;
	
	private Polynomial(ArrayList<Double> aList)
	{
        // the order of coefs is reverse.
        m_coefs = new ArrayList<>();
        for(int i = (aList.size() - 1); i >= 0; --i)
        {
            m_coefs.add(aList.get(i));
        }
	}

	/**
	 * @param coefArray 
	 * @return return a func which means this polynomial
	 * 
	 */
	@SuppressWarnings({ "boxing" })
	public static OneVariableFunction getInstance(double[] coefArray)
	{
	    if(0 == coefArray.length) return Constant.ZERO;
        if(1 == coefArray.length) return Constant.getInstance(coefArray[0]);

        ArrayList<Double> helper = new ArrayList<>();
        boolean foundFirstNonZeroCoef = false;
        for( int i = (coefArray.length - 1); i >= 0; --i)
        {
            if(Double.isNaN(coefArray[i])) throw new IllegalArgumentException("at position " + i + " is NaN"); //$NON-NLS-1$
            if(Double.isInfinite(coefArray[i])) throw new IllegalArgumentException("at position " + i + " is infinity"); //$NON-NLS-1$
            
            if(foundFirstNonZeroCoef) helper.add(coefArray[i]);
            else if(Math.abs(coefArray[i]) > Surroundings.getEpsilon())
            {
                helper.add(coefArray[i]);
                foundFirstNonZeroCoef = true;
            }
        }
        
        if(0 == helper.size()) return Constant.ZERO;
        if(1 == helper.size()) return Constant.getInstance(helper.get(0));
        
        return new Polynomial(helper);
	}
	
	/**
	 * @return degree of polynomial: 0 for const, 1 for linear, 2 for quadratic, 3 for cubic and so on.
	 */
	public int degree()
	{
	    return (m_coefs.size() - 1);
	}
	
	/**
	 * @param x
	 * @return value of function at p_x
	 * 
	 */
	@SuppressWarnings({ "boxing" })
	@Override
	public double valueAt(double x) 
	{
		double result = 0.0;
		for(int i = m_coefs.size() - 1; i > 0; --i)
		{
			result += m_coefs.get(i);
			result *= x;
		}
		result += m_coefs.get(0);
		return result;
	}
	
	/**
	 * @return derivation of polynomial. If it is already a linear polynomial,
	 * then the derivation is a constant value
	 * 
	 */
	@SuppressWarnings({ "boxing" })
	@Override
	public OneVariableFunction getDerivation() 
	{
		if(null == m_derivation)
		{
			if(2 == m_coefs.size())
			{
				m_derivation = Constant.getInstance(m_coefs.get(1));
			}
			else
			{
				double[] coefs = new double[m_coefs.size() - 1];
				for(int i = 0, n = coefs.length; i < n; ++i)
				{
					coefs[i] = (m_coefs.get(i+1) * (i+1));
				}
				
				m_derivation =  getInstance(coefs);
			}
		}
		
		return m_derivation;
	}

	@SuppressWarnings("boxing")
    @Override
	public Set<Double> getZeroOfFunc()
	{
		boolean isLinear = true;
		boolean isQuadratic = true;
        boolean isNewtonNecessary = false;
		
		int index = 1;
		while(!isNewtonNecessary && (index < m_coefs.size()))
		{
            if((2 == index) && (Math.abs(m_coefs.get(index)) > Surroundings.getEpsilon()))
            {
                isLinear = false;
            }
            if((index >= 3) && (Math.abs(m_coefs.get(index)) > Surroundings.getEpsilon()))
            {
                isLinear = false;
                isQuadratic = false;
                isNewtonNecessary = true;
            }
		    ++index;
		}
		
		if(!isNewtonNecessary)
		{
            HashSet<Double> result = new HashSet<>();if(isLinear)
    		{
    		    result.add(solveLinear(m_coefs.get(1), m_coefs.get(0)));
    		    return result;
    		}
    		else if(isQuadratic)
    		{
    		    return solveQuadratic(m_coefs.get(2), m_coefs.get(1), m_coefs.get(0));
    		}
    		else
    		{
    		    return result;
    		}
		}
		
		return Surroundings.getZeroViaNewtonOf(this);
	}

	@SuppressWarnings("boxing")
    @Override
	public Set<Double> getZeroOfFunc(double leftBound, double rightBound)
	{
        boolean isLinear = true;
        boolean isQuadratic = true;
        
        int index = 1;
        boolean isNewtonNecessary = false;
        while(!isNewtonNecessary && (index < m_coefs.size()))
        {
            if((2 == index) && (Math.abs(m_coefs.get(index)) > Surroundings.getEpsilon()))
            {
                isLinear = false;
            }
            if((index >= 3) && (Math.abs(m_coefs.get(index)) > Surroundings.getEpsilon()))
            {
                isLinear = false;
                isQuadratic = false;
                isNewtonNecessary = true;
            }
            ++index;
        }
        
        if(isNewtonNecessary) return Surroundings.getZeroViaNewtonOf(this, leftBound, rightBound);

        HashSet<Double> result = new HashSet<>();
        if(isLinear)
        {
            double resDouble = solveLinear(m_coefs.get(1), m_coefs.get(0));
            if((leftBound <= resDouble) && (resDouble <= rightBound))
            {
                result.add(resDouble);
            }
        }
        else if(isQuadratic)
        {
            Set<Double> resultSet = solveQuadratic(m_coefs.get(2), m_coefs.get(1), m_coefs.get(0));
            Iterator<Double> iter = resultSet.iterator();
            
            while(iter.hasNext())
            {
                double solution = iter.next();
                if((leftBound <= solution) && (solution <= rightBound))
                {
                    result.add(solution);
                }
            }
        }
        return result;
	}
	
	@Override
    public boolean isLinear()
	{
	    return (m_coefs.size() < 2);
	}
    
    @Override
    public boolean equals(Object arg)
    {
        if(null == arg) return false;
        if(this == arg) return true;
        if(false == arg instanceof Polynomial) return false;
        
        Polynomial poly = (Polynomial) arg;
        if(m_coefs.size() != poly.m_coefs.size()) return false;
        for(int i = 0; i < m_coefs.size() ; ++i)
        {
            if(Math.abs(m_coefs.get(i).doubleValue() - poly.m_coefs.get(i).doubleValue()) > Surroundings.getEpsilon()) return false;
        }

        return true;
    }
    
    @Override
    public int hashCode()
    {
        int result = (int) m_coefs.get(0).doubleValue();
        for(int i = 1; i < m_coefs.size(); ++i)
        {
            result <<= 1;
            result += (int) m_coefs.get(i).doubleValue();
        }
        
        return result;
    }
    
    private static double solveLinear(double a, double b) // y = a*x + b
    {
        return (-b / a);
    }
    
    @SuppressWarnings("boxing")
    private static Set<Double> solveQuadratic(double a, double b, double c) // y = a*x^2 + b*x + c
    {
        HashSet<Double> result = new HashSet<>();
        double discriminant = b*b - 4.0*a*c;
        if(Math.abs(discriminant) <= Surroundings.getEpsilon()) // discriminant = 0
        {
            result.add(-b /(2.0 * a));
        }
        else if(discriminant > 0)
        {
            double solution = (-b + Math.sqrt(discriminant)) / (2.0 * a);
            result.add(solution);
            solution = (-b - Math.sqrt(discriminant)) / (2.0 * a);
            result.add(solution);
        }
        // if discriminant < 0 there is no solution, so nothing to add to result set
        return result;
    }

}
