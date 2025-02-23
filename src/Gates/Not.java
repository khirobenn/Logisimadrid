package Gates;

import Gate.Fils;
import Gates.NotQuad;
import Gate.Gate;
import Gate.GatesShapes;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class Not extends Gate {
    public Not(Fils fils, Pane layout, double x, double y){
        super("NOT", 1, fils, layout, x, y);
    }

    @Override
    public void evaluateOutput(){
        setOutput(NotQuad.Not(getInputs()[0].getOutput()));
        setIHaveOutput(true);
    }
}