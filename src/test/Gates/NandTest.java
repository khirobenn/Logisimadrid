package test.Gates;

import Circuit.Circuit;
import Circuit.Fils;
import Circuit.QuadBool;
import Gates.Nand;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NandTest {
	private Circuit circuit;
	private Pane layout;
	private Fils input1, input2;
	private Nand nandGate;

	@BeforeEach
	public void setUp() {
		layout = new Pane();
		circuit = new Circuit(layout);
		nandGate = new Nand(2, circuit, layout, 100, 100);  // Crée la porte NAND d’abord

		input1 = new Fils(0, 0, circuit, null, nandGate, true);
		input2 = new Fils(0, 0, circuit, null, nandGate, true);

		nandGate.setInput(0, input1);  // ⚠ Il faut connecter les entrées à la porte
		nandGate.setInput(1, input2);
	}

	@Test
	public void testNandGateOutput() {
		input1.setOutput(QuadBool.TRUE);  // Définir la sortie de l'entrée 1
		input2.setOutput(QuadBool.FALSE); // Définir la sortie de l'entrée 2

		nandGate.evaluateOutput();  // Évaluer la sortie de la porte NAND

		// La sortie d'une porte NAND est FAUX uniquement si toutes les entrées sont VRAIES
		assertEquals(QuadBool.TRUE, nandGate.getOutput());  // Attendu: VRAI (car une entrée est FAUSSE)
	}

	@Test
	public void testNandGateWithBothTrueInputs() {
		input1.setOutput(QuadBool.TRUE);
		input2.setOutput(QuadBool.TRUE);

		nandGate.evaluateOutput();

		assertEquals(QuadBool.FALSE, nandGate.getOutput());  // Attendu: FAUX (car les deux entrées sont VRAIES)
	}

	@Test
	public void testNandGateWithBothFalseInputs() {
		input1.setOutput(QuadBool.FALSE);
		input2.setOutput(QuadBool.FALSE);

		nandGate.evaluateOutput();

		assertEquals(QuadBool.TRUE, nandGate.getOutput());  // Attendu: VRAI (car toutes les entrées sont FAUSSES)
	}
}
