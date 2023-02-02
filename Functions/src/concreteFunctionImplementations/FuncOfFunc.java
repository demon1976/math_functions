/**
 * 
 */
package concreteFunctionImplementations;

/**
 * @author dimaw
 *
 */
public final class FuncOfFunc extends OneVariableFunction
{
	private OneVariableFunction m_outer;
	private OneVariableFunction m_inner;

	/**
	 * @param outer 
	 * @param inner 
	 * 
	 */
	private FuncOfFunc(OneVariableFunction outer, OneVariableFunction inner)
	{
		m_outer = outer;
		m_inner = inner;
	}
	
	/**
	 * @param outer
	 * @param inner
	 * @return a function which computes the value of inner and takes the result as an argument
	 * for the outer function. The result of outer is the result of of complete function. @see valueAt
	 */
	public static OneVariableFunction getInstance(OneVariableFunction outer, OneVariableFunction inner)
	{
	    if(((outer instanceof ArcCos)   && (inner instanceof Cos))      ||
	       ((outer instanceof Cos)      && (inner instanceof ArcCos))   ||
           ((outer instanceof ArcSin)   && (inner instanceof Sin))      ||
           ((outer instanceof Sin)      && (inner instanceof ArcSin))   ||
           ((outer instanceof ArcTan)   && (inner instanceof Tan))      ||
           ((outer instanceof Tan)      && (inner instanceof ArcTan))   ||
           
	       ((outer instanceof ArCosH)   && (inner instanceof CosH))     ||
           ((outer instanceof CosH)     && (inner instanceof ArCosH))   ||
           ((outer instanceof ArSinH)   && (inner instanceof SinH))     ||
           ((outer instanceof SinH)     && (inner instanceof ArSinH))   ||
           ((outer instanceof ArTanH)   && (inner instanceof TanH))     ||
           ((outer instanceof TanH)     && (inner instanceof ArTanH))   ||
           
           ((outer instanceof Exp)      && (inner instanceof Ln))       ||
           ((outer instanceof Ln)       && (inner instanceof Exp))      ||
           
           ((outer instanceof Negation) && (inner instanceof Negation)) ||
           ((outer instanceof Inverse)  && (inner instanceof Inverse)))
	    {
	        FunctionOneParameter func = (FunctionOneParameter) inner;
	        return func.getFunctionArgument();
	    }
	    
	    return new FuncOfFunc(outer, inner);
	}

	@Override
	public double valueAt(double x)
	{
		return m_outer.valueAt(m_inner.valueAt(x));
	}

	@Override
	public OneVariableFunction getDerivation()
	{
		if(null == m_derivation)
		{
			m_derivation = Multiplication.getInstance(new FuncOfFunc(m_outer.getDerivation(), m_inner),
											 m_inner.getDerivation()) ;
		}
		
		return m_derivation;
	}
    
    @Override
    public boolean equals(Object arg)
    {
        if(null == arg)                         return false;
        if(this == arg)                         return true;
        if(false == arg instanceof FuncOfFunc)  return false;

        FuncOfFunc argFunc = (FuncOfFunc) arg;
        if(!m_outer.equals(argFunc.m_outer)) return false;
        if(!m_inner.equals(argFunc.m_inner)) return false;

        return true;
    }
    
    @Override
    public int hashCode()
    {
        return (m_outer.hashCode()<<16 + m_inner.hashCode());
    }

}
