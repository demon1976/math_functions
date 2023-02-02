/**
 * 
 */
package concreteFunctionImplementations;

import java.util.HashSet;
import java.util.Set;

/**
 * @author dimaw
 *
 */
public class Abs extends FunctionOneParameter
{

    /**
     * 
     */
    private Abs(OneVariableFunction arg)
    {
        m_func = arg;
    }
    
    /**
     * @param arg
     * @return instance
     */
    public static OneVariableFunction getInstance(OneVariableFunction arg)
    {
        if(arg instanceof Abs) return arg;
        return new Abs(arg);
    }

    @Override
    public double valueAt(double x)
    {
        double val = m_func.valueAt(x);
        if(val < 0.0) return (-val);
        return val;
    }

    @Override
    public OneVariableFunction getDerivation()
    {
        // TODO das stimmt so nicht! Man braucht Funktion, die abschnittsweise definiert ist
        return m_func.getDerivation();
        //throw new NoSuchMethodException("undefined for Abs");
    }
    

    
    @Override
    public Set<Double> getZeroOfFunc()
    {
        return m_func.getZeroOfFunc();
    }
        
    /**
     * Try to find any zero of function between borders
     * If one zero was found then it will be returned. By polynomials 
     * it will be tried to find all zeros of function. 
     * @param leftBound
     * @param rightBound
     * @return array of zeros of function or null when nothing found
     */
    @Override
    public Set<Double> getZeroOfFunc(double leftBound, double rightBound)
    {
        Set<Double> tmp = m_func.getZeroOfFunc();
        Set<Double> result = new HashSet<>();
        for(Double d : tmp)
        {
            double dv = d.doubleValue();
            if((dv >= leftBound) && (dv <= rightBound)) result.add(d);
        }
        
        return result;
    }
    
    @Override
    public boolean equals(Object arg)
    {
        if(null == arg)                 return false;
        if(this == arg)                 return true;
        if(false == arg instanceof Abs) return false;
        
        Abs argAbs = (Abs) arg;
        if(m_func.equals(argAbs.m_func)) return true;
        
        return false;
    }
    
    @Override
    public int hashCode()
    {
        return (-m_func.hashCode());
    }

}
