package Gates;

import Circuit.Circuit;
import Circuit.Fils;
import Circuit.Gate;
import Circuit.QuadBool;
import Circuit.Unity;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class Bascule_D extends Gate {

    private QuadBool current = QuadBool.FALSE;  // É Qn
    private QuadBool lastClock = QuadBool.FALSE; // Pour détection front montant

    public Bascule_D(Circuit circuit, Pane layout, double x, double y) {
        super("Bascule D", 2, circuit, layout, x, y); //  entrées: La Data, l'horloge 
        setOutputValue(QuadBool.FALSE); // Initialisation de la sortie
        
        Shape shape = getShape();
        double shapeX = shape.getLayoutX();
        double shapeY = shape.getLayoutY();
        double shapeWidth = shape.getLayoutBounds().getWidth();
        double shapeHeight = shape.getLayoutBounds().getHeight();

        double centerX = shapeX + shapeWidth / 2 - 1;
        double centerY = shapeY + shapeHeight / 2 - 1;

        setText("D ", centerX, centerY);
    }

    @Override
    public void evaluateOutput() {
        Fils[] inputs = getInputs();
        
        
        if (inputs[0] == null || inputs[1] == null) {
            setOutput(current);
            return;
        }
        QuadBool CLK = inputs[0].getOutput();
        QuadBool D = inputs[1].getOutput();
       

        // Détection du front montant de l'horloge
        boolean risingEdge = (lastClock == QuadBool.FALSE && CLK == QuadBool.TRUE);
        lastClock = CLK;

        QuadBool nextQ = current; 

        // etat d'erreur
        if (D == QuadBool.ERROR || CLK == QuadBool.ERROR) {
            nextQ = QuadBool.ERROR;
        } 
        // travaille seulement sur front montant de l'horloge
        else if (risingEdge) {
            //  on copie simplement la valeur de la data
            nextQ = D;
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