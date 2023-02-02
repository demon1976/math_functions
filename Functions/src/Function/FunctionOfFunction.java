/**
 * 
 */
package Function;

import java.util.HashMap;

import concreteFunctionImplementations.OneVariableFunction;

/**
 * @author dimaw
 *
 */
public final class FunctionOfFunction implements Function 
{
	private OneVariableFunction m_outer;
	private Function m_inner;

	/**
	 * @param outer 
	 * @param inner 
	 * 
	 */
	public FunctionOfFunction(OneVariableFunction outer, Function inner) 
	{
		m_outer = outer;
		m_inner = inner;
	}

	/**
	 * @param p_list
	 * @return value of function 
	 */
	@Override
	public double valueAt(HashMap<String, Double> p_list) 
	{
		return m_outer.valueAt(m_inner.valueAt(p_list));
	}

	@Override
	public Function getDerivation(String p_var) 
	{
		return new MultiplikationGeneral(new FunctionOfFunction(m_outer.getDerivation(), m_inner),
								m_inner.getDerivation(p_var));
	}



}
