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
import javafx.scene.paint.Color;


public class Horloge extends Gate {

    private QuadBool current = QuadBool.FALSE;
    private Timeline toggleTimer;
    private boolean isAutoToggling = false;

    public Horloge(Circuit circuit, Pane layout, double x, double y) {
        super("HORLOGE", 1, circuit, layout, x, y);
        setOutput(current);

        Shape shape = getShape();
        setText("CLK", shape.getLayoutBounds().getMaxX() / 4 + shape.getLayoutX(),
                shape.getLayoutBounds().getMaxY() / 2 + shape.getLayoutY());

        // Timer pour alternance automatique
        toggleTimer = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            toggleClock();
        }));
        toggleTimer.setCycleCount(Timeline.INDEFINITE);
    }

    private void toggleClock() {
        current = NotQuad.Not(current);
        setOutput(current);
        setIHaveOutput(true);
        updateConnectedComponents();
    }

    private void updateConnectedComponents() {
        if (getOutputFils() != null) {
            getOutputFils().setOutputValue(current);
            getOutputFils().changeColor(getColorForQuadBool(current));
    
            for (Fils connectedFil : getOutputFils().getConnected()) {
                connectedFil.setOutputValue(current);
                connectedFil.changeColor(getColorForQuadBool(current));
    
                if (connectedFil.getGate() != null) {
                    connectedFil.getGate().evaluateOutput();
                }
            }
        }
    }
    

    private Color getColorForQuadBool(QuadBool value) {
        switch (value) {
            case TRUE: return Unity.ON;
            case FALSE: return Unity.OFF;
            case ERROR: return Unity.ERR;
            default: return Unity.NOTH;
        }
    }

    @Override
    public void evaluateOutput() {
        Fils[] inputs = getInputs();
        if (inputs == null || inputs.length == 0) return;

        QuadBool in0 = inputs[0].getOutput();

        if (in0 == QuadBool.TRUE) {
            startToggle();
        } else {
            stopToggle();
        }
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
        
        // Création du fil de sortie
        Fils output = new Fils(Unity.tranformDoubleToInt(shape.getLayoutX() + distance / 2),
                Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxY() + shape.getLayoutY()),
                circuit, null, this, true);
        setOutputFils(output);

        // Création des fils d'entrée
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
            if (input != null) {
                input.onRelease();
            }
        }
    }
}