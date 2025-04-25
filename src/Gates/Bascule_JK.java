package Gates;

import Circuit.Circuit;
import Circuit.Fils;
import Circuit.Gate;
import Circuit.QuadBool;
import Circuit.Unity;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class Bascule_JK extends Gate {

    private QuadBool current = QuadBool.FALSE;  // ETAT Qn
    private QuadBool lastClock = QuadBool.FALSE; // front montant

    public Bascule_JK(Circuit circuit, Pane layout, double x, double y) {
        super("BASCULE JK", 3, circuit, layout, x, y); //  entrées: J, CLK, K
        setOutputValue(QuadBool.FALSE); // Initialisation de la sortie
        
        Shape shape = getShape();
        double shapeX = shape.getLayoutX();
        double shapeY = shape.getLayoutY();
        double shapeWidth = shape.getLayoutBounds().getWidth();
        double shapeHeight = shape.getLayoutBounds().getHeight();

        double centerX = shapeX + shapeWidth / 2 - 1;
        double centerY = shapeY + shapeHeight / 2 - 1;

        setText("JK", centerX, centerY);
    }

    @Override
    public void evaluateOutput() {
        Fils[] inputs = getInputs();
        
        
        if (inputs[0] == null || inputs[1] == null || inputs[2] == null) {
            setOutput(current);
            return;
        }

        QuadBool J = inputs[0].getOutput();
        QuadBool CLK = inputs[1].getOutput();
        QuadBool K = inputs[2].getOutput();

        //  front montant de l'horloge
        boolean risingEdge = (lastClock == QuadBool.FALSE && CLK == QuadBool.TRUE);
        lastClock = CLK;

        QuadBool nextQ = current; // Par défaut, maintien de l'état ou mémorisation

        // le cas d'erreur
        if (J == QuadBool.ERROR || CLK == QuadBool.ERROR || K == QuadBool.ERROR) {
            nextQ = QuadBool.ERROR;
        } 
        // travaille seulement sur front montant de l'horloge
        else if (risingEdge) {
            if (J == QuadBool.TRUE && K == QuadBool.TRUE) {
                // Mode bascule - inversion 
                nextQ = NotQuad.Not(current);
            } 
            else if (J == QuadBool.TRUE && K != QuadBool.TRUE) {
                // Set
                nextQ = QuadBool.TRUE;
            } 
            else if (K == QuadBool.TRUE && J != QuadBool.TRUE) {
                // Reset
                nextQ = QuadBool.FALSE;
            }
            // Sinon maintien de l'état (déjà défaut)
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