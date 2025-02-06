package Gates;
import Gate.Fils;
import Gate.Gate;
import Gate.GatesShapes;
import javafx.scene.Group;
import javafx.scene.shape.Shape;
public class Variable extends Gate {
    public Variable(boolean value, Fils fils, Group layout){
        super("VARIABLE", fils, layout);
        setIHaveOutput(true);
        Shape sh = GatesShapes.variable();
        sh.setOnMouseClicked(e -> setOutput(!getOutput()));
        setShape(sh);
        addShapeToGroup();
        addPoints();
        
        setOutput(value);
    }

    public Variable(Fils fils, Group layout){
        this(false, fils, layout);
    }

    @Override
    public void evaluateOutput(){}
}