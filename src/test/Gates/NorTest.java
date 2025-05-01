package test.Gates;

import Circuit.Circuit;
import Circuit.Fils;
import Circuit.QuadBool;
import Gates.Nor;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NorTest {

    private Circuit circuit;
    private Pane layout;
    private Fils input1, input2;
    private Nor norGate;

    @BeforeEach
    public void setUp() {
        layout = new Pane();
        circuit = new Circuit(layout);

        // Créer la porte NOR d'abord
        norGate = new Nor(2, circuit, layout, 100, 100);

        // Créer les fils et les connecter à la porte
	input1 = new Fils(0, 0, circuit, null, norGate, true);
	input2 = new Fils(0, 0, circuit, null, norGate, true);

        // Ajouter les fils comme entrées de la porte
	norGate.getInputs()[0] = input1;
	norGate.getInputs()[1] = input2;
    }

    @Test
    public void testNorGateOutputTrueFalse() {
        input1.setOutput(QuadBool.TRUE);
        input2.setOutput(QuadBool.FALSE);

        norGate.evaluateOutput();

        assertEquals(QuadBool.FALSE, norGate.getOutput());
    }

    @Test
    public void testNorGateWithBothFalseInputs() {
        input1.setOutput(QuadBool.FALSE);
        input2.setOutput(QuadBool.FALSE);

        norGate.evaluateOutput();

        assertEquals(QuadBool.TRUE, norGate.getOutput());
    }

    @Test
    public void testNorGateWithBothTrueInputs() {
        input1.setOutput(QuadBool.TRUE);
        input2.setOutput(QuadBool.TRUE);

        norGate.evaluateOutput();

        assertEquals(QuadBool.FALSE, norGate.getOutput());
    }

    @Test
    public void testNorGateWithError() {
        input1.setOutput(QuadBool.ERROR);
        input2.setOutput(QuadBool.FALSE);

        norGate.evaluateOutput();

        assertEquals(QuadBool.ERROR, norGate.getOutput());
    }

    @Test
    public void testNorGateWithNothing() {
        input1.setOutput(QuadBool.NOTHING);
        input2.setOutput(QuadBool.FALSE);

        norGate.evaluateOutput();

        assertEquals(QuadBool.ERROR, norGate.getOutput());
    }
}
