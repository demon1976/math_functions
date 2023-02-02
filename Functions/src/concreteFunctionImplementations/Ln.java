/**
 * 
 */
package concreteFunctionImplementations;

/**
 * @author dimaw
 *
 */
public final class Ln extends FunctionOneParameter
{
    /**
     * 
     */
    private Ln(OneVariableFunction arg)
    {
        m_func = arg;
    }
    
    /**
     * @param arg
     * @return an instance which means the cosh of argument
     */
    public static OneVariableFunction getInstance(OneVariableFunction arg)
    {
        if(arg instanceof Exp)
        {
            Ln argExp = (Ln) arg;
            return argExp.getFunctionArgument();
        }
        return new Ln(arg);
    }

	@Override
	public double valueAt(double x) 
	{
		return Math.log(m_func.valueAt(x));
	}

	@Override
	public OneVariableFunction getDerivation()
	{
		if(null == m_derivation)
		{
		    m_derivation = Division.getInstance(Constant.ONE, m_func);
		    m_derivation = Multiplication.getInstance(m_derivation, m_func.getDerivation());
		}
		
		return m_derivation;
	}
    
    @Override
    public boolean equals(Object arg)
    {
        if(null == arg)                return false;
        if(this == arg)                return true;
        if(false == arg instanceof Ln) return false;

        Ln argLn = (Ln) arg;
        if(m_func.equals(argLn.m_func)) return true;

        return false;
    }
    
    @Override
    public int hashCode()
    {
        return (m_func.hashCode()<<7 + 87);
    }

}
