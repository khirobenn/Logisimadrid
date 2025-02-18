package Gates;
import Gate.Fils;
import Gate.Gate;
import Gate.GatesShapes;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
public class Variable extends Gate {
    public Variable(boolean value, Fils fils, Pane layout, double x, double y){
        super("VARIABLE", fils, layout);
        setIHaveOutput(true);
        Shape sh = GatesShapes.variable();
        sh.setOnMouseClicked(e -> fils.eval(this));
        setShape(sh);
        addShapeToGroup();
        sh.setLayoutX(x);
        sh.setLayoutY(y);
        addPoints();
        setOutput(value);
        setText("0");
        getText().setOnMouseClicked(e -> fils.eval(this));
    }

    public Variable(Fils fils, Pane layout, double x, double y){
        this(false, fils, layout, x, y);
    }

    @Override
    public void evaluateOutput(){}
}