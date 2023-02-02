/**
 * 
 */
package concreteFunctionImplementations;

import Function.Surroundings;

/**
 * @author dimaw
 *
 */
public final class Multiplication extends OneVariableFunction 
{
	private OneVariableFunction m_first;
	private OneVariableFunction m_second;

	/**
	 * @param first 
	 * @param second 
	 * 
	 */
	private Multiplication(OneVariableFunction first, OneVariableFunction second)
	{
		m_first = first;
		m_second = second;
	}
	
   /**
     * @param first
     * @param second
     * @return multiplication of both functions
     */
    public static OneVariableFunction getInstance(OneVariableFunction first, OneVariableFunction second)
    {
        if(first instanceof Constant)
        {
            double valOfConst1st = first.valueAt(0.0);
            if(Math.abs(valOfConst1st) < Surroundings.getEpsilon()) return Constant.ZERO;
            if(Math.abs(valOfConst1st - 1.0) < Surroundings.getEpsilon()) return second; 
            if(Math.abs(valOfConst1st + 1.0) < Surroundings.getEpsilon()) return Negation.getInstance(second);
            
            if(second instanceof Constant)
            {
                double valOfConst2nd = second.valueAt(0.0);
                if(Math.abs(valOfConst2nd) < Surroundings.getEpsilon()) 
                {
                    return Constant.ZERO;
                }
                if(Math.abs(valOfConst2nd - 1.0) < Surroundings.getEpsilon())
                {
                    return first;
                }
                if(Math.abs(valOfConst2nd + 1.0) < Surroundings.getEpsilon()) 
                {
                    return Constant.getInstance(- first.valueAt(0.0));
                }

                return Constant.getInstance(valOfConst1st * valOfConst2nd);
            }
        }
        else if(second instanceof Constant)
        {
            double valOfConst2nd = second.valueAt(0.0);
            if(Math.abs(valOfConst2nd) < Surroundings.getEpsilon())       return Constant.ZERO;
            if(Math.abs(valOfConst2nd - 1.0) < Surroundings.getEpsilon()) return first;
            if(Math.abs(valOfConst2nd + 1.0) < Surroundings.getEpsilon()) return Negation.getInstance(first);
        }
        
        return new Multiplication(first, second);
    }

	@Override
	public double valueAt(double x)
	{
		return (m_first.valueAt(x) * m_second.valueAt(x));
	}

	@Override
	public OneVariableFunction getDerivation()
	{
		if(null == m_derivation)
		{
			m_derivation = Addition.getInstance(getInstance(m_first.getDerivation(), m_second), 
			                                    getInstance(m_first, m_second.getDerivation()));
		}

		return m_derivation;
	}

    @Override
    public boolean isPeriodic()
    {
        return (m_first.isPeriodic() && m_second.isPeriodic());
    }
    
    @Override
    public double getPeriod()
    {
        if(isPeriodic())
        {
            return Surroundings.commonPeriod(m_first.getPeriod(), m_second.getPeriod());
        }

        return Double.NaN;
    }
    
    /**
     * either both are constants or one is constant and other Plain var, 
     * vorConstPower with power 1, or polynomial with at most 2 coeffs
     */
    @Override
    public boolean isLinear()
    {
        if(m_first instanceof Constant)
        {
            if((m_second instanceof Constant) || (m_second instanceof PlainVar)) return true;
            if(m_second instanceof VarConstPower)
            {
                VarConstPower tmp = (VarConstPower) m_second;
                // if power is 1.0
                if(Math.abs(tmp.getPower() - 1.0) < Surroundings.getEpsilon()) return true;
                return false;
            }
            if(m_second instanceof Polynomial)
            {
                Polynomial tmp = (Polynomial) m_second;
                // so polynomial is at most linear
                if(tmp.degree() < 2) return true;
                return false;
            }
            return false;
        }
        // now the same for exchange 1st and 2nd
        if(m_second instanceof Constant)
        {
            if((m_first instanceof Constant) || (m_first instanceof PlainVar)) return true;
            if(m_first instanceof VarConstPower)
            {
                VarConstPower tmp = (VarConstPower) m_first;
                // if power is 1.0
                if(Math.abs(tmp.getPower() - 1.0) < Surroundings.getEpsilon()) return true;
                return false;
            }
            if(m_first instanceof Polynomial)
            {
                Polynomial tmp = (Polynomial) m_first;
                // so polynomial is at most linear
                if(tmp.degree() < 2) return true;
                return false;
            }
            return false;
        }
        
        return false;
    }
    
    @Override
    public boolean equals(Object arg)
    {
        if(null == arg)                             return false;
        if(this == arg)                             return true;
        if(false == arg instanceof Multiplication)  return false;
        
        Multiplication argMul = (Multiplication) arg;
        if(((m_first.equals(argMul.m_first)) && (m_second.equals(argMul.m_second))) ||
           ((m_first.equals(argMul.m_second)) && (m_second.equals(argMul.m_first)))) 
        {
            return true;
        }
        
        return false;
    }
    
    @Override
    public int hashCode()
    {
        return ((m_first.hashCode()<<16) + m_second.hashCode());
    }

}
