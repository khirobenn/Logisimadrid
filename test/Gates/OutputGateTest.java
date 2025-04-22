package Gates;

import Circuit.Circuit;
import Circuit.Fils;
import Circuit.QuadBool;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OutputGateTest {

    private Circuit circuit;
    private Pane layout;
    private OutputGate outputGate;
    private Fils input;

    @BeforeEach
    public void setUp() {
        layout = new Pane();  // Crée un layout pour le test
        circuit = new Circuit(layout);  // Crée un circuit avec le layout
        input = new Fils(circuit, null, outputGate);  // Crée un fils (entrée) pour la porte OutputGate
        outputGate = new OutputGate(circuit, layout, 100, 100);  // Crée une porte OutputGate
    }

    @Test
    public void testOutputGateInitialState() {
        // Vérifier que la sortie initiale est "NOTHING"
        assertEquals(QuadBool.NOTHING, outputGate.getOutput());
    }

    @Test
    public void testOutputGateWithTrueInput() {
        input.setOutput(QuadBool.TRUE);  // Définir l'entrée sur VRAI
        outputGate.evaluateOutput();  // Évaluer la sortie

        // Vérifier que la sortie de la porte OutputGate est VRAI
        assertEquals(QuadBool.TRUE, outputGate.getOutput());
    }

    @Test
    public void testOutputGateWithFalseInput() {
        input.setOutput(QuadBool.FALSE);  // Définir l'entrée sur FAUX
        outputGate.evaluateOutput();  // Évaluer la sortie

        // Vérifier que la sortie de la porte OutputGate est FAUX
        assertEquals(QuadBool.FALSE, outputGate.getOutput());
    }

    @Test
    public void testOutputGateWithNothingInput() {
        input.setOutput(QuadBool.NOTHING);  // Définir l'entrée sur NOTHING
        outputGate.evaluateOutput();  // Évaluer la sortie

        // Vérifier que la sortie de la porte OutputGate est NOTHING
        assertEquals(QuadBool.NOTHING, outputGate.getOutput());
    }
}
