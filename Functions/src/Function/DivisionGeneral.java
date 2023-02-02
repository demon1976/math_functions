/**
 * 
 */
package Function;

import java.util.HashMap;

/**
 * @author dimaw
 *
 */
public class DivisionGeneral implements Function 
{
	private Function m_toDivide;
	private Function m_divisor;

	/**
	 * @param p_toDivide 
	 * @param p_divisor 
	 * 
	 */
	public DivisionGeneral(Function p_toDivide, Function p_divisor) 
	{
		m_toDivide = p_toDivide/*.copy()*/;
		m_divisor = p_divisor/*.copy()*/;
	}

	@Override
	public double valueAt(HashMap<String, Double> p_list) 
	{
		double divisor = m_divisor.valueAt(p_list);
		if(Math.abs(divisor) < Surroundings.getEpsilon())
		{
			throw new ArithmeticException("division by zero of [" + m_divisor + "] in " + p_list);  //$NON-NLS-1$//$NON-NLS-2$
		}
		return (m_toDivide.valueAt(p_list) / divisor);
	}

	@Override
	public Function getDerivation(String p_var) 
	{
		return new DivisionGeneral(new SubtrcationGeneral(new MultiplikationGeneral(m_toDivide.getDerivation(p_var), m_divisor), 
												new MultiplikationGeneral(m_toDivide, m_divisor.getDerivation(p_var))), 
							new MultiplikationGeneral(m_divisor, m_divisor));
	}

	/*
	@SuppressWarnings("unqualified-field-access")
	@Override
	public Function copy() 
	{
		return new DivisionGeneral(m_toDivide.copy(), m_divisor.copy());
	}*/


}
