/**
 * 
 */
package concreteFunctionImplementations;

import Function.Surroundings;

/**
 * @author dimaw
 *
 */
public class GeneralLog extends OneVariableFunction
{
    private OneVariableFunction m_base;
    private OneVariableFunction m_value;
    
    /**
     * @return base of this func
     */
    public OneVariableFunction getBase()
    {
        return m_base;
    }
    /**
     * @return power of this func
     */
    public OneVariableFunction getValue()
    {
        return m_value;
    }

    private GeneralLog(OneVariableFunction base, OneVariableFunction val)
    {
        m_base = base;
        m_value = val;
    }
    
    /**
     * @param base
     * @param value
     * @return a function for this general log
     */
    public static OneVariableFunction getInstance(OneVariableFunction base, OneVariableFunction value)
    {
        if(base instanceof Constant)
        {
            double valOfConst = base.valueAt(0.0);
            if((valOfConst < 0.0) || (Math.abs(valOfConst) < Surroundings.getEpsilon()))
            {
                throw new IllegalArgumentException("base of log is less or equal to 0: " + valOfConst);
            }
            else if(Math.abs(valOfConst - Math.E) < Surroundings.getEpsilon())
            {
                if(value instanceof Exp)
                {
                    Exp expArg = (Exp) value;
                    return expArg.getFunctionArgument();
                }
                else if(value instanceof FuncPowerFunc)
                {
                    FuncPowerFunc funcPowerArg = (FuncPowerFunc) value;
                    if(funcPowerArg.getBase().equals(Constant.E))
                    {
                        return funcPowerArg.getPower();
                    }
                    
                    return Multiplication.getInstance(funcPowerArg.getPower(), Ln.getInstance(funcPowerArg.getBase()));
                }
                
                return Ln.getInstance(value);
            }
        }
        else if(value instanceof FuncPowerFunc)
        {
            FuncPowerFunc funcPowerArg = (FuncPowerFunc) value;
            if(base.equals(funcPowerArg.getBase())) return funcPowerArg.getPower();
        }
        
        return new GeneralLog(base, value);
    }

    @Override
    public double valueAt(double x)
    {
        return (Math.log(m_value.valueAt(x)) / Math.log(m_base.valueAt(x)));
    }

    @Override
    public OneVariableFunction getDerivation()
    {
        if(null == m_derivation)
        {
            m_derivation = Division.getInstance(Ln.getInstance(m_value), Ln.getInstance(m_base));
            m_derivation = m_derivation.getDerivation();
        }
        
        return m_derivation;
    }
    
    @Override
    public boolean equals(Object arg)
    {
        if(null == arg)                  return false;
        if(this == arg)                  return true;
        if(false == arg instanceof GeneralLog) return false;

        GeneralLog argGenLog = (GeneralLog) arg;
        if(!m_base.equals(argGenLog.m_base)) return false;
        if(!m_value.equals(argGenLog.m_value)) return false;

        return true;
    }
    
    @Override
    public int hashCode()
    {
        return ((m_base.hashCode()<<16) + m_value.hashCode());
    }

}
