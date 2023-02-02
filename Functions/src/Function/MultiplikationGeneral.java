/**
 * 
 */
package Function;

import java.util.HashMap;

/**
 * @author dimaw
 *
 */
public final class MultiplikationGeneral implements Function 
{
	private Function m_first;
	private Function m_second;
	
	/**
	 * @param firstArg 
	 * @param secondArg 
	 * 
	 */
	public MultiplikationGeneral(Function firstArg, Function secondArg) 
	{
		m_first = firstArg/*.copy()*/;
		m_second = secondArg/*.copy()*/;
	}

	@Override
	public double valueAt(HashMap<String, Double> p_list) 
	{
		return m_first.valueAt(p_list) * m_second.valueAt(p_list);
	}

	@Override
	public Function getDerivation(String p_var) 
	{
		return new AdditionGeneral(new MultiplikationGeneral(m_first.getDerivation(p_var), m_second),
							new MultiplikationGeneral(m_first, m_second.getDerivation(p_var)));
	}

	/*
	@SuppressWarnings("unqualified-field-access")
	@Override
	public Function copy() 
	{
		return new MultiplikationGeneral(m_first.copy(), m_second.copy());
	}*/


}
