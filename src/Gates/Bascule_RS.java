package Gates;

import Circuit.Circuit;
import Circuit.Fils;
import Circuit.Gate;
import Circuit.QuadBool;
import Circuit.Unity;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class Bascule_RS extends Gate {


    private QuadBool current = QuadBool.FALSE;  // représente Qn

    public Bascule_RS(Circuit circuit, Pane layout, double x, double y) {
        super("BASCULE RS", 2, circuit, layout, x, y);
        setOutputValue(QuadBool.FALSE); // Explicitly set initial output
        
        Shape shape = getShape();
        double shapeX = shape.getLayoutX();
        double shapeY = shape.getLayoutY();
        double shapeWidth = shape.getLayoutBounds().getWidth();
        double shapeHeight = shape.getLayoutBounds().getHeight();

        double centerX = shapeX + shapeWidth / 2 -1;
        double centerY = shapeY + shapeHeight / 2-1;

        setText("R  S", centerX , centerY);
    }

    @Override
    public void evaluateOutput() {
        Fils[] inputs = getInputs();
        
        // Handle case where inputs aren't connected yet
        if (inputs[0] == null || inputs[1] == null) {
            setOutput(current);
            return;
        }

        QuadBool R = inputs[0].getOutput();
        QuadBool S = inputs[1].getOutput();

        QuadBool nextQ;

        // Propagation d'erreur en priorité
        if (R == QuadBool.ERROR || S == QuadBool.ERROR) {
            nextQ = QuadBool.ERROR;
        } 
        // État interdit
        else if (R == QuadBool.TRUE && S == QuadBool.TRUE) {
            nextQ = QuadBool.ERROR;
        } 
        // Set state
        else if (S == QuadBool.TRUE && R != QuadBool.TRUE) {
            nextQ = QuadBool.TRUE;
        } 
        // Reset state
        else if (R == QuadBool.TRUE && S != QuadBool.TRUE) {
            nextQ = QuadBool.FALSE;
        } 
        // Maintain state for NOTHING inputs or both FALSE
        else {
            nextQ = current;
        }

        setOutput(nextQ);
        current = nextQ;
        setIHaveOutput(true);
    }
	@Override
	public void addPoints(Circuit circuit) {
		Fils[] inputs = getInputs();
		Shape shape = getShape();
		int distance = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxX());
		Fils output = new Fils(Unity.tranformDoubleToInt(shape.getLayoutX() + distance / 2),
				Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxY() + shape.getLayoutY()), 
				circuit, null, this, true);
		setOutputFils(output);

		if (inputs != null) {
			distance /= (inputs.length + 1);
			for (int i = 1; i < inputs.length + 1; i++) {
				inputs[i - 1] = new Fils(Unity.tranformDoubleToInt(shape.getLayoutX() + i * distance),
						Unity.tranformDoubleToInt(shape.getLayoutY()), circuit, null, null, true);
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

	@Override
	public void onRelease() {
		super.onRelease();
		for (Fils input : getInputs()) {
			input.onRelease();
		}
	}
}
