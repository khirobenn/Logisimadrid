package Gate;

import java.lang.classfile.components.ClassPrinter.Node;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

public class GatesShapes {
    public static final int x = 10;
    public static final int y = 10;
    public static final int width = 400;
    public static final int height = 200;
    public static final double STROKE_WIDTH = 2.0;

    public static void dragItem(MouseEvent e, Shape item){
        // On convertit les coordonnées de la souris par rapport au parent (la fenetre)
        Point2D mouseCoord = item.localToParent(e.getX(), e.getY());
        mouseCoord = new Point2D(
            Math.floor(mouseCoord.getX()) - Math.floor(mouseCoord.getX())%x,
            Math.floor(mouseCoord.getY()) - Math.floor(mouseCoord.getY())%y
        );

        // On vérifie que l'objet ne dépasse pas la fenetre
        // On note que setLayout, définit les coordonnées par rapport au parent
        if(mouseCoord.getX() >= 0 && mouseCoord.getX() + item.boundsInParentProperty().getValue().getWidth() <= width)
            item.setLayoutX(mouseCoord.getX());

        if(mouseCoord.getY() >= 0 && mouseCoord.getY() + item.boundsInParentProperty().getValue().getHeight() <= height)
            item.setLayoutY(mouseCoord.getY());
    }


    public static Shape andShape(){
        Circle circle = new Circle(3*x, 3*y, 3*x);
        Rectangle rectangle = new Rectangle(0, 0, 3*x, 3*2*x);
        Shape andGate = Shape.union(circle, rectangle);
        andGate.setStroke(Color.BLACK);
        andGate.setFill(Color.TRANSPARENT);
        andGate.setStrokeType(StrokeType.CENTERED);
        andGate.setStrokeWidth(STROKE_WIDTH);
        andGate.setLayoutX(0);
        andGate.setLayoutY(0);
        System.out.println(new Point2D(andGate.getLayoutX(), andGate.getLayoutY()) + " | " + andGate.localToParent(0, 0));
        andGate.setOnMouseDragged(e -> dragItem(e, andGate));
        return andGate;
    }

    public static Shape nandShape(){
        Shape and = andShape();  
        Circle circle = new Circle(6.5*x, 3*y, 0.5*x);
        
        Shape sh = Shape.union(and, circle);
        sh.setFill(Color.TRANSPARENT);
        sh.setStroke(Color.BLACK);
        sh.setStrokeType(StrokeType.CENTERED);
        sh.setStrokeWidth(GatesShapes.STROKE_WIDTH);

        sh.setOnMouseDragged(e -> dragItem(e, sh));
        sh.setOnMouseClicked(e -> {
            
        });
        return sh;
    }

    public static Shape orShape(){
        QuadCurve quad = new QuadCurve(0, 0, 1.5*x, 3*y, 0, 6*y);

        QuadCurve quad2 = new QuadCurve(0, 0, 12*x, 3*y, 0, 6*y);

        Shape orGate = Shape.subtract(quad2, quad);

        orGate.setOnMouseDragged(e -> dragItem(e, orGate));

        orGate.setStroke(Color.BLACK);
        orGate.setFill(Color.TRANSPARENT);
        return orGate;
    }

    public static Shape norShape(){
        Shape or = orShape();
        Circle circle = new Circle(6.5*x, 3*y, 0.5*x);

        Shape sh = Shape.union(or, circle);
        sh.setFill(Color.TRANSPARENT);
        sh.setStroke(Color.BLACK);

        sh.setOnMouseDragged(e -> dragItem(e, sh));
        return sh;
    }
    
    public static Shape xorShape(){
        QuadCurve quad = new QuadCurve(x, 0, 4*x, 3*y, x, 6*y);

        QuadCurve quad2 = new QuadCurve(x, 0, 12*x, 3*y, x, 6*y);

        Shape shape = Shape.subtract(quad2, quad);
        shape.setStroke(Color.BLACK);
        shape.setFill(Color.TRANSPARENT);

        QuadCurve quad3 = new QuadCurve(0, 0, 2*x, 3*y, 0, 6*y);
        quad3.setStroke(Color.BLACK);
        quad3.setFill(Color.TRANSPARENT);

        Shape sh = Shape.union(shape, quad3);
        QuadCurve quad4 = new QuadCurve(0, 0, 1.99*x, 3*y, 0, 6*y);

        Shape sh2 = Shape.subtract(sh, quad4);
        sh2.setStroke(Color.BLACK);
        sh2.setFill(Color.TRANSPARENT);
        sh2.setOnMouseDragged(e -> dragItem(e, sh2));

        return sh2;
    }

    public static Shape xnorShape(){
        Shape xor = xorShape();
        Circle circle = new Circle(7*x, 3*y, 0.5*x);

        Shape sh = Shape.union(xor, circle);
        sh.setFill(Color.TRANSPARENT);
        sh.setStroke(Color.BLACK);

        sh.setOnMouseDragged(e -> dragItem(e, sh));
        return sh;
    }

    public static Shape notShape(){
        Polygon lines = new Polygon();
        lines.getPoints().addAll(new Double[]{
        0.0, 0.0,
        0.0, 4.0*y,
        3.0*x, 2.0*y });

        Circle circle = new Circle(3.5*x, 2*y, 0.5*x);

        Shape sh = Shape.union(lines, circle);
        
        sh.setFill(Color.TRANSPARENT);
        sh.setStroke(Color.BLACK);

        sh.setOnMouseDragged(e -> dragItem(e, sh));
        System.out.println(sh.getLayoutBounds());
        return sh;
    }

    public static void rotate(Shape item){
        if(item != null) item.setRotate(item.getRotate() + 90);
    }
}