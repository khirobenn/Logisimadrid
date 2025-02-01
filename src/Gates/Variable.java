package Gates;
import Gate.Fils;
import Gate.Gate;
import javafx.scene.Group;
public class Variable extends Gate {
    public Variable(boolean value, Fils fils, Group layout){
        super("VARIABLE", fils, layout);
        setOutput(value);
        setIHaveOutput(true);
    }

    public Variable(Fils fils, Group layout){
        this(false, fils, layout);
    }

    @Override
    public void evaluateOutput(){}
}