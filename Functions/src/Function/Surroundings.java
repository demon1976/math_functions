/**
 * 
 */
package Function;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import concreteFunctionImplementations.OneVariableFunction;

/**
 * @author dimaw
 *
 */
public final class Surroundings 
{
	private static double s_epsilon = 1E-06;
	
	/**
	 * @return epsilon
	 */
	public static double getEpsilon()
	{
		return s_epsilon;
	}
	
	/**
	 * @param p_eps new value of epsilon
	 */
	public static void setEpsilon(double p_eps)
	{
		s_epsilon = Math.abs(p_eps);
	}
	
	/**
	 * 
	 */
	private Surroundings() {}
	
	/**
	 * @param func for which the zero should be detected
	 * @return the value by which function is zero. If no such value
	 * was found then return Double.NaN.
	 */
	public static Set<Double> getZeroViaNewtonOf(OneVariableFunction func)
	{
		return getZeroViaNewtonOf(func, Double.MIN_VALUE, Double.MAX_VALUE);
	}
	
	/**
	 * @param func
	 * @param leftBound
	 * @param rightBound
	 * @return zero of function via Newton algorothm
	 */
	@SuppressWarnings("boxing")
	public static Set<Double> getZeroViaNewtonOf(OneVariableFunction func, double leftBound, double rightBound)
	{
		if(null == func) throw new IllegalArgumentException("func is null"); //$NON-NLS-1$
		else if(Double.isNaN(leftBound))  throw new IllegalArgumentException("left boundary is null"); //$NON-NLS-1$
		else if(Double.isNaN(rightBound))  throw new IllegalArgumentException("right boundary is null"); //$NON-NLS-1$
		
		double signumLeftBound = Math.signum(func.valueAt(leftBound));
		double signumRightBound = Math.signum(func.valueAt(rightBound));
		double startPoint = 0.0;
		
		if(Double.isInfinite(leftBound) && Double.isInfinite(rightBound))
		{
			startPoint = 0.0;
			leftBound = Double.MIN_VALUE;
			rightBound = Double.MAX_VALUE;
		}
		else if(Double.isInfinite(leftBound) && Double.isFinite(rightBound))
		{
			startPoint = rightBound;
			leftBound = Double.MIN_VALUE;
		}
		else if(Double.isFinite(leftBound) && Double.isInfinite(rightBound))
		{
			startPoint = leftBound;
			rightBound = Double.MAX_VALUE;
		}
		else
		{
			startPoint = ((leftBound + rightBound) / 2.0);
		}
		
		double[] resDoubleArr = new double[] {Double.NaN};
		
		if(Math.abs(leftBound) < Surroundings.getEpsilon()) resDoubleArr = new double[]{leftBound};
		else if(Math.abs(rightBound) < Surroundings.getEpsilon()) resDoubleArr = new double[] {rightBound};
		else if(Math.abs(startPoint) < Surroundings.getEpsilon()) resDoubleArr = new double[] {startPoint};
		
		OneVariableFunction deriv = func.getDerivation();
		// simple case: the value of function has different sign on both bounds
		if((signumLeftBound * signumRightBound) < 0.0) // the signums are different
		{
			double result = newtonWithStartPoint(func,
												deriv,
												leftBound,
												rightBound,
												(signumLeftBound > 0.0),
												startPoint, Surroundings.getEpsilon(), 0);
			
			if(Math.abs(result) > Surroundings.getEpsilon()) resDoubleArr = new double[] {Double.NaN};
			else resDoubleArr = new double[] {result};
		}
		else // same signums
		{
			double eps = Surroundings.getEpsilon();
			// look whether at least the derivations have different signs
			if(Math.signum(deriv.valueAt(leftBound)) != Math.signum(deriv.valueAt(rightBound)))
			{
				double leftZero = newtonWithoutBounds(func, deriv, leftBound, eps);
				double rightZero = newtonWithoutBounds(func, deriv, rightBound, eps);
				
				if(!Double.isNaN(leftZero) && (Math.abs(leftZero) <= eps)) resDoubleArr = new double[] {leftZero};
				if(!Double.isNaN(rightZero) && (Math.abs(rightZero) <= eps))
				{
					if(1 == resDoubleArr.length)
					{
						if(Double.isNaN(resDoubleArr[0])) resDoubleArr[0] = rightZero;
						else resDoubleArr = new double[] {leftZero, rightZero};
					}
				}
			}
			else // if derivations are equal => start with absolute smaller value
			{
				if(signumLeftBound > 0.0) startPoint = Math.min(leftBound, rightBound);
				else 					  startPoint = Math.max(leftBound, rightBound);
				
				double potentialZero = newtonWithoutBounds(func, deriv, startPoint, eps);
				if(!Double.isNaN(potentialZero) && (Math.abs(potentialZero) <= eps)) resDoubleArr = new double[] {potentialZero};
			}
		}
		
		Set<Double> result = new HashSet<>();
		for(Double d: resDoubleArr)
		{
			result.add(d);
		}
		
		return result;
	}
	
