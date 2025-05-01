package test.Gates;

import Circuit.Circuit;
import Circuit.QuadBool;
import Gates.Or;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrTest {

    private Or orGate;
    private Circuit circuit;
    private Pane pane;

    @BeforeEach
    public void setUp() {
        new JFXPanel(); // Initialise JavaFX

        pane = new Pane();
        circuit = new Circuit(pane);
        orGate = new Or(2, circuit, pane, 0, 0);
    }

    @Test
    public void testOr_TrueTrue_ReturnsTrue() {
        orGate.getInputs()[0].setOutput(QuadBool.TRUE);
        orGate.getInputs()[1].setOutput(QuadBool.TRUE);
        orGate.evaluateOutput();
        assertEquals(QuadBool.TRUE, orGate.getOutputValue());
    }

    @Test
    public void testOr_TrueFalse_ReturnsTrue() {
        orGate.getInputs()[0].setOutput(QuadBool.TRUE);
        orGate.getInputs()[1].setOutput(QuadBool.FALSE);
        orGate.evaluateOutput();
        assertEquals(QuadBool.TRUE, orGate.getOutputValue());
    }

    @Test
    public void testOr_FalseFalse_ReturnsFalse() {
        orGate.getInputs()[0].setOutput(QuadBool.FALSE);
        orGate.getInputs()[1].setOutput(QuadBool.FALSE);
        orGate.evaluateOutput();
        assertEquals(QuadBool.FALSE, orGate.getOutputValue());
    }

    @Test
    public void testOr_TrueError_ReturnsTrue() {
        orGate.getInputs()[0].setOutput(QuadBool.TRUE);
        orGate.getInputs()[1].setOutput(QuadBool.ERROR);
        orGate.evaluateOutput();
        assertEquals(QuadBool.TRUE, orGate.getOutputValue());
    }

    @Test
    public void testOr_ErrorFalse_ReturnsError() {
        orGate.getInputs()[0].setOutput(QuadBool.ERROR);
        orGate.getInputs()[1].setOutput(QuadBool.FALSE);
        orGate.evaluateOutput();
        assertEquals(QuadBool.ERROR, orGate.getOutputValue());
    }

    @Test
    public void testOr_NothingTrue_ReturnsTrue() {
        orGate.getInputs()[0].setOutput(QuadBool.NOTHING);
        orGate.getInputs()[1].setOutput(QuadBool.TRUE);
        orGate.evaluateOutput();
        assertEquals(QuadBool.TRUE, orGate.getOutputValue());
    }

    @Test
    public void testOr_NothingFalse_ReturnsError() {
        orGate.getInputs()[0].setOutput(QuadBool.NOTHING);
        orGate.getInputs()[1].setOutput(QuadBool.FALSE);
        orGate.evaluateOutput();
    }
}