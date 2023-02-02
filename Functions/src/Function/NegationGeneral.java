/**
 * 
 */
package Function;

import java.util.HashMap;

/**
 * @author dimaw
 *
 */
public final class NegationGeneral implements Function 
{
	private Function m_arg;

	/**
	 * @param p_arg 
	 * 
	 */
	public NegationGeneral(Function p_arg) 
	{
		m_arg = p_arg;
	}

	@Override
	public double valueAt(HashMap<String, Double> p_list) 
	{
		return (-(m_arg.valueAt(p_list)));
	}

	@Override
	public Function getDerivation(String p_var) 
	{
		return new NegationGeneral(m_arg.getDerivation(p_var));
	}

}
