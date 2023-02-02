/**
 * 
 */
package concreteFunctionImplementations;

/**
 * @author dimaw
 *
 */
public abstract class FunctionOneParameter extends OneVariableFunction
{
    protected OneVariableFunction m_func = null;
    
    OneVariableFunction getFunctionArgument()
    {
        return m_func;
    }
}
