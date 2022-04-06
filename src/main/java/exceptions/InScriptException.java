package exceptions;

/**
 * when executing script
 */
public class InScriptException extends WhileRunCommandException{
    public InScriptException(String msg) {
        super("execute_script", msg);
    }
}
