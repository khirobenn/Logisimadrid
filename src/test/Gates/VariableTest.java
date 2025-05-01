package test.Gates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Circuit.Circuit;
import Circuit.QuadBool;
import Gates.Variable;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;

import static org.junit.jupiter.api.Assertions.*;

public class VariableTest {

    private Variable variable;
    private Circuit circuit;
    private Pane pane;

    @BeforeEach
    public void setUp() {
        new JFXPanel(); // Initialiser JavaFX
        pane = new Pane();
        circuit = new Circuit(pane);
        variable = new Variable(QuadBool.NOTHING, circuit, pane, 0, 0 );  // Crée une nouvelle variable initialisée à NOTHING
    }

    @Test
    public void testVariableInitialValue() {
        // Vérifie que la valeur initiale de la variable est "NOTHING"
        assertEquals(QuadBool.NOTHING, variable.getOutputValue());
    }

    @Test
    public void testSetValue() {
        // Définit une nouvelle valeur pour la variable
        variable.setOutputValue(QuadBool.TRUE);
        // Vérifie que la valeur de la variable a bien été modifiée
        assertEquals(QuadBool.TRUE, variable.getOutputValue());

        // Modifie la valeur à FAUX
        variable.setOutputValue(QuadBool.FALSE);
        // Vérifie que la valeur est maintenant FAUX
        assertEquals(QuadBool.FALSE, variable.getOutputValue());
    }

    
}
