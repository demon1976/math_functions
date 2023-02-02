/**
 * 
 */
package concreteFunctionImplementations;

import Function.Surroundings;

/**
 * a^x
 * @author dimaw
 *
 */
public final class FuncPowerFunc extends OneVariableFunction
{
	private OneVariableFunction m_base;
	private OneVariableFunction m_power;
	
	/**
	 * @return base of this func
	 */
	public OneVariableFunction getBase()
	{
	    return m_base;
	}
    /**
     * @return power of this func
     */
    public OneVariableFunction getPower()
    {
        return m_power;
    }
    
    private FuncPowerFunc(OneVariableFunction base, OneVariableFunction power)
    {
        m_base  = base;
        m_power = power;
    }

	/**
	 * @param base 
	 * @param power 
	 * @return a func which represents a base function powered to power function
	 * 
	 */
	public static OneVariableFunction getInstance(OneVariableFunction base, OneVariableFunction power)
	{
	    if(base instanceof Constant)
	    {
	        Constant cBase = (Constant) base;
	        if(Math.abs(cBase.valueAt(0.0)) < Surroundings.getEpsilon()) // practical 0.0
	        {
	            if(power instanceof Constant)
	            {
	                Constant cPower = (Constant) power;
	                if(cPower.valueAt(0.0) > 0.0) return Constant.ZERO;
	                else if(Math.abs(cPower.valueAt(0.0)) < Surroundings.getEpsilon()) return Constant.ONE;
	                else throw new ArithmeticException("division by 0");
	            }
	        }
	        else if(Math.abs(cBase.valueAt(0.0) - 1.0) < Surroundings.getEpsilon()) // practical 1.0
            {
	            return Constant.ONE;
            }
            else if(cBase.valueAt(0.0) < 0.0)
            {
                throw new ArithmeticException("negative number is not allowed to power");
            }
	    }
	    
	    if(power instanceof Constant)
	    {
            Constant cPower = (Constant) power;
            if(Math.abs(cPower.valueAt(0.0)) < Surroundings.getEpsilon()) // practical 0.0
            {
                return Constant.ONE;
            }
            else if(Math.abs(cPower.valueAt(0.0) - 1.0) < Surroundings.getEpsilon()) // practical 1.0
            {
                return base;
            }
            else if(Math.abs(cPower.valueAt(0.0) + 1.0) < Surroundings.getEpsilon()) // practical -1.0
            {
                return Division.getInstance(Constant.ONE, base);
            }
	    }

	    return new FuncPowerFunc(base, power);
	}

	@Override
	public double valueAt(double x)
	{
		return Math.pow(m_base.valueAt(x), m_power.valueAt(x));
	}

	@Override
	public OneVariableFunction getDerivation()
	{
		if(null == m_derivation)
		{
			OneVariableFunction lnFx = Ln.getInstance(m_base);
			OneVariableFunction firstInBraces = Multiplication.getInstance(m_power.getDerivation(), lnFx);

			OneVariableFunction secondInBraces = Division.getInstance(m_power, m_base);
			secondInBraces = Multiplication.getInstance(secondInBraces, m_base.getDerivation());

			OneVariableFunction braces = Addition.getInstance(firstInBraces, secondInBraces);

			m_derivation = Multiplication.getInstance(this, braces);
		}
		
		return m_derivation;
	}
    
    @Override
    public boolean equals(Object arg)
    {
        if(null == arg) return false;
        if(this == arg) return true;
        if(false == arg instanceof FuncPowerFunc) return false;
        
        FuncPowerFunc powerRuleArg = (FuncPowerFunc) arg;
        if(false == m_base.equals(powerRuleArg.m_base)) return false;
        if(false == m_power.equals(powerRuleArg.m_power)) return false;

        return true;
    }
    
    @Override
    public int hashCode()
    {
        return ((m_base.hashCode()<<16) + m_power.hashCode());
    }

}
