package Gates;

import Circuit.Circuit;
import Circuit.QuadBool;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class XnorTest {

    private Xnor xnorGate;
    private Circuit circuit;
    private Pane pane;

    @BeforeEach
    public void setUp() {
        new JFXPanel(); // Initialise JavaFX
        pane = new Pane();
        circuit = new Circuit(pane);
        xnorGate = new Xnor(2, circuit, pane, 0, 0);
    }

    @Test
    public void testXnor_TrueTrue_ReturnsTrue() {
        xnorGate.getInputs()[0].setOutput(QuadBool.TRUE);
        xnorGate.getInputs()[1].setOutput(QuadBool.TRUE);
        xnorGate.evaluateOutput();
        assertEquals(QuadBool.TRUE, xnorGate.getOutputValue());
    }

    @Test
    public void testXnor_FalseFalse_ReturnsTrue() {
        xnorGate.getInputs()[0].setOutput(QuadBool.FALSE);
        xnorGate.getInputs()[1].setOutput(QuadBool.FALSE);
        xnorGate.evaluateOutput();
        assertEquals(QuadBool.TRUE, xnorGate.getOutputValue());
    }

    @Test
    public void testXnor_TrueFalse_ReturnsFalse() {
        xnorGate.getInputs()[0].setOutput(QuadBool.TRUE);
        xnorGate.getInputs()[1].setOutput(QuadBool.FALSE);
        xnorGate.evaluateOutput();
        assertEquals(QuadBool.FALSE, xnorGate.getOutputValue());
    }

    @Test
    public void testXnor_FalseTrue_ReturnsFalse() {
        xnorGate.getInputs()[0].setOutput(QuadBool.FALSE);
        xnorGate.getInputs()[1].setOutput(QuadBool.TRUE);
        xnorGate.evaluateOutput();
        assertEquals(QuadBool.FALSE, xnorGate.getOutputValue());
    }

    @Test
    public void testXnor_ErrorInput_PropagatesError() {
        xnorGate.getInputs()[0].setOutput(QuadBool.ERROR);
        xnorGate.getInputs()[1].setOutput(QuadBool.TRUE);
        xnorGate.evaluateOutput();
        assertEquals(QuadBool.ERROR, xnorGate.getOutputValue());
    }

    @Test
    public void testXnor_ThreeInputs_TRUE_TRUE_FALSE_ReturnsFalse() {
        Xnor xnor3 = new Xnor(3, circuit, pane, 0, 0);
        xnor3.getInputs()[0].setOutput(QuadBool.TRUE);
        xnor3.getInputs()[1].setOutput(QuadBool.TRUE);
        xnor3.getInputs()[2].setOutput(QuadBool.FALSE);
        xnor3.evaluateOutput();
        assertEquals(QuadBool.FALSE, xnor3.getOutputValue());
    }

    @Test
    public void testXnor_ThreeInputs_TRUE_TRUE_TRUE_ReturnsTrue() {
        Xnor xnor3 = new Xnor(3, circuit, pane, 0, 0);
        xnor3.getInputs()[0].setOutput(QuadBool.TRUE);
        xnor3.getInputs()[1].setOutput(QuadBool.TRUE);
        xnor3.getInputs()[2].setOutput(QuadBool.TRUE);
        xnor3.evaluateOutput();
        assertEquals(QuadBool.TRUE, xnor3.getOutputValue());
    }
}
