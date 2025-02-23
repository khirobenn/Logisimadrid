package Gates;

import Circuit.Circuit;
import Circuit.Gate;
import javafx.scene.layout.Pane;

public class Not extends Gate {
    public Not(Circuit circuit, Pane layout, double x, double y){
        super("NOT", 1, circuit, layout, x, y);
    }

    @Override
    public void evaluateOutput(){
        setOutput(NotQuad.Not(getInputs()[0].getOutput()));
        setIHaveOutput(true);
    }
}