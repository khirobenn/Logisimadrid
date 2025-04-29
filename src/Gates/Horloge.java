package Gates;

import Circuit.Circuit;
import Circuit.Fils;
import Circuit.Gate;
import Circuit.QuadBool;
import Circuit.Unity;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class Horloge extends Gate {

    private QuadBool lastInput = QuadBool.FALSE;
    private QuadBool current = QuadBool.FALSE;
    private Timeline toggleTimer;
    private boolean isAutoToggling = false;

    public Horloge(Circuit circuit, Pane layout, double x, double y) {
        super("HORLOGE", 2, circuit, layout, x, y); 

        Shape shape = getShape();
        setText("CLK", shape.getLayoutBounds().getMaxX() / 4 + shape.getLayoutX(),
                shape.getLayoutBounds().getMaxY() / 2 + shape.getLayoutY());

        // Timer pour alternance automatique
        toggleTimer = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            current = NotQuad.Not(current);
            setOutput(current);
            setIHaveOutput(true);
        }));
        toggleTimer.setCycleCount(Timeline.INDEFINITE);
    }

    @Override
    public void evaluateOutput() {
        Fils[] inputs = getInputs();
        QuadBool in0 = inputs[0].getOutput(); // entrée principale
        QuadBool in1 = inputs[1].getOutput(); // mode

        // Cas d'erreur : les deux entrées sont à 1
        if (in0 == QuadBool.TRUE && in1 == QuadBool.TRUE) {
            stopToggle();
            current = QuadBool.ERROR;
        } 
        // Cas toggle automatique la 2eme entrée activé
        else if (in1 == QuadBool.TRUE) {
            startToggle();
            return; // ne pas appliquer manuellement d'autres changements
        } 
        // Cas normal 1ere antrée activée (horloge march en front montant)
        else {
            stopToggle();
            if (lastInput == QuadBool.FALSE && in0 == QuadBool.TRUE) {
                current = NotQuad.Not(current);
            }
        }

        lastInput = in0;
        setOutput(current);
        setIHaveOutput(true);
    }

    private void startToggle() {
        if (!isAutoToggling) {
            toggleTimer.play();
            isAutoToggling = true;
        }
    }

    private void stopToggle() {
        if (isAutoToggling) {
            toggleTimer.stop();
            isAutoToggling = false;
        }
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
