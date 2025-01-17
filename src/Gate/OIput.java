package Gate;

import java.util.Deque;
import java.util.LinkedList;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class OIput {
    private Fils fils;
    private Deque <OIput> childs;

    private OIput parent;

    private Line l1;
    private Line l2;
    private Circle circle;
    private Circle circle2;

    private boolean dx, dy;

    public OIput(Fils fils, OIput parent){
        this.parent = parent;
        int pos = 20;
        this.fils = fils;
        childs = new LinkedList<OIput>();

        dx = false;
        dy = false;

        circle = new Circle(0, 0, 10);
        circle.setCenterX(pos);
        circle.setCenterY(pos);

        circle2 = new Circle(0, 0, 10);
        circle2.setCenterX(pos);
        circle2.setCenterY(pos);

        l1 = new Line(-10, -10, -10, -10);
        l2 = new Line(-10, -10, -10, -10);

        l1.setStrokeWidth(6);
        l2.setStrokeWidth(6);

        circle.setFill(Color.TRANSPARENT);
        circle2.setFill(Color.BLUE);
        circle2.setOnMouseDragged(e -> dragOIput(e, circle2, circle));
        circle2.setOnMouseReleased(e -> onRelease());
        circle2.setOnMouseEntered(e -> addStroke(circle2));
        circle2.setOnMouseExited(e -> removeStroke(circle2));

        fils.addElement(this);
    }

    public OIput(double x, double y, Fils fils, OIput parent){
        this(fils, parent);
        circle.setCenterX(x);
        circle2.setCenterX(x);

        circle.setCenterY(y);
        circle2.setCenterY(y);
    }


    private void reinitialiseLines(){
        int r = -10;
        setLineCoord(l1, r, r, r, r);
        setLineCoord(l2, r, r, r, r);
        fils.getGroup().getChildren().removeAll(l1, l2);
    }

    private void setLineCoord(Line l, double startX, double startY, double endX, double endY){
        l.setStartX(startX);
        l.setStartY(startY);

        l.setEndX(endX);
        l.setEndY(endY);
    }

    private void onRelease(){
        if(dx || dy){
            boolean isL1InParent = false, isL2InParent = false;
            if(parent != null){
                isL1InParent = lineContainsLine(parent.l1, l1) || lineContainsLine(parent.l2, l1);
                isL2InParent = lineContainsLine(parent.l1, l2) || lineContainsLine(parent.l2, l2);
            }
            
            if(isL1InParent && isL2InParent){
                System.out.println("Cas 1");
                reinitialiseLines();
                return;
            }
            else if(isL1InParent){
                // Have to add this func
                // fixLineLineToAdd
                if(parent.parent != null && (lineContainsLine(parent.parent.l1, l2) || lineContainsLine(parent.parent.l2, l2))){
                    reinitialiseLines();
                    System.out.println("no add");
                    return;
                }
                createPointOnEachLine(l2);
                System.out.println("Cas 2");
            }
            else if(isL2InParent){
                // fixLineToAdd
                createPointOnEachLine(l1);
                System.out.println("Cas 3");

            }
            else{
                createPointOnEachLine(l1);
                createPointOnEachLine(l2);
                System.out.println("Cas 4");
            }
            
            l1.setStroke(Color.BLUE);
            l2.setStroke(Color.BLUE);
            circle.setCenterX(l2.getEndX());
            circle.setCenterY(l2.getEndY());
        }
    }

    private void fixLineToAdd(Line l){
        boolean isLineX;
        boolean isLineY;

        if(l.getStartX() == l.getEndX()){
            isLineX = true;
            isLineY = false;
        }
        else{
            isLineX = false;
            isLineY = true;          
        }
    }
    /** 
     * returns true if line1 contains line2
     * */
    private boolean lineContainsLine(Line line1, Line line2){
        return line1.contains(new Point2D(line2.getStartX(), line2.getStartY())) && line1.contains(new Point2D(line2.getEndX(), line2.getEndY()));
    }

    private double tranformDoubleToInt(double xPoint){
        int x = 20;
        return Math.floor(xPoint) - Math.floor(xPoint)%x;
    }

    private void dragOIput(MouseEvent e, Circle circle, Circle circle2){
        if(!(fils.getGroup().getChildren().contains(l1) && fils.getGroup().getChildren().contains(l2))){
            fils.getGroup().getChildren().addAll(l1, l2);
        }
        double xPoint = e.getX();
        double yPoint = e.getY();

        xPoint = tranformDoubleToInt(xPoint);
        yPoint = tranformDoubleToInt(yPoint);

        if(xPoint == circle.getCenterX() && yPoint != circle.getCenterY()) dy = true;
        else if(xPoint != circle.getCenterX() && yPoint == circle.getCenterY()) dx = true;
        else if(xPoint == circle.getCenterX() && yPoint == circle.getCenterY()){
            dx = false;
            dy = false;
        }

        if(dx){
            setLineCoord(l1, circle.getCenterX(), circle.getCenterY(), xPoint, circle.getCenterY());
            setLineCoord(l2, xPoint, circle.getCenterY(), xPoint, yPoint);
        }
        else if(dy){
            setLineCoord(l1, circle.getCenterX(), circle.getCenterY(), circle.getCenterX(), yPoint);
            setLineCoord(l2, circle.getCenterX(), yPoint, xPoint, yPoint);
    
        }
        else if(!dx && !dy){
            reinitialiseLines();
        }

        circle2.setCenterX(xPoint);
        circle2.setCenterY(yPoint);
    }

    public void addPoint(Group layout){
        layout.getChildren().addAll(circle, circle2);
    }

    private void addStroke(Shape sh){
        sh.setStrokeWidth(2);
        sh.setStroke(Color.GREEN);
    }

    private void removeStroke(Shape sh){
        sh.setStrokeWidth(0);
        sh.setStroke(null);
    }


    /**
     * Adding Fils for a line we created, such that for each point we can drag it.
     */
    private void addFilsInLine(double p1, double p2, double constantCoord, int n){ // the n is for the position of the line
        int x = 20;
        double start;
        double end;
        if(p1 > p2){
            start = p2;
            end = p1;
        } 
        else{
            start = p1;
            end = p2;
        }

        double xCord, yCord;
        if(n == 0){
            xCord = constantCoord;
            yCord = start;
        }
        else{
            xCord = start;
            yCord = constantCoord;
        }
        
        while(start <= end){
            OIput element;
            if(n == 0){
                element = new OIput(xCord, yCord, fils, this);
            }
            else{
                element = new OIput(xCord, yCord, fils, this);
            }

            if(parent != null && (parent.l1.contains(xCord, yCord) || parent.l2.contains(xCord, yCord))
            && (parent.l2.getEndX() != xCord || parent.l2.getEndY() != yCord)){
                element.circle2.setFill(Color.BLUE);
            }
            else{
                element.circle2.setFill(Color.TRANSPARENT);
            }

            if(n == 0) yCord += x;
            else xCord += x;
            childs.push(element);
            start += x;
        }
    }

    /** 
     * Adding fils for our two lines, we call addFilsInLine for the two line in our class.
    */
    private void createPointOnEachLine(Line l){
        if(l.getEndX() == l.getStartX()){
            addFilsInLine(l.getStartY(), l.getEndY(), l.getEndX(), 0);
        }
        else if(l.getEndY() == l.getStartY()){
            addFilsInLine(l.getStartX(), l.getEndX(), l.getEndY(), 1);
        }
    }
}