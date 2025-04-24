package Circuit;

import Gates.And;
import Gates.OutputGate;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CircuitTest {

    private Circuit circuit;
    private Pane pane;

    @BeforeEach
    public void setUp() {
        
        new JFXPanel();
        pane = new Pane();
        circuit = new Circuit(pane);
    }

    @Test
    public void testAddGate() {
        int initialSize = circuit.getGates().size();
        circuit.createGate("AND", 2, 100, 100);
        assertEquals(initialSize + 1, circuit.getGates().size());
    }

    @Test
    public void testZoomFunction() {
        double oldZoom = circuit.getZoom();
        circuit.zoom();
        assertTrue(circuit.getZoom() > oldZoom);
    }

    @Test
    public void testUnzoomFunction() {
        circuit.zoom(); // pour augmenter un peu
        double zoomed = circuit.getZoom();
        circuit.unzoom();
        assertTrue(circuit.getZoom() < zoomed);
    }

    @Test
    public void testClearAll() {
        circuit.createGate("AND", 2, 100, 100);
        circuit.clearAll();
        assertTrue(circuit.getGates().isEmpty());
        assertTrue(circuit.getVar().isEmpty());
        assertTrue(circuit.getFils().isEmpty());
    }
}
