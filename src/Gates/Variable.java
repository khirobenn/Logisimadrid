package Gates;
import Gate.Gate;
public class Variable extends Gate {
    public Variable(boolean value){
        super("VARIABLE");
        setOutput(value);
        setIHaveOutput(true);
    }

    public Variable(){
        this(false);
    }

    @Override
    public void evaluateOutput(){}
}