package Circuit;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

public class GatesShapes {
    public static final int x = Unity.x;
    public static final int y = Unity.y;
    public static final int width = Unity.width;
    public static final int height = Unity.height;
    public static final double STROKE_WIDTH = Unity.STROKE_WIDTH;

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
        andGate.setStrokeWidth(3);
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

        sh.setOnMouseClicked(e -> {
            
        });
        sh.setStrokeWidth(3);
        return sh;
    }

    public static Shape orShape(){
        QuadCurve quad = new QuadCurve(0, 0, 1.5*x, 3*y, 0, 6*y);
        QuadCurve quad2 = new QuadCurve(0, 0, 12*x, 3*y, 0, 6*y);
        Shape orGate = Shape.subtract(quad2, quad);
        orGate.setStroke(Color.BLACK);
        orGate.setFill(Color.TRANSPARENT);
        orGate.setStrokeWidth(3);
        return orGate;
    }

    public static Shape norShape(){
        Shape or = orShape();
        Circle circle = new Circle(6.5*x, 3*y, 0.5*x);
        Shape sh = Shape.union(or, circle);
        sh.setFill(Color.TRANSPARENT);
        sh.setStroke(Color.BLACK);
        sh.setStrokeWidth(3);

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
        sh2.setStrokeWidth(3);

        return sh2;
    }

    public static Shape xnorShape(){
        Shape xor = xorShape();
        Circle circle = new Circle(7*x, 3*y, 0.5*x);
        Shape sh = Shape.union(xor, circle);

        sh.setFill(Color.TRANSPARENT);
        sh.setStroke(Color.BLACK);
        sh.setStrokeWidth(3);

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
        sh.setStrokeWidth(3);

        return sh;
    }

    public static Shape variable(){
        Polygon sh = new Polygon();
        sh.getPoints().addAll(new Double[]{
            0.0, 0.0,
            2*(double)Unity.x, 0.0,
            3*(double)Unity.x, (double)Unity.y,
            2*(double)Unity.x, 2*(double)Unity.y,
            0.0, 2*(double)Unity.y
        });
        sh.setFill(Color.TRANSPARENT);
        sh.setStroke(Color.BLACK);
        sh.setStrokeWidth(3);
        return sh;
    }

    public static void rotate(Shape item){
        if(item != null) item.setRotate(item.getRotate() + 90);
    }
}