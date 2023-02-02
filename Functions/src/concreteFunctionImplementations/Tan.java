/**
 * 
 */
package concreteFunctionImplementations;

import Function.Surroundings;

/**
 * @author dimaw
 *
 */
public final class Tan extends FunctionOneParameter
{
	/**
	 * 
	 */
	private Tan(OneVariableFunction arg)
	{
        m_func = arg;
	}
	
	/**
	 * @param arg
	 * @return an instance which means the cos of argument
	 */
	public static OneVariableFunction getInstance(OneVariableFunction arg)
	{
        if(arg instanceof ArcTan)
        {
            ArcTan argArcTan = (ArcTan) arg;
            return argArcTan.getFunctionArgument();
        }
        return new Tan(arg);
	}

	@Override
	public double valueAt(double x)
	{
		return Math.tan(m_func.valueAt(x));
	}

	@Override
	public OneVariableFunction getDerivation()
	{
		if(null == m_derivation)
		{
		    m_derivation = Cos.getInstance(m_func);
		    m_derivation = Multiplication.getInstance(m_derivation, m_derivation);
			m_derivation = Division.getInstance(Constant.ONE,
			                                    m_derivation);
			m_derivation = Multiplication.getInstance(m_derivation, m_func.getDerivation());
		}

		return m_derivation;
	}

    @Override
    public boolean isPeriodic()
    {
        if(m_func.isLinear()) return true;
        if(m_func.isPeriodic())
        {
            if(Double.isNaN(Surroundings.commonPeriod(m_func.getPeriod(), Math.PI * 2.0))) return false;
            return true;
        }
        return false;
    }
    
    @Override
    public double getPeriod()
    {
        if(isPeriodic())
        {
            return Surroundings.commonPeriod(m_func.getPeriod(), Math.PI);
        }

        return Double.NaN;
    }
    
    @Override
    public boolean equals(Object arg)
    {
        if(null == arg)                 return false;
        if(this == arg)                 return true;
        if(false == arg instanceof Tan) return false;

        Tan argTan = (Tan) arg;
        if(m_func.equals(argTan.m_func)) return true;

        return false;
    }
    
    @Override
    public int hashCode()
    {
        return (m_func.hashCode()<<16 + 8230);
    }

}
