package Gates;

import Circuit.Circuit;
import Circuit.QuadBool;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VariableTest {

    private Circuit circuit;
    private Pane layout;
    private Variable variable;

    @BeforeEach
    public void setUp() {
        layout = new Pane();  // Crée un layout pour le test
        circuit = new Circuit(layout);  // Crée un circuit avec le layout
        variable = new Variable(QuadBool.TRUE, circuit, layout, 100, 100);  // Crée une variable initialisée à TRUE
    }

    @Test
    public void testInitialState() {
        // Vérifie que la sortie initiale est "TRUE"
        assertEquals(QuadBool.TRUE, variable.getOutput());
    }

    @Test
    public void testSetOutput() {
        // Change la sortie à FALSE
        variable.setOutput(QuadBool.FALSE);
        assertEquals(QuadBool.FALSE, variable.getOutput());

        // Change la sortie à NOTHING
        variable.setOutput(QuadBool.NOTHING);
        assertEquals(QuadBool.NOTHING, variable.getOutput());
    }

    @Test
    public void testOutputAfterCircuitEval() {
        // Tester l'effet de l'évaluation du circuit sur la variable
        variable.setOutput(QuadBool.FALSE);  // On définit la sortie à FALSE
        circuit.eval(variable);  // On fait l'évaluation (hypothétique selon le comportement du circuit)
        
        // Vérifier si la sortie est toujours FALSE après l'évaluation (comme la classe ne modifie pas l'état)
        assertEquals(QuadBool.FALSE, variable.getOutput());
    }

    @Test
    public void testToString() {
        // Tester la méthode toString (si elle est présente ou ajoutée pour décrire l'état de la variable)
        String expectedString = "Variable{value=TRUE}";
        assertEquals(expectedString, variable.toString());
    }
}
