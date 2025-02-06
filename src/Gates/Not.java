package Gates;

import Gate.Fils;
import Gate.Gate;
import Gate.GatesShapes;
import javafx.scene.Group;

public class Not extends Gate {
    public Not(Fils fils, Group layout){
        super("NOT", 1, fils, layout);
        setShape(GatesShapes.notShape());
        addShapeToGroup();
        addPoints();
    }

    @Override
    public void evaluateOutput(){
        setOutput(!(getInputs()[0].getOutput()));
        setIHaveOutput(true);
    }
}