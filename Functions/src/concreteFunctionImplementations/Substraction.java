/**
 * 
 */
package concreteFunctionImplementations;

import Function.Surroundings;

/**
 * @author dimaw
 *
 */
public final class Substraction extends OneVariableFunction
{
	private OneVariableFunction m_substractFrom;
	private OneVariableFunction m_toSubstract;

	/**
	 * @param from 
	 * @param toSubstract 
	 * 
	 */
	private Substraction(OneVariableFunction from, OneVariableFunction toSubstract)
	{
		m_substractFrom = from;
		m_toSubstract   = toSubstract;
	}
	
   /**
 * @param from
 * @param toSubstract
 * @return difference
 */
	public static OneVariableFunction getInstance(OneVariableFunction from, OneVariableFunction toSubstract)
    {
        if(from instanceof Constant)
        {
            double valOfConst = from.valueAt(0.0);
            if(toSubstract instanceof Constant) return Constant.getInstance(valOfConst - toSubstract.valueAt(0.0));
            else if(Math.abs(valOfConst) < Surroundings.getEpsilon()) return Negation.getInstance(toSubstract);
        }
        else if(toSubstract instanceof Constant)
        {
            double valOfConst = toSubstract.valueAt(0.0);
            if(Math.abs(valOfConst) < Surroundings.getEpsilon()) return from;
        }
        
        return new Substraction(from, toSubstract);
    }

	@Override
	public double valueAt(double x)
	{
		return (m_substractFrom.valueAt(x) - m_toSubstract.valueAt(x));
	}

	@Override
	public OneVariableFunction getDerivation()
	{
		if(null == m_derivation)
		{
			m_derivation = getInstance(m_substractFrom.getDerivation(), m_toSubstract.getDerivation());
		}

		return m_derivation;
	}

    @Override
    public boolean isPeriodic()
    {
        return (m_substractFrom.isPeriodic() && m_toSubstract.isPeriodic());
    }
    
    @Override
    public double getPeriod()
    {
        if(isPeriodic())
        {
            return Surroundings.commonPeriod(m_substractFrom.getPeriod(), m_toSubstract.getPeriod());
        }
        else
        {
            return Double.NaN;
        }
    }
    
    @Override
    public boolean isLinear()
    {
        return (m_substractFrom.isLinear() && m_toSubstract.isLinear());
    }
    
    @Override
    public boolean equals(Object arg)
    {
        if(null == arg) return false;
        if(this == arg) return true;
        if(false == arg instanceof Substraction) return false;
        
        Substraction argSub = (Substraction) arg;
        if(false == m_substractFrom.equals(argSub.m_substractFrom)) return false; 
        if(false == m_toSubstract.equals(argSub.m_toSubstract))     return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        return ((m_substractFrom.hashCode()<<16) + m_toSubstract.hashCode());
    }

}
