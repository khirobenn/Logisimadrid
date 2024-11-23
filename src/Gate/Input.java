package Gate;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Input {
    private Line l1;
    private Line l2;
    private Circle circle;

    private Line l3;
    private Line l4;
    private Circle circle2;

    public Input(){
        circle = new Circle(0, 0, 5);
        circle.setCenterX(10);
        circle.setCenterY(10);
        l1 = new Line();
        l2 = new Line();

        l1.setStrokeWidth(2);
        l2.setStrokeWidth(2);
        circle.setOnMouseDragged(e -> dragInput(e));

        circle2 = new Circle(-40, -40, 5);
        l3 = new Line(); 
        l4 = new Line();

        circle2.setOnMouseDragged(e -> dragInput(e));
    }

    private void dragInput(MouseEvent e){
        double xPoint = e.getX();
        double yPoint = e.getY();

        l1.setStartX(circle.getCenterX());
        l1.setStartY(circle.getCenterY());

        l1.setEndX(xPoint);
        l1.setEndY(circle.getCenterY());

        l2.setStartX(xPoint);
        l2.setStartY(yPoint);

        l2.setEndX(xPoint);
        l2.setEndY(circle.getCenterY());

        circle2.setCenterX(xPoint);
        circle2.setCenterY(yPoint);
    }

    public void addPoint(Group layout){
        layout.getChildren().addAll(circle, l1, l2, circle2);
    }
}
