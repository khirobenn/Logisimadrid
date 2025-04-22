package Gates;

import Circuit.Circuit;
import Circuit.Fils;
import Circuit.QuadBool;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdderTest {
    private Adder adder;
    private Circuit circuit;
    private Pane pane;

    @BeforeEach
    public void setUp() {
        circuit = new Circuit();
        pane = new Pane();
        adder = new Adder(circuit, pane, 0, 0);
    }

    @Test
    public void testInitialization() {
        assertNotNull(adder.getRetenuOut());
        assertEquals("ADDER", adder.getGateType());
        assertEquals(2, adder.getInputs().length);
    }

    @Test
    public void testAdditionWithoutCarry() {
        // 0 + 0 = 0 (no carry)
        adder.setInput(0, QuadBool.FALSE);
        adder.setInput(1, QuadBool.FALSE);
        adder.getRetenuIn().setOutput(QuadBool.FALSE);
        adder.evaluateOutput();
        
        assertEquals(QuadBool.FALSE, adder.getOutput());
        assertEquals(QuadBool.FALSE, adder.getRetenuOut().getOutput());
    }

    @Test
    public void testAdditionWithCarry() {
        // 1 + 1 = 0 (with carry)
        adder.setInput(0, QuadBool.TRUE);
        adder.setInput(1, QuadBool.TRUE);
        adder.getRetenuIn().setOutput(QuadBool.FALSE);
        adder.evaluateOutput();
        
        assertEquals(QuadBool.FALSE, adder.getOutput());
        assertEquals(QuadBool.TRUE, adder.getRetenuOut().getOutput());
    }

    @Test
    public void testAdditionWithInputCarry() {
        // 1 + 0 + 1 (carry in) = 0 (with carry out)
        adder.setInput(0, QuadBool.TRUE);
        adder.setInput(1, QuadBool.FALSE);
        adder.getRetenuIn().setOutput(QuadBool.TRUE);
        adder.evaluateOutput();
        
        assertEquals(QuadBool.FALSE, adder.getOutput());
        assertEquals(QuadBool.TRUE, adder.getRetenuOut().getOutput());
    }

    @Test
    public void testAdditionWithAllInputsTrue() {
        // 1 + 1 + 1 (carry in) = 1 (with carry out)
        adder.setInput(0, QuadBool.TRUE);
        adder.setInput(1, QuadBool.TRUE);
        adder.getRetenuIn().setOutput(QuadBool.TRUE);
        adder.evaluateOutput();
        
        assertEquals(QuadBool.TRUE, adder.getOutput());
        assertEquals(QuadBool.TRUE, adder.getRetenuOut().getOutput());
    }

    @Test
    public void testUndefinedInput() {
        // X + 0 = X (undefined)
        adder.setInput(0, QuadBool.NOTHING);
        adder.setInput(1, QuadBool.FALSE);
        adder.getRetenuIn().setOutput(QuadBool.FALSE);
        adder.evaluateOutput();
        
        assertEquals(QuadBool.NOTHING, adder.getOutput());
        assertEquals(QuadBool.NOTHING, adder.getRetenuOut().getOutput());
    }

    @Test
    public void testRetenuOutConnection() {
        assertNotNull(adder.getRetenuOut());
        assertEquals(adder, adder.getRetenuOut().getGate());
    }

    @Test
    public void testUpdatePoints() {
        // Test that updatePoints doesn't throw exceptions
        assertDoesNotThrow(() -> adder.updatePoints());
    }

    @Test
    public void testOnRelease() {
        // Test that onRelease doesn't throw exceptions
        assertDoesNotThrow(() -> adder.onRelease());
    }
}