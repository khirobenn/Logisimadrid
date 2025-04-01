package Gates;

import Circuit.Circuit;
import Circuit.Gate;
import Circuit.QuadBool;
import Circuit.Unity;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class OutputGate extends Gate{
    
    public OutputGate(Circuit circuit, Pane layout, double x, double y){
        super("OUTPUT", 1, circuit, layout, x, y);
        Shape sh = new Rectangle(2*Unity.x, 2*Unity.y);
        sh.setFill(Color.TRANSPARENT);
        sh.setStroke(Color.BLACK);
        sh.setStrokeWidth(3);
        setShape(sh);
        addShapeToGroup();
        sh.setLayoutX(x);
        sh.setLayoutY(y);
        sh.setOnMouseClicked(e -> circuit.setSelectedGate(this));
        setText("U");
        addPoints(circuit);
        setOutputValue(QuadBool.NOTHING);
        hideOutput();
    }

    @Override
    public void evaluateOutput(){
        setOutput(getInputs()[0].getOutput());
    }
}
