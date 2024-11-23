package Gates;

import Gate.Gate;
import Gate.GatesShapes;

public class Not extends Gate {
    public Not(){
        super("NOT", 1);
        setShape(GatesShapes.notShape());
    }

    @Override
    public void evaluateOutput(){
        setOutput(!(getInputs()[0].getOutput()));
        setIHaveOutput(true);
    }
}