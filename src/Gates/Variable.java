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
        setText("0", sh.getLayoutBounds().getMaxX() / 2 + sh.getLayoutX(), sh.getLayoutBounds().getMaxY() / 2 + sh.getLayoutY());
        getText().setOnMouseClicked(e -> evaluate(circuit));
        addPoints(circuit);
        setIHaveOutput(true);
        setOutput(value);
    }

    private void evaluate(Circuit circuit){
        circuit.evaluation(this);
        circuit.evaluation(null); // Pour bien calculer les états de fils
    }

    public Variable(Circuit circuit, Pane layout, double x, double y){
        this(QuadBool.FALSE, circuit, layout, x, y);
    }


    @Override
    public void evaluateOutput(){}
}