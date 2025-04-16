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
	private Circuit circuit;

	public Bascule_RS(Circuit circuit, Pane layout, double x, double y) {
		super("BASCULE RS", 2, circuit, layout, x, y);
		this.circuit = circuit;

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
		QuadBool R = inputs[0].getOutput();
		QuadBool S = inputs[1].getOutput();

		QuadBool nextQ;

		// Propagation d'erreur en priorité
		if (R == QuadBool.ERROR || S == QuadBool.ERROR) {
			nextQ = QuadBool.ERROR;
		} else if (R == QuadBool.TRUE && S == QuadBool.TRUE) {
			nextQ = QuadBool.ERROR; // état interdit
		} else if (S == QuadBool.TRUE) {
			nextQ = QuadBool.TRUE;
		} else if (R == QuadBool.TRUE) {
			nextQ = QuadBool.FALSE;
		} else if (S == QuadBool.NOTHING || R == QuadBool.NOTHING) {
			nextQ = current; // garder l'état
		} else {
			nextQ = current; // aucun changement
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

	@Override
	public void onRelease() {
		super.onRelease();
		for (Fils input : getInputs()) {
			input.onRelease();
		}
	}
}
