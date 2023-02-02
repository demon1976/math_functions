/**
 * 
 */
package Function;

import java.util.HashMap;

import concreteFunctionImplementations.OneVariableFunction;
import concreteFunctionImplementations.Constant;

/**
 * @author dimaw
 *
 */
public final class FunctionWithOneVariable implements Function
{
	
	private OneVariableFunction m_func;
	private Function m_deriv;
	private String m_variableName;

	/**
	 * @param func 
	 * @param varName 
	 * 
	 */
	public FunctionWithOneVariable(OneVariableFunction func, String varName)
	{
		if(null == func) throw new IllegalArgumentException("function shall not be null");
		// if((null == varName) || varName.isEmpty() || varName.isBlank()) throw new IllegalArgumentException("variable name shall not be null");
		
		m_func = func;
		m_variableName = varName;
		m_deriv = null;
	}

	@SuppressWarnings({ "boxing" })
	@Override
	public double valueAt(HashMap<String, Double> p_list)
	{
		if(p_list.containsKey(m_variableName))
		{
			return m_func.valueAt(p_list.get(m_variableName));
		}
		
        return Double.NaN;
	}

	@Override
	public Function getDerivation(String var)
	{
		if(m_variableName.equals(var))
		{
			if(null == m_deriv)
			{
				OneVariableFunction derivarion = m_func.getDerivation();
				if(derivarion instanceof Constant)
				{
					m_deriv = ((Constant) derivarion); 
				}
				else
				{
					m_deriv = new FunctionWithOneVariable(derivarion, m_variableName);
				}
			}
			return m_deriv;
		}

        return Constant.ZERO;
	}

}
