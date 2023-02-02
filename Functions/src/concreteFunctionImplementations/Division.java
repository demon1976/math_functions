/**
 * 
 */
package concreteFunctionImplementations;

import Function.Surroundings;

/**
 * @author dimaw
 *
 */
public final class Division extends OneVariableFunction
{
	private OneVariableFunction m_toDivide;
	private OneVariableFunction m_divisor;

	/**
	 * @param toDivide 
	 * @param divisor 
	 * 
	 */
	private Division(OneVariableFunction toDivide, OneVariableFunction divisor)
	{
		m_toDivide = toDivide;
		m_divisor = divisor;
	}
	
    /**
     * @param toDivide
     * @param divisor
     * @return the division
     */
    public static OneVariableFunction getInstance(OneVariableFunction toDivide, OneVariableFunction divisor)
    {
        if(divisor instanceof Constant)
        {
            double valOfDiv = divisor.valueAt(0.0);
            if(Math.abs(valOfDiv) < Surroundings.getEpsilon()) throw new IllegalArgumentException("divisor is 0");
            
            if(toDivide instanceof Constant)
            {
                double valAbove = toDivide.valueAt(0.0);
                if(Math.abs(valAbove) < Surroundings.getEpsilon()) return Constant.ZERO;
            }
            if(Math.abs(valOfDiv - 1.0) < Surroundings.getEpsilon()) return toDivide; 
            if(Math.abs(valOfDiv + 1.0) < Surroundings.getEpsilon()) return Negation.getInstance(toDivide);
        }
        else if(toDivide instanceof Constant)
        {
            double valAbove = toDivide.valueAt(0.0);
            if(Math.abs(valAbove) < Surroundings.getEpsilon()) return Constant.ZERO;
        }
        
        return new Division(toDivide, divisor);
    }

	@Override
	public double valueAt(double arg)
	{
		double divisor = m_divisor.valueAt(arg);
		if(Math.abs(divisor) < Surroundings.getEpsilon())
		{
			throw new ArithmeticException("division by zero of [" + m_divisor + "] in " + arg);  //$NON-NLS-1$//$NON-NLS-2$
		}
		return (m_toDivide.valueAt(arg) / divisor);
	}

	@Override
	public OneVariableFunction getDerivation()
	{
		if(null == m_derivation)
		{
			m_derivation = getInstance(Substraction.getInstance(Multiplication.getInstance(m_toDivide.getDerivation(),m_divisor),
			        Multiplication.getInstance(m_toDivide, m_divisor.getDerivation())),
			        Multiplication.getInstance(m_divisor, m_divisor));
		}
		
		return m_derivation;
	}

    @Override
    public boolean isPeriodic()
    {
        return (m_toDivide.isPeriodic() && m_divisor.isPeriodic());
    }
    
    @Override
    public double getPeriod()
    {
        if(isPeriodic())
        {
            return Surroundings.commonPeriod(m_toDivide.getPeriod(), m_divisor.getPeriod());
        }
        else
        {
            return Double.NaN;
        }
    }
    
    @Override
    public boolean isLinear()
    {
        if(false == m_divisor instanceof Constant) return false;
        if(m_toDivide instanceof VarConstPower) return true;

        if(m_toDivide instanceof Polynomial)
        {
            Polynomial poly = (Polynomial) m_toDivide;
            if(poly.degree() <= 1) return true;
            else return false;
        }
        else
        {
            return false;
        }
    }
    
    @Override
    public boolean equals(Object arg)
    {
        if(null == arg) return false;
        if(this == arg) return true;
        if(false == arg instanceof Division) return false;
        
        Division argDiv = (Division) arg;
        if(false == m_toDivide.equals(argDiv.m_toDivide)) return false;
        if(false == m_divisor.equals(argDiv.m_divisor)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        return ((m_toDivide.hashCode()<<16) + m_divisor.hashCode());
    }

}
