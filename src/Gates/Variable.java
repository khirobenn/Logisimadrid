package Gates;
import Gate.Fils;
import Gate.Gate;
import Gate.GatesShapes;
import javafx.scene.Group;
import javafx.scene.shape.Shape;
public class Variable extends Gate {
    public Variable(boolean value, Fils fils, Group layout, double x, double y){
        super("VARIABLE", fils, layout);
        setIHaveOutput(true);
        Shape sh = GatesShapes.variable();
        sh.setOnMouseClicked(e -> setOutput(!getOutput()));
        setShape(sh);
        addShapeToGroup();
        sh.setLayoutX(x);
        sh.setLayoutY(y);
        addPoints();
        
        setOutput(value);
    }

    public Variable(Fils fils, Group layout, double x, double y){
        this(false, fils, layout, x, y);
    }

    @Override
    public void evaluateOutput(){}
}