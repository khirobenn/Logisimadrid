package test.Circuit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Circuit.Circuit;
import Circuit.Fils;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class GateTest {

    private Pane layout;
    private Circuit circuit;
    private GateTestImpl testGate;
    private GateTestImpl andGate;
    private GateTestImpl orGate;
    private GateTestImpl notGate;
    
    static {
        
        new JFXPanel();
    }

    @BeforeEach
    void setUp() throws Exception {
        
        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            try {
                layout = new Pane();
                circuit = new Circuit(layout);
                
                
                testGate = new GateTestImpl("TEST", 2, circuit, layout, 100.0, 200.0);
                andGate = new GateTestImpl("AND", 2, circuit, layout, 150.0, 250.0);
                orGate = new GateTestImpl("OR", 2, circuit, layout, 200.0, 300.0);
                notGate = new GateTestImpl("NOT", 1, circuit, layout, 250.0, 350.0);
            } finally {
                latch.countDown();
            }
        });
        
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("Timeout lors de l'initialisation JavaFX");
        }
    }

    @Test
    void testConstructor() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            try {
                assertEquals("TEST", testGate.getName());
                assertEquals(2, testGate.getInputs().length);
                assertNull(testGate.getOutputFils());
                assertFalse(testGate.isOutputSet());  
                assertNotNull(testGate.getCircuit());
                assertNotNull(testGate.getLayout());
                assertNotNull(testGate.getOtherFils());
                assertEquals(0, testGate.getOtherFils().size());
            } finally {
                latch.countDown();
            }
        });
        
        latch.await(2, TimeUnit.SECONDS);
    }

    @Test
    void testGetName() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            try {
                assertEquals("AND", andGate.getName());
                assertEquals("OR", orGate.getName());
                assertEquals("NOT", notGate.getName());
            } finally {
                latch.countDown();
            }
        });
        
        latch.await(2, TimeUnit.SECONDS);
    }

    @Test
    void testSetAndGetOutputFils() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            try {
                Fils outputFils = new Fils(circuit, null, testGate, true);
                testGate.setOutputFils(outputFils);
                assertEquals(outputFils, testGate.getOutputFils());  
            } finally {
                latch.countDown();
            }
        });
        
        latch.await(2, TimeUnit.SECONDS);
    }

    @Test
    void testGetInputs() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            try {
                Fils[] inputs = testGate.getInputs();
                assertNotNull(inputs);
                assertEquals(2, inputs.length);
                
                Fils[] notInputs = notGate.getInputs();
                assertNotNull(notInputs);
                assertEquals(1, notInputs.length);
            } finally {
                latch.countDown();
            }
        });
        
        latch.await(2, TimeUnit.SECONDS);
    }

    @Test
    void testAddFilsToOthers() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            try {
                Fils newFils = new Fils(circuit, null, testGate, false);
                assertEquals(0, testGate.getOtherFils().size());
                
                testGate.addFilsToOthers(newFils);
                assertEquals(1, testGate.getOtherFils().size());
                assertTrue(testGate.getOtherFils().contains(newFils));
                
                testGate.addFilsToOthers(newFils);
                assertEquals(1, testGate.getOtherFils().size());
                
                Fils anotherFils = new Fils(circuit, null, testGate, false);
                testGate.addFilsToOthers(anotherFils);
                assertEquals(2, testGate.getOtherFils().size());
            } finally {
                latch.countDown();
            }
        });
        
        latch.await(2, TimeUnit.SECONDS);
    }

    @Test
    void testGetShape() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            try {
                Shape andShape = andGate.getShape();
                Shape orShape = orGate.getShape();
                Shape notShape = notGate.getShape();
                
                assertNotNull(andShape);
                assertNotNull(orShape);
                assertNotNull(notShape);
                
                assertEquals(andShape.getClass(), notShape.getClass());
            } finally {
                latch.countDown();
            }
        });
        
        latch.await(2, TimeUnit.SECONDS);
    }

    @Test
    void testSetAndGetText() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            try {
                assertNull(testGate.getText());
                
                String textValue = "Test Text";
                testGate.setText(textValue, 100, 200);
                
                Text textObject = testGate.getText();
                assertNotNull(textObject);
                assertEquals(textValue, textObject.getText());
                assertEquals(100, textObject.getLayoutX());
                assertEquals(200, textObject.getLayoutY());
                
                String newTextValue = "Updated Text";
                testGate.setText(newTextValue, 100, 200);
                assertEquals(newTextValue, textObject.getText());
            } finally {
                latch.countDown();
            }
        });
        
        latch.await(2, TimeUnit.SECONDS);
    }
}
