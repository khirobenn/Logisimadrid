/*TODO pareil que pour EvenParityGate */

package Circuit;

import javafx.scene.layout.Pane;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class OddParityGate extends Gate {
    private int numInputs;
    private Circuit circuit;

    public OddParityGate(Circuit circuit, Pane layout, double x, double y, int numInputs) {
        super("ODDPARITY", numInputs, circuit, layout, x, y);
        this.numInputs = numInputs;
        this.circuit = circuit;

        Shape shape = getShape();
        int distance = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxY());

        setText("2k+1", shape.getLayoutBounds().getMaxX() / 2 + shape.getLayoutX(),
                shape.getLayoutBounds().getMaxY() / 2 + shape.getLayoutY());
    }

    @Override
    public void evaluateOutput() {
        Fils[] inputs = getInputs();
        QuadBool result = QuadBool.FALSE;
        
        for (Fils input : inputs) {
            QuadBool value = input.getOutput();
            if (value == QuadBool.ERROR) {
                setOutput(QuadBool.ERROR);
                return;
            } else if (value == QuadBool.NOTHING) {
                setOutput(QuadBool.NOTHING);
                return;
            }

            result = xor(result, value);
        }

        setOutput(result);  
        setIHaveOutput(true);
    }

    private QuadBool xor(QuadBool a, QuadBool b) {
        return (a == b) ? QuadBool.FALSE : QuadBool.TRUE;  
    }

    @Override
    public void addPoints(Circuit circuit) {
        Fils[] inputs = getInputs();
        Shape shape = getShape();
        int distance = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxX());
        Fils output = new Fils(Unity.tranformDoubleToInt(shape.getLayoutX() + distance / 2),
                               Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxY() + shape.getLayoutY()), 
                               circuit, null, this);
        setOutputFils(output);

        if (inputs != null) {
            distance /= (inputs.length + 1);
            for (int i = 1; i < inputs.length + 1; i++) {
                inputs[i - 1] = new Fils(Unity.tranformDoubleToInt(shape.getLayoutX() + i * distance),
                                         Unity.tranformDoubleToInt(shape.getLayoutY()), circuit, null, null);
            }
        }
    }

    @Override
    public void updatePoints() {
        updateOnePoint(-1, false, true);
        if (getInputs() != null) {
            for (int i = 1; i < getInputs().length + 1; i++) {
                updateOnePoint(i, true, false);
            }
        }
    }

    // Override onRelease() similar to the multiplier, to handle input/output releases
    @Override
    public void onRelease() {
        super.onRelease();
        for (Fils input : getInputs()) {
            input.onRelease();
        }
    }
}