	/*
	 * it assumes that the values on both bounds have different signs, so there is a at 
	 * least one zero between the bounds
	 */
	/**
	 * @param func
	 * @param derivate
	 * @param leftBound
	 * @param rightBound
	 * @param isOnLeftBoundGreaterZero
	 * @param startPoint
	 * @param epsilon
	 * @param numOfIterations
	 * @return zero point of function
	 */
	public /*private*/ static double newtonWithStartPoint(OneVariableFunction func, 
															OneVariableFunction derivate,
															double leftBound, double rightBound, boolean isOnLeftBoundGreaterZero,
															double startPoint, double epsilon, int numOfIterations)
	{
		if(numOfIterations > 7) return startPoint;
		
		double funcAtCurX = func.valueAt(startPoint);
		if(Math.abs(funcAtCurX) < epsilon) return startPoint;
		
		// due to the fact only trusted sources call this function => derivate !=0.0+epsilon
		double derivAtCurX = derivate.valueAt(startPoint);
		
		if(Math.abs(derivAtCurX) < epsilon) // derivation is equal to 0.0
		{
			boolean isComingFromLeft = true;
			if((isOnLeftBoundGreaterZero && (funcAtCurX > 0.0)) ||
			   (!isOnLeftBoundGreaterZero && (funcAtCurX < 0.0)))
			{
				leftBound = startPoint;
			}
			else
			{
				rightBound = startPoint;
				isComingFromLeft = false;
			}
			
			startPoint = getNewStartPointViaBisection(func,
													  derivate,
													  !isOnLeftBoundGreaterZero,
													  isComingFromLeft,
													  leftBound, rightBound, epsilon);
		}
		else
		{
			if(((funcAtCurX < 0.0) && (derivAtCurX < 0.0)) || ((funcAtCurX > 0.0) && (derivAtCurX > 0.0)))
			{
				rightBound = startPoint;
			}
			else
			{
				leftBound = startPoint;
			}
			
			startPoint -= funcAtCurX / derivAtCurX;
		}

		return newtonWithStartPoint(func,
									derivate,
									leftBound,
									rightBound,
									(func.valueAt(leftBound) > 0.0),
									startPoint,
									epsilon,
									numOfIterations++);
	}
	
	/**
	 * bisection algorithm
	 * @param func 
	 * @param derivate 
	 * @param searchForPositiveDerivation 
	 * @param isComingFromLeft 
	 * @param leftBound 
	 * @param rightBound 
	 * @param epsilon 
	 * @return new start point which was computed via bisection
	 */
	public /*private*/ static double getNewStartPointViaBisection(OneVariableFunction func,
														OneVariableFunction derivate,
														boolean searchForPositiveDerivation, boolean isComingFromLeft,
														double leftBound, double rightBound,
														double epsilon)
	{
		double result;
		double signumOfSearching = 1.0;
		if(false == searchForPositiveDerivation) signumOfSearching = -1.0;
		
		boolean loopTest = false;
		do
		{
			result = ((leftBound + rightBound) / 2.0);
			double funcAtResult = func.valueAt(result);
			if(Math.abs(funcAtResult) < epsilon) return result;
			
			double derivAtResult = derivate.valueAt(result);
			
			if(isComingFromLeft)
			{
				leftBound = result;
			}
			else
			{
				rightBound = result;
			}
			
			if((Math.abs(derivAtResult) < epsilon) ||
			   (Math.signum(derivAtResult) * Math.signum(signumOfSearching) < 0.0)) 
			{
				loopTest = true;
			}
		} while(loopTest);
		
		return result;
	}

	/**
	 * @param func
	 * @param derivate
	 * @param startPoint
	 * @param epsilon
	 * @return zero point which is computed via newton
	 */
	public /*private*/ static double newtonWithoutBounds(OneVariableFunction func,
        												 OneVariableFunction derivate,
        												 double startPoint, 
        												 double epsilon)
	{
		double signOfDerivPrev = Double.NaN;
		double signOfDerivNext;
		
		for(int i = 0; i < 7 ; ++i)
		{
			double funcAtCurX = func.valueAt(startPoint);
			if(Math.abs(funcAtCurX) < epsilon) return startPoint;
			
			// due to the fact only trusted sources call this function => derivation !=0.0+epsilon
			double derivAtCurX = derivate.valueAt(startPoint);
			
			signOfDerivNext = Math.signum(derivAtCurX);
			if(Double.isNaN(signOfDerivPrev))
			{
				signOfDerivPrev = signOfDerivNext;
			}
			else
			{
				if(signOfDerivPrev != signOfDerivNext) return Double.NaN; // derivation change the sign => abort
				else signOfDerivPrev = signOfDerivNext;
			}
			
			//s.
			
			if(Math.abs(derivAtCurX) < epsilon) // derivation is equal to 0.0
			{
				return Double.NaN; // no zero found
			}
			
			startPoint -= funcAtCurX / derivAtCurX;
		}
		return startPoint;
	}
	
	/**
	 * @param period1st
	 * @param period2nd
	 * @return common period of two if existed; NaN if not
	 */
	public static double commonPeriod(double period1st, double period2nd)
	{
        period1st /= Surroundings.getEpsilon();
        period2nd /= Surroundings.getEpsilon();
        double firstPeriodFloor = Math.floor(period1st);
        double secondPeriodFloor = Math.floor(period2nd);
        
        while((Math.abs(period1st - firstPeriodFloor) < Surroundings.getEpsilon()) && 
              (Math.abs(period2nd - secondPeriodFloor) < Surroundings.getEpsilon()))
        {
            period1st /= 10.0;
            firstPeriodFloor = Math.floor(period1st);
            period2nd /= 10.0;
            secondPeriodFloor = Math.floor(period2nd);
        }
        
        period1st *= 10.0;
        period2nd *= 10.0;
        long longPer1 = (long) period1st;
        long longPer2 = (long) period2nd;
        BigInteger b1 = new BigInteger(Long.toString(longPer1));
        BigInteger b2 = new BigInteger(Long.toString(longPer2));
        BigInteger b3 = b1.gcd(b2);
        b3 = b1.multiply(b2).divide(b3);
        long commonPeriod = b3.longValue();
        
        return (commonPeriod * Surroundings.getEpsilon());
	}
}
