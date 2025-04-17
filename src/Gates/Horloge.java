package Gates;

import Circuit.Circuit;
import Circuit.Fils;
import Circuit.Gate;
import Circuit.GatesShapes;
import Circuit.QuadBool;
import com.sun.jdi.ShortValue;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javax.swing.text.StyledEditorKit;


public class Horloge extends Gate{

    private QuadBool lastInput = QuadBool.FALSE;
    private QuadBool current = QuadBool.FALSE;
    Circuit circuit;

    public Horloge(Circuit circuit, Pane layout, double x, double y){
        super("Horloge", 1, circuit, layout, x, y);
        Shape shape = getShape();
        this.circuit = circuit;
        setText("CLK", shape.getLayoutBounds().getMaxX() / 2 + shape.getLayoutX(),
        shape.getLayoutBounds().getMaxY() / 2 + shape.getLayoutY());
   }
   @Override
   public void evaluateOutput() {
    Fils[] inputs = getInputs();
    QuadBool input = inputs[0].getOutput(); 
    if ( lastInput == QuadBool.FALSE && input == QuadBool.TRUE ) // detection de front montant
    {
        current = NotQuad.Not(current);g // changement d'état
    }

    lastInput = input;
    setOutput(current);
    setIHaveOutput(true);

   }

   @Override
   public void addPoints (Circuit circuit){
    super.addPoints(circuit);
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