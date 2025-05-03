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
import java.util.ArrayDeque;
import java.util.Deque;

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

        // Timer plus rapide pour meilleur feedback visuel
        toggleTimer = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            toggleClock();
        }));
        toggleTimer.setCycleCount(Timeline.INDEFINITE);
        
        // Démarrer immédiatement pour le test
        startToggle();
    }

    private void toggleClock() {
        current = NotQuad.Not(current);
        setOutput(current);
        setIHaveOutput(true);
        propagateSignal();
    }

    private void propagateSignal() {
        if (getOutputFils() != null) {
            Deque<Fils> visited = new ArrayDeque<>();
            recursivePropagate(visited, getOutputFils(), current);
        }
    }

    private void recursivePropagate(Deque<Fils> visited, Fils currentFil, QuadBool value) {
        if (visited.contains(currentFil)) {
            return;
        }
        
        visited.add(currentFil);
        
        // Mettre à jour le fil courant
        currentFil.setOutputValue(value);
        currentFil.changeColor(getColorForQuadBool(value));
        
        // Propager aux composants connectés
        for (Fils connectedFil : currentFil.getConnected()) {
            // Mettre à jour le fil connecté
            connectedFil.setOutputValue(value);
            connectedFil.changeColor(getColorForQuadBool(value));
            
            // Si connecté à une porte, évaluer sa sortie
            if (connectedFil.getGate() != null) {
                connectedFil.getGate().evaluateOutput();
                
                // Propager récursivement la nouvelle sortie
                if (connectedFil.getGate().getOutputFils() != null) {
                    recursivePropagate(visited, 
                                     connectedFil.getGate().getOutputFils(), 
                                     connectedFil.getGate().getOutput());
                }
            }
            
            // Continuer la propagation
            recursivePropagate(visited, connectedFil, value);
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
            propagateSignal(); // Propager l'état initial
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
        if (inputs != null && inputs.length > 0) {
            inputs[0] = new Fils(Unity.tranformDoubleToInt(shape.getLayoutX() + distance / 2),
                    Unity.tranformDoubleToInt(shape.getLayoutY()),
                    circuit, null, null, true);
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