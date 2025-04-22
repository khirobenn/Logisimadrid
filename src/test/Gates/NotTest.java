package Gates;

import Circuit.Circuit;
import Circuit.QuadBool;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotTest {

    private Not notGate;
    private Circuit circuit;
    private Pane pane;

    @BeforeEach
    public void setUp() {
        // Initialiser JavaFX pour les composants graphiques
        new JFXPanel();

        pane = new Pane();
        circuit = new Circuit(pane);
        notGate = new Not(circuit, pane, 0, 0); // Crée une porte NOT
    }

    @Test
    public void testNot_True_ReturnsFalse() {
        notGate.getInputs()[0].setOutput(QuadBool.TRUE);
        notGate.evaluateOutput();
        assertEquals(QuadBool.FALSE, notGate.getOutputValue());
    }

    @Test
    public void testNot_False_ReturnsTrue() {
        notGate.getInputs()[0].setOutput(QuadBool.FALSE);
        notGate.evaluateOutput();
        assertEquals(QuadBool.TRUE, notGate.getOutputValue());
    }

    @Test
    public void testNot_Error_ReturnsError() {
        notGate.getInputs()[0].setOutput(QuadBool.ERROR);
        notGate.evaluateOutput();
        assertEquals(QuadBool.ERROR, notGate.getOutputValue());
    }

    @Test
    public void testNot_Nothing_ReturnsError() {
        notGate.getInputs()[0].setOutput(QuadBool.NOTHING);
        notGate.evaluateOutput();
        assertEquals(QuadBool.ERROR, notGate.getOutputValue());
    }

    @Test
    public void testNot_VisualDebug() {
        notGate.getInputs()[0].setOutput(QuadBool.TRUE);
        notGate.evaluateOutput();
        System.out.println("[DEBUG] NOT(TRUE) = " + notGate.getOutputValue());
        assertEquals(QuadBool.FALSE, notGate.getOutputValue());
    }
}
