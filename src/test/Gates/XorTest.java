package test.Gates;

import Circuit.Circuit;
import Circuit.QuadBool;
import Gates.Xor;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class XorTest {

    private Xor xorGate;
    private Circuit circuit;
    private Pane pane;

    @BeforeEach
    public void setUp() {
        new JFXPanel(); // Initialiser JavaFX
        pane = new Pane();
        circuit = new Circuit(pane);
        xorGate = new Xor(2, circuit, pane, 0, 0); // XOR à 2 entrées
    }

    @Test
    public void testXor_TrueFalse_ReturnsTrue() {
        xorGate.getInputs()[0].setOutput(QuadBool.TRUE);
        xorGate.getInputs()[1].setOutput(QuadBool.FALSE);
        xorGate.evaluateOutput();
        assertEquals(QuadBool.TRUE, xorGate.getOutputValue());
    }

    @Test
    public void testXor_TrueTrue_ReturnsFalse() {
        xorGate.getInputs()[0].setOutput(QuadBool.TRUE);
        xorGate.getInputs()[1].setOutput(QuadBool.TRUE);
        xorGate.evaluateOutput();
        assertEquals(QuadBool.FALSE, xorGate.getOutputValue());
    }

    @Test
    public void testXor_FalseFalse_ReturnsFalse() {
        xorGate.getInputs()[0].setOutput(QuadBool.FALSE);
        xorGate.getInputs()[1].setOutput(QuadBool.FALSE);
        xorGate.evaluateOutput();
        assertEquals(QuadBool.FALSE, xorGate.getOutputValue());
    }

    @Test
    public void testXor_ErrorInput_PropagatesError() {
        xorGate.getInputs()[0].setOutput(QuadBool.TRUE);
        xorGate.getInputs()[1].setOutput(QuadBool.ERROR);
        xorGate.evaluateOutput();
        assertEquals(QuadBool.ERROR, xorGate.getOutputValue());
    }

    @Test
    public void testXor_NothingInput_ReturnsError() {
        xorGate.getInputs()[0].setOutput(QuadBool.FALSE);
        xorGate.getInputs()[1].setOutput(QuadBool.NOTHING);
        xorGate.evaluateOutput();
        assertEquals(QuadBool.ERROR, xorGate.getOutputValue());
    }

    @Test
    public void testXor_ThreeInputs_TRUE_FALSE_TRUE_ReturnsFalse() {
        Xor xor3 = new Xor(3, circuit, pane, 0, 0);
        xor3.getInputs()[0].setOutput(QuadBool.TRUE);
        xor3.getInputs()[1].setOutput(QuadBool.FALSE);
        xor3.getInputs()[2].setOutput(QuadBool.TRUE);
        xor3.evaluateOutput();
        assertEquals(QuadBool.FALSE, xor3.getOutputValue()); // car 1 ⊕ 0 ⊕ 1 = 0
    }
}

