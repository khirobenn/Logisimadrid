package Gates;

import Gate.Fils;
import Gate.Gate;
import Gate.GatesShapes;
import javafx.scene.Group;
import javafx.scene.shape.Shape;

public class Not extends Gate {
    public Not(Fils fils, Group layout, double x, double y){
        super("NOT", 1, fils, layout);
        Shape sh = GatesShapes.notShape();
        setShape(sh);
        addShapeToGroup();
        sh.setLayoutX(x);
        sh.setLayoutY(y);
        addPoints();
    }

    @Override
    public void evaluateOutput(){
        setOutput(!(getInputs()[0].getOutput()));
        setIHaveOutput(true);
    }
}