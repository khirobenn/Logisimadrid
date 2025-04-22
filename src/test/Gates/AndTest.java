package Gates;

import Circuit.Circuit;
import Circuit.QuadBool;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AndTest {
    private And andGate;
    private Pane pane;
    private Circuit circuit;

    @BeforeEach
    public void setUp() {
        pane = new Pane();
        circuit = new Circuit(pane);
        andGate = new And(2, circuit, pane, 0, 0); // Porte AND à 2 entrées
    }

    // Tests de base
    @Test
    public void testAnd_TrueTrue_ReturnsTrue() {
        andGate.getInputs()[0].setOutput(QuadBool.TRUE);
        andGate.getInputs()[1].setOutput(QuadBool.TRUE);
        andGate.evaluateOutput();
        assertEquals(QuadBool.TRUE, andGate.getOutputValue());
    }

    @Test
    public void testAnd_TrueFalse_ReturnsFalse() {
        andGate.getInputs()[0].setOutput(QuadBool.TRUE);
        andGate.getInputs()[1].setOutput(QuadBool.FALSE);
        andGate.evaluateOutput();
        assertEquals(QuadBool.FALSE, andGate.getOutputValue());
    }

    // Tests avec états spéciaux
    @Test
    public void testAnd_ErrorInput_PropagatesError() {
        andGate.getInputs()[0].setOutput(QuadBool.ERROR);
        andGate.getInputs()[1].setOutput(QuadBool.TRUE);
        andGate.evaluateOutput();
        assertEquals(QuadBool.ERROR, andGate.getOutputValue());
    }

    @Test
    public void testAnd_NothingInput_ReturnsError() {
        andGate.getInputs()[0].setOutput(QuadBool.NOTHING);
        andGate.getInputs()[1].setOutput(QuadBool.TRUE);
        andGate.evaluateOutput();
        assertEquals(QuadBool.ERROR, andGate.getOutputValue());
    }

    // Tests de configuration
    @Test
    public void testAnd_ThreeInputs_Behavior() {
        And threeInputAnd = new And(3, circuit, pane, 0, 0);
        threeInputAnd.getInputs()[0].setOutput(QuadBool.TRUE);
        threeInputAnd.getInputs()[1].setOutput(QuadBool.TRUE);
        threeInputAnd.getInputs()[2].setOutput(QuadBool.FALSE);
        threeInputAnd.evaluateOutput();
        assertEquals(QuadBool.FALSE, threeInputAnd.getOutputValue());
    }

    

    // Test visuel (pour debug)
    @Test
    public void testAnd_VisualFeedback() {
        andGate.getInputs()[0].setOutput(QuadBool.TRUE);
        andGate.getInputs()[1].setOutput(QuadBool.FALSE);
        andGate.evaluateOutput();
        
        System.out.println("[TEST VISUEL] AND(TRUE, FALSE) = " 
            + andGate.getOutputValue());
        assertEquals(QuadBool.FALSE, andGate.getOutputValue());
    }
}