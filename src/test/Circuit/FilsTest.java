package test.Circuit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Circuit.Circuit;
import Circuit.Fils;
import Circuit.Gate;
import Circuit.QuadBool;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

class FilsTest {

    private Pane layout;
    private Circuit circuit;
    private Gate gate;
    private Fils testFils;
    private Fils parentFils;
    

    @BeforeEach
    void setUp() {
                layout = new Pane();
                circuit = new Circuit(layout);
                gate = new GateTestImpl("GateName", 1, circuit, layout, 100.0, 200.0);
                
               
                Circle circle1 = new Circle(10, 10, 5);
                Circle circle2 = new Circle(20, 20, 5);
                Line line1 = new Line(10, 10, 20, 20);
                Line line2 = new Line(20, 20, 30, 30);
                layout.getChildren().addAll(circle1, circle2, line1, line2);
                
                
                parentFils = new Fils(circuit, null, gate, true);
                parentFils.setOutputValue(QuadBool.TRUE);
                
                testFils = new Fils(circuit, parentFils, gate, true);
    }

    @Test
    void testConstructorWithParent() {
        
                assertEquals(parentFils, testFils.getParent());
                assertEquals(gate, testFils.getGate());
                assertTrue(testFils.getIsFilsRelatedToSomething());
                assertEquals(QuadBool.TRUE, testFils.getOutputValue());
    }

    @Test
    void testConstructorWithoutParent() {
        final Fils[] filsWithoutParent = new Fils[1];
        
                filsWithoutParent[0] = new Fils(circuit, null, gate, false);
                assertNull(filsWithoutParent[0].getParent());
                assertEquals(gate, filsWithoutParent[0].getGate());
                assertFalse(filsWithoutParent[0].getIsFilsRelatedToSomething());
                assertEquals(QuadBool.NOTHING, filsWithoutParent[0].getOutputValue());
    }

    @Test
    void testSetAndGetGate() {
        
                Gate newGate = new GateTestImpl("NewGateName", 2, circuit, layout, 150.0, 250.0);
                testFils.setGate(newGate);
                assertEquals(newGate, testFils.getGate());
    }

    @Test
    void testSetAndGetIsFilsRelatedToSomething() {
        
                testFils.setIsFilsRelatedToSomething(false);
                assertFalse(testFils.getIsFilsRelatedToSomething());

                testFils.setIsFilsRelatedToSomething(true);
                assertTrue(testFils.getIsFilsRelatedToSomething());
    }

    @Test
    void testAddToConnected() {
        final Fils[] otherFils = new Fils[1];
        
                otherFils[0] = new Fils(circuit, null, gate, false);
                testFils.addToConnected(otherFils[0]);

                assertTrue(testFils.getConnected().contains(otherFils[0]));
                assertTrue(otherFils[0].getConnected().contains(testFils));
                assertEquals(1, testFils.getConnectedNb());
    }

    @Test
    void testSetAndGetIsAVariable() {
        
                assertFalse(testFils.getIsAVariable());
                testFils.setFilsAsVariable();
                assertTrue(testFils.getIsAVariable());
    }

    @Test
    void testSetAndGetOutput() {
        
                testFils.setOutput(QuadBool.FALSE);
                assertEquals(QuadBool.FALSE, testFils.getOutput());

                testFils.setOutput(QuadBool.ERROR);
                assertEquals(QuadBool.ERROR, testFils.getOutput());
    }

    @Test
    void testSetOutputValue() {
        
                testFils.setOutputValue(QuadBool.NOTHING);
                assertEquals(QuadBool.NOTHING, testFils.getOutputValue());
    }

    @Test
    void testReinitialiseOutput() {
        
                testFils.setOutput(QuadBool.TRUE);
                testFils.reinitialiseOutput();
                assertEquals(QuadBool.NOTHING, testFils.getOutputValue());
    }

    @Test
    void testSetIsOutputSet() {
        
                testFils.setIsOutputSet(true);
                testFils.setOutputValue(QuadBool.TRUE);
                assertEquals(QuadBool.TRUE, testFils.getOutput());
    }

