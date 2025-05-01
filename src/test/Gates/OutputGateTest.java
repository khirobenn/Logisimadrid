package test.Gates;

import Circuit.Circuit;
import Circuit.Fils;
import Circuit.QuadBool;
import Gates.OutputGate;
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
    layout = new Pane();  
    circuit = new Circuit(layout);  
    outputGate = new OutputGate(circuit, layout, 100, 100);
    input = new Fils(circuit, null, outputGate, true);
    outputGate.getInputs()[0] = input; // Explicitly connect the input
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

