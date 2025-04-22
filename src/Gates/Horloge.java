package Gates;

import Circuit.Circuit;
import Circuit.Fils;
import Circuit.Gate;
import Circuit.QuadBool;
import Circuit.Unity;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;


public class Horloge extends Gate{

    private QuadBool lastInput = QuadBool.FALSE;
    private QuadBool current = QuadBool.FALSE;
    Circuit circuit;

    public Horloge(Circuit circuit, Pane layout, double x, double y){
        super("HORLOGE", 1, circuit, layout, x, y);
        
        this.circuit = circuit;
        Shape shape = getShape();
        setText("CLK", shape.getLayoutBounds().getMaxX() / 2 + shape.getLayoutX(),
        shape.getLayoutBounds().getMaxY() / 2 + shape.getLayoutY());
   }
   @Override
   public void evaluateOutput() {
    Fils[] inputs = getInputs();
    QuadBool input = inputs[0].getOutput(); 
    if ( lastInput == QuadBool.FALSE && input == QuadBool.TRUE ) // detection de front montant
    {
        current = NotQuad.Not(current);// changement d'état
    }

    lastInput = input;
    setOutput(current);
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