    @Test
    void testChangePlaceForPoints() {
        
                double newX = 50.0;
                double newY = 60.0;
                testFils.changePlaceForPoints(newX, newY);

                Point2D circle1Coord = testFils.getCircle1Coord();
                Point2D circle2Coord = testFils.getCircle2Coord();

                assertEquals(newX, circle1Coord.getX());
                assertEquals(newY, circle1Coord.getY());
                assertEquals(newX, circle2Coord.getX());
                assertEquals(newY, circle2Coord.getY());
    }

    @Test
    void testIsNoLine() {
        
                Line emptyLine = new Line(10, 10, 10, 10);
                Line nonEmptyLine = new Line(10, 10, 20, 20);

                assertTrue(testFils.isNoLine(emptyLine));
                assertFalse(testFils.isNoLine(nonEmptyLine));
                assertTrue(testFils.isNoLine(null));
    }

    @Test
    void testGetCircleCoordinates() {
        
                double x = 75.0;
                double y = 85.0;
                testFils.setCircle1Coord(x, y);
                testFils.setCircle2Coord(x, y);

                Point2D circle1Coord = testFils.getCircle1Coord();
                Point2D circle2Coord = testFils.getCircle2Coord();

                assertEquals(x, circle1Coord.getX());
                assertEquals(y, circle1Coord.getY());
                assertEquals(x, circle2Coord.getX());
                assertEquals(y, circle2Coord.getY());
    }

    @Test
    void testSetCircleFill() {
        
                testFils.setCircleFill(Color.RED);
                testFils.setCircle2Fill(Color.BLUE);
               
    }

    @Test
    void testChangeColor() {
        
                testFils.changeColor(Color.GREEN);
                
    }

    @Test
    void testSwapCircles() {
        
                testFils.setCircle1Coord(10, 20);
                testFils.setCircle2Coord(30, 40);

                Point2D originalCircle1 = testFils.getCircle1Coord();
                Point2D originalCircle2 = testFils.getCircle2Coord();

                testFils.swapCircles();

                Point2D newCircle1 = testFils.getCircle1Coord();
                Point2D newCircle2 = testFils.getCircle2Coord();

                assertEquals(originalCircle2.getX(), newCircle1.getX());
                assertEquals(originalCircle2.getY(), newCircle1.getY());
                assertEquals(originalCircle1.getX(), newCircle2.getX());
                assertEquals(originalCircle1.getY(), newCircle2.getY());
    }

    @Test
    void testSetLineCoordinates() {
        
                Point2D start1 = new Point2D(10, 20);
                Point2D end1 = new Point2D(30, 40);
                Point2D start2 = new Point2D(50, 60);
                Point2D end2 = new Point2D(70, 80);

                testFils.setL1Coord(start1, end1);
                testFils.setL2Coord(start2, end2);

                Line l1 = testFils.getL1();
                Line l2 = testFils.getL2();

                assertEquals(start1.getX(), l1.getStartX());
                assertEquals(start1.getY(), l1.getStartY());
                assertEquals(end1.getX(), l1.getEndX());
                assertEquals(end1.getY(), l1.getEndY());

                assertEquals(start2.getX(), l2.getStartX());
                assertEquals(start2.getY(), l2.getStartY());
                assertEquals(end2.getX(), l2.getEndX());
                assertEquals(end2.getY(), l2.getEndY());
    }

    @Test
    void testSetParent() {
        
                Fils newParent = new Fils(circuit, null, gate, true);
                testFils.setParent(newParent);
                assertEquals(newParent, testFils.getParent());
    }

    @Test
    void testSetItemsToTransparent() {
        
                testFils.setItemsToTransparent();
               
    }

    @Test
    void testSetCircleToTransparent() {
        
                testFils.setCircleToTransparent();
                
    }

    @Test
    void testGetConnectedNb() {
        
                assertEquals(0, testFils.getConnectedNb());
                
                Fils otherFils = new Fils(circuit, null, gate, false);
                testFils.addToConnected(otherFils);
                
                assertEquals(1, testFils.getConnectedNb());
    }

    @Test
    void testMoveFils() {
        
                double newX = 100.0;
                double newY = 120.0;
                
                testFils.moveFils(newX, newY);
                
                Point2D circle1Coord = testFils.getCircle1Coord();
                assertEquals(newX, circle1Coord.getX());
                assertEquals(newY, circle1Coord.getY());
    }
}