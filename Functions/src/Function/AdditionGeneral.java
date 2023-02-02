/**
 * 
 */
package Function;

import java.util.HashMap;

/**
 * @author dimaw
 *
 */
public final class AdditionGeneral implements Function 
{
	private Function m_first;
	private Function m_second;
	
	/**
	 * @param firstArg of sum
	 * @param secondArg of sum
	 * 
	 */
	public AdditionGeneral(Function firstArg, Function secondArg) 
	{
		m_first = firstArg/*.copy()*/;
		m_second = secondArg/*.copy()*/;
	}

	@Override
	public Function getDerivation(String p_var) 
	{
		return new AdditionGeneral(m_first.getDerivation(p_var), m_second.getDerivation(p_var));
	}

	/*
	@SuppressWarnings("unqualified-field-access")
	@Override
	public Function copy() 
	{
		return new AdditionGeneral(m_first.copy(), m_second.copy());
	}
	*/

	@Override
	public double valueAt(HashMap<String, Double> p_list) 
	{
		return m_first.valueAt(p_list) + m_second.valueAt(p_list);
	}
	
	

}
