package test.Circuit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Circuit.Circuit;
import Circuit.Fils;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;


class GateTest {

    private Pane layout;
    private Circuit circuit;
    private GateTestImpl testGate;
    private GateTestImpl andGate;
    private GateTestImpl orGate;
    private GateTestImpl notGate;

    @BeforeAll
    static void initialiser(){
        new JFXPanel();
    }

    @BeforeEach
    void setUp() throws Exception{
        
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {

            layout = new Pane();
            circuit = new Circuit(layout);
            
            
            testGate = new GateTestImpl("TEST", 2, circuit, layout, 100.0, 200.0);
            andGate = new GateTestImpl("AND", 2, circuit, layout, 150.0, 250.0);
            orGate = new GateTestImpl("OR", 2, circuit, layout, 200.0, 300.0);
            notGate = new GateTestImpl("NOT", 1, circuit, layout, 250.0, 350.0);
            latch.countDown();
        });

        latch.await();     
    }

    @Test
    void testConstructor() throws Exception{
        
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
                assertEquals("TEST", testGate.getName());
                assertEquals(2, testGate.getInputs().length);
                assertNotNull(testGate.getOutputFils());
                assertFalse(testGate.isOutputSet());  
                assertNotNull(testGate.getCircuit());
                assertNotNull(testGate.getLayout());
                assertNotNull(testGate.getOtherFils());
                assertEquals(0, testGate.getOtherFils().size());
                latch.countDown();
        });
    
        latch.await();
    }

    @Test
    void testGetName() {
        
                assertEquals("AND", andGate.getName());
                assertEquals("OR", orGate.getName());
                assertEquals("NOT", notGate.getName());
    }

    @Test
    void testSetAndGetOutputFils() {
        
                Fils outputFils = new Fils(circuit, null, testGate, true);
                testGate.setOutputFils(outputFils);
                assertEquals(outputFils, testGate.getOutputFils());  
    }

    @Test
    void testGetInputs() {
        
                Fils[] inputs = testGate.getInputs();
                assertNotNull(inputs);
                assertEquals(2, inputs.length);
                
                Fils[] notInputs = notGate.getInputs();
                assertNotNull(notInputs);
                assertEquals(1, notInputs.length);
    }

    @Test
    void testAddFilsToOthers() throws Exception{
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
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
                latch.countDown();
            });
    
            latch.await();
    }

    @Test
    void testGetShape() throws Exception{
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
                Shape andShape = andGate.getShape();
                Shape orShape = orGate.getShape();
                Shape notShape = notGate.getShape();
                
                System.out.println(andShape.getClass());
                System.out.println(notShape.getClass());
                assertNotNull(andShape);
                assertNotNull(orShape);
                assertNotNull(notShape);
                
                assertEquals(andShape.getClass(), notShape.getClass());
                latch.countDown();
            });
    
            latch.await();
    }

    @Test
    void testSetAndGetText() {
        
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
    }
}
