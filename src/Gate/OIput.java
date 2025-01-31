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
    // childs1 for childs in x coordinate 
    private Deque <OIput> childs1;
    // childs2 for childs in y coordinate 
    private Deque <OIput> childs2;

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
        childs1 = new LinkedList<OIput>();
        childs2 = new LinkedList<OIput>();

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
        circle2.setFill(Color.GREEN);
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

    private void reinitialiseLine(Line l){
        int r = -10;
        setLineCoord(l1, r, r, r, r);
        fils.getGroup().getChildren().remove(l);
    }
    private void reinitialiseLines(){
        reinitialiseLine(l1);
        reinitialiseLine(l2);
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
            boolean fixOnL1 = false;
            boolean fixOnL2 = false;
            if(isL1InParent && isL2InParent){
                if(l1.getStartX() == parent.l2.getEndX() && l1.getStartY() == parent.l2.getEndY()){
                    parent.l2.setEndX(l1.getEndX());
                    parent.l2.setEndY(l1.getEndY());
                    parent.circle.setCenterX(l1.getEndX());
                    parent.circle.setCenterY(l1.getEndY());

                    // TO DO
                }
                System.out.println("Cas 1");
                reinitialiseLines();
                return;
            }
            else if(isL1InParent){
                // Have to add this func
                fixOnL1 = fixLineToAdd(l2, parent.l1);
                fixOnL2 = fixLineToAdd(l2, parent.l2);
                
                if(fixOnL1 || fixOnL2){
                    System.out.println("Cas 2.1");
                    parent.createPointOnEachLine(l2);
                    reinitialiseLines();
                }
                else{
                    createPointOnEachLine(l2);
                    System.out.println("Cas 2.2");
                }
            }
            else{
                if(parent != null){
                    fixOnL1 = fixLineToAdd(l1, parent.l1);
                    fixOnL2 = fixLineToAdd(l1, parent.l2);
                }

                if(fixOnL1 || fixOnL2){
                    parent.createPointOnEachLine(l1);
                    reinitialiseLine(l1);
                }
                else{
                    createPointOnEachLine(l1);
                }

                if(parent != null 
                && parent.l2.getEndX() == parent.l2.getStartX() 
                && parent.l2.getEndY() == parent.l2.getStartY()){
                    parent.l2 = l2;
                    parent.createPointOnEachLine(l2);
                    l2.setStroke(Color.BLUE);
                    l2 = new Line();
                    reinitialiseLine(l2);
                }else{

                    createPointOnEachLine(l2);
                }
                System.out.println("Cas 3");
            }

            if(l1.getStartX() == -10 && l2.getStartX() == -10){
                reinitialiseParemeters();
            }
            else{
                l1.setStroke(Color.BLUE);
                l2.setStroke(Color.BLUE);
                circle.setCenterX(l2.getEndX());
                circle.setCenterY(l2.getEndY());
            }
        }
    }

    private void reinitialiseParemeters(){
        l1.setStroke(Color.DARKBLUE);
        l2.setStroke(Color.DARKBLUE);
        circle.setCenterX(circle2.getCenterX());
        circle.setCenterY(circle2.getCenterY());
    }

    private int sign(double start, double end){
        if(end > start) return 1;
        return -1;
    }

    // n is for direction to compare x or y ; n = 0, we compare x coordinates
    // n = 1, we compare y coordinates
    private boolean areInSameDirection(Line l, Line k, int n){
        if(n == 0) return sign(l.getStartX(), l.getEndX()) == sign(k.getStartX(), k.getEndX());
        return sign(l.getStartY(), l.getEndY()) == sign(k.getStartY(), k.getEndY());
    }

    private boolean fixLineToAdd(Line l, Line lToExtend){
        if(l.getStartX() == lToExtend.getStartX() 
        && l.getStartX() == l.getEndX() 
        && lToExtend.getStartX() == lToExtend.getEndX()){
            if(areInSameDirection(l, lToExtend, 1)){
                double start = lToExtend.getEndY();
                lToExtend.setEndY(l.getEndY());
                l.setStartY(start);
                return true;
            }
            else{
                l.setStartY(lToExtend.getStartY());
                return false;
            }
        }
        else if(l.getStartY() == lToExtend.getStartY() 
        && l.getStartY() == l.getEndY() 
        && lToExtend.getStartY() == lToExtend.getEndY()){
            if(areInSameDirection(l, lToExtend, 0)){
                double start = lToExtend.getEndX();
                lToExtend.setEndX(l.getEndX());
                l.setStartX(start);
                return true;
            }
            else{
                l.setStartX(lToExtend.getStartX());
                return false;
            }
        }
        return false;

    }
    /** 
     * returns true if line1 contains line2
     * */
    private boolean lineContainsLine(Line line1, Line line2){
        return line1.contains(new Point2D(line2.getStartX(), line2.getStartY())) && line1.contains(new Point2D(line2.getEndX(), line2.getEndY()));
    }

    private double tranformDoubleToInt(double xPoint){
        int x = Unity.x;
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
        sh.setStrokeWidth(Unity.STROKE_WIDTH);
        sh.setStroke(Color.GREEN);
    }

    private void removeStroke(Shape sh){
        sh.setStrokeWidth(0);
        sh.setStroke(null);
    }


    /**
     * Adding Fils for a line we created, such that for each point we can drag it.
     */
    private void addFilsInLine(double p1, double p2, double constantCoord, int n){ 
        // the n is for the position of the line
        // n = 0 , x is constant else y is constant
        int x = sign(p1, p2) * Unity.x;
        int distance = sign(p1, p2)*(int)(p2-p1);
        double start = p1;

        double xCord, yCord;
        if(n == 0){
            xCord = constantCoord;
            yCord = start;
        }
        else{
            xCord = start;
            yCord = constantCoord;
        }
        
        for(int i = 0; i <= distance; i += Unity.x) {
            OIput element = new OIput(xCord, yCord, fils, this);
            
            if(parent != null && (parent.l1.contains(xCord, yCord) || parent.l2.contains(xCord, yCord))
            && (parent.l2.getEndX() != xCord || parent.l2.getEndY() != yCord)){
                element.circle2.setFill(Color.BLUE);
            }
            else{
                element.circle2.setFill(Color.TRANSPARENT);
            }

            if(n == 0){
                yCord += x;
                childs1.push(element);
            }
            else{
                xCord += x;
                childs2.push(element);
            }
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