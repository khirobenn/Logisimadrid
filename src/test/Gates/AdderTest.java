package Gates;

import Circuit.Circuit;
import Circuit.Fils;
import Circuit.QuadBool;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdderTest {
    private Adder adder;
    private Circuit circuit;
    private Pane pane;

    @BeforeEach
    void setUp() {
        pane = new Pane();
        circuit = new Circuit(pane);
        adder = new Adder(circuit, pane, 100, 100);
    }

    @Test
    void testConstructor() {
        assertNotNull(adder);
        assertEquals("ADDER", adder.getName());
        assertEquals(2, adder.getInputs().length);
        assertNotNull(adder.getRetenuOut());
        assertNotNull(adder.getRetenuIn());
        assertNotNull(adder.getShape());
    }

    @Test
    void testEvaluateOutput() {
        // Test cases for adder functionality
        
        // Case 1: 0 + 0 + 0 = 0 (sum 0, carry 0)
        setInputs(QuadBool.FALSE, QuadBool.FALSE, QuadBool.FALSE);
        adder.evaluateOutput();
        assertEquals(QuadBool.FALSE, adder.getOutput());
        assertEquals(QuadBool.FALSE, adder.getRetenuOut().getOutputValue());

        // Case 2: 0 + 1 + 0 = 1 (sum 1, carry 0)
        setInputs(QuadBool.FALSE, QuadBool.TRUE, QuadBool.FALSE);
        adder.evaluateOutput();
        assertEquals(QuadBool.TRUE, adder.getOutput());
        assertEquals(QuadBool.FALSE, adder.getRetenuOut().getOutputValue());

        // Case 3: 1 + 1 + 0 = 0 (sum 0, carry 1)
        setInputs(QuadBool.TRUE, QuadBool.TRUE, QuadBool.FALSE);
        adder.evaluateOutput();
        assertEquals(QuadBool.FALSE, adder.getOutput());
        assertEquals(QuadBool.TRUE, adder.getRetenuOut().getOutputValue());

        // Case 4: 1 + 1 + 1 = 1 (sum 1, carry 1)
        setInputs(QuadBool.TRUE, QuadBool.TRUE, QuadBool.TRUE);
        adder.evaluateOutput();
        assertEquals(QuadBool.TRUE, adder.getOutput());
        assertEquals(QuadBool.TRUE, adder.getRetenuOut().getOutputValue());

        // Case 5: Error propagation
        setInputs(QuadBool.ERROR, QuadBool.TRUE, QuadBool.FALSE);
        adder.evaluateOutput();
        assertEquals(QuadBool.ERROR, adder.getOutput());
        assertEquals(QuadBool.ERROR, adder.getRetenuOut().getOutputValue());
    }

    private void setInputs(QuadBool a, QuadBool b, QuadBool carryIn) {
        Fils[] inputs = adder.getInputs();
        inputs[0].setOutput(a);
        inputs[1].setOutput(b);
        adder.getRetenuIn().setOutput(carryIn);
    }
}
