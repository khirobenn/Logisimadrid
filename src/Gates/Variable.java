package Gates;
import Circuit.Circuit;
import Circuit.Gate;
import Circuit.GatesShapes;
import Circuit.QuadBool;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
public class Variable extends Gate {
    public Variable(QuadBool value, Circuit circuit, Pane layout, double x, double y){
        super("VARIABLE", circuit, layout);
        Shape sh = GatesShapes.variable();
        sh.setOnMouseClicked(e -> circuit.eval(this));
        setShape(sh);
        addShapeToGroup();
        sh.setLayoutX(x);
        sh.setLayoutY(y);
        sh.setOnMouseClicked(e -> circuit.setSelectedGate(this));
        setText("0");
        getText().setOnMouseClicked(e -> circuit.eval(this));
        addPoints();
        setIHaveOutput(true);
        setOutput(value);
    }

    public Variable(Circuit circuit, Pane layout, double x, double y){
        this(QuadBool.FALSE, circuit, layout, x, y);
    }

    @Override
    public void evaluateOutput(){}
}