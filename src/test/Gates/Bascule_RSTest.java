package Gates;

import Circuit.Circuit;
import Circuit.Fils;
import Circuit.QuadBool;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Bascule_RSTest {
    private Bascule_RS bascule;
    private Circuit circuit;
    private Pane pane;

    @BeforeEach
    public void setUp() {
        circuit = new Circuit(new Pane());
        pane = new Pane();
        bascule = new Bascule_RS(circuit, pane, 0, 0);
    }

    @Test
    public void testInitialState() {
        assertEquals("BASCULE RS", bascule.getName());
        assertEquals(2, bascule.getInputs().length);
        assertEquals(QuadBool.FALSE, bascule.getOutput());
    }

    @Test
    public void testSetInputs() {
        // Test que les entrées peuvent être définies
	 Fils input1 = new Fils(0, 0, circuit, null, bascule, true);
	 Fils input2 = new Fils(0, 0, circuit, null, bascule, true);
        bascule.getInputs()[0] = input1;
        bascule.getInputs()[1] = input2;
        
        assertNotNull(bascule.getInputs()[0]);
        assertNotNull(bascule.getInputs()[1]);
    }

    @Test
    public void testEvaluateOutput_SetState() {
        // S=1, R=0 => Q=1
        setInputs(QuadBool.FALSE, QuadBool.TRUE);
        bascule.evaluateOutput();
        assertEquals(QuadBool.TRUE, bascule.getOutput());
    }

    @Test
    public void testEvaluateOutput_ResetState() {
        // S=0, R=1 => Q=0
        setInputs(QuadBool.TRUE, QuadBool.FALSE);
        bascule.evaluateOutput();
        assertEquals(QuadBool.FALSE, bascule.getOutput());
    }

    @Test
    public void testEvaluateOutput_HoldState() {
        // S=0, R=0 => Q maintient son état
        // Premier état
        setInputs(QuadBool.FALSE, QuadBool.TRUE);
        bascule.evaluateOutput();
        assertEquals(QuadBool.TRUE, bascule.getOutput());
        
        // Changement à maintien
        setInputs(QuadBool.FALSE, QuadBool.FALSE);
        bascule.evaluateOutput();
        assertEquals(QuadBool.TRUE, bascule.getOutput()); // Doit rester à TRUE
    }

    @Test
    public void testEvaluateOutput_ForbiddenState() {
        // S=1, R=1 => état interdit (ERROR)
        setInputs(QuadBool.TRUE, QuadBool.TRUE);
        bascule.evaluateOutput();
        assertEquals(QuadBool.ERROR, bascule.getOutput());
    }

    @Test
    public void testEvaluateOutput_UndefinedInput() {
        // S=NOTHING, R=0 => maintien de l'état
        setInputs(QuadBool.FALSE, QuadBool.NOTHING);
        bascule.evaluateOutput();
        assertEquals(QuadBool.FALSE, bascule.getOutput()); // État initial
        
        // Change l'état puis test avec NOTHING
        setInputs(QuadBool.FALSE, QuadBool.TRUE);
        bascule.evaluateOutput();
        setInputs(QuadBool.FALSE, QuadBool.NOTHING);
        bascule.evaluateOutput();
        assertEquals(QuadBool.TRUE, bascule.getOutput()); // Doit rester à TRUE
    }

    @Test
    public void testEvaluateOutput_ErrorPropagation() {
        // Si une entrée est ERROR, la sortie doit être ERROR
        setInputs(QuadBool.ERROR, QuadBool.FALSE);
        bascule.evaluateOutput();
        assertEquals(QuadBool.ERROR, bascule.getOutput());
        
        setInputs(QuadBool.FALSE, QuadBool.ERROR);
        bascule.evaluateOutput();
        assertEquals(QuadBool.ERROR, bascule.getOutput());
    }

    @Test
    public void testAddPoints() {
        assertDoesNotThrow(() -> bascule.addPoints(circuit));
        assertNotNull(bascule.getOutputFils());
        assertEquals(2, bascule.getInputs().length);
    }

    @Test
    public void testUpdatePoints() {
        assertDoesNotThrow(() -> bascule.updatePoints());
    }

    @Test
    public void testOnRelease() {
        assertDoesNotThrow(() -> bascule.onRelease());
    }

    private void setInputs(QuadBool R, QuadBool S) {
        Fils inputR = new Fils(0, 0, circuit,null, bascule, true);
        Fils inputS = new Fils(0, 0, circuit,null, bascule, true);
        inputR.setOutput(R);
        inputS.setOutput(S);
        bascule.getInputs()[0] = inputR;
        bascule.getInputs()[1] = inputS;
    }
}
