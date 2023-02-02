package Function;

import java.util.HashMap;

/**
 * @author dimaw
 *
 */
public final class SubtrcationGeneral implements Function 
{
	private Function m_from;
	private Function m_substraction;

	/**
	 * @param p_from 
	 * @param p_substraction 
	 * 
	 */
	public SubtrcationGeneral(Function p_from, Function p_substraction) 
	{
		m_from = p_from/*.copy()*/;
		m_substraction = p_substraction/*.copy()*/;
	}

	@Override
	public double valueAt(HashMap<String, Double> p_list) 
	{
		return m_from.valueAt(p_list) - m_substraction.valueAt(p_list);
	}

	@Override
	public Function getDerivation(String p_var) 
	{
		return new SubtrcationGeneral(m_from.getDerivation(p_var), m_substraction.getDerivation(p_var));
	}

	/*
	@SuppressWarnings("unqualified-field-access")
	@Override
	public Function copy() 
	{
		return new SubtrcationGeneral(m_from.copy(), m_substraction.copy());
	}*/

}
